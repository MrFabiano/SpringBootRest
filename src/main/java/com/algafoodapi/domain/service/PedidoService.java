package com.algafoodapi.domain.service;


import com.algafoodapi.domain.exception.NegocioException;
import com.algafoodapi.domain.exception.PedidoNaoEncontradoException;
import com.algafoodapi.domain.model.*;
import com.algafoodapi.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    public static final String PEDIDO_EM_USO = "Pedido está em uso, tente novamente mais tarde?";

    public static final String PEDIDO_NAO_ENCONTRADO = "Pedido não encontrado, verifique novamente";
    @Autowired
    private CadastroCidadeService cadastroCidadeService;
    @Autowired
    private UsuarioService  usuarioService;
    @Autowired
    private  CadastroRestauranteService  cadastroRestauranteService;
    @Autowired
    private FormaPagamentoService formaPagamentoService;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProdutoService produtoService;

    @Transactional
    public Pedido salvar(Pedido pedido){
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void excluir(Long pedidoId){
       try{
           pedidoRepository.deleteById(pedidoId);
           pedidoRepository.flush();
       }catch(DataIntegrityViolationException e){
           throw new NegocioException(String.format(PEDIDO_EM_USO, pedidoId));

       }catch(EmptyResultDataAccessException e){
           throw new NegocioException(String.format(PEDIDO_NAO_ENCONTRADO, pedidoId));
        }
    }

    @Transactional
    public Pedido emitir(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

        return pedidoRepository.save(pedido);
    }

    private void validarPedido(Pedido pedido) {
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
        Usuario cliente = usuarioService.buscarOuFalhar(pedido.getCliente().getId());
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if (restaurante.naoaceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao()));
        }
    }

    private void validarItens(Pedido pedido) {
        pedido.getItensPedido().forEach(item -> {
            Produto produto = produtoService.buscarOuFalhar(
                    pedido.getRestaurante().getId(), item.getProduto().getId());

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }
    public Pedido buscarOuFalhar(String uuidPedido){
        return pedidoRepository.findByUUID(uuidPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(String.format(PEDIDO_NAO_ENCONTRADO, uuidPedido)));
    }

}
