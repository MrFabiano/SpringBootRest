package com.algafoodapi.domain.service;

import com.algafoodapi.domain.exception.EntidadeNaoEncontraException;
import com.algafoodapi.domain.model.*;
import com.algafoodapi.domain.repository.CozinhaRepository;
import com.algafoodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId  = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);


        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    public Restaurante buscarOuFalhar(Long restauranteId){
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() ->
                        new EntidadeNaoEncontraException(String.format("Não existe cadastro com codigo %d", restauranteId)));

    }

    @Transactional
    public void ativar(Long restauranteId){
        Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
        //restauranteAtual.setAtivo(true);
        restauranteAtual.ativar();
    }

    @Transactional
    public void inativar(Long restauranteId){
        Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
        //restauranteAtual.setAtivo(false);
        restauranteAtual.inativar();
    }

    @Transactional
    public void ativar(List<Long> restaurantesIds){
        restaurantesIds.forEach(this::ativar);
    }

    @Transactional
    public void inativar(List<Long> restaurantesIds){
        restaurantesIds.forEach(this::inativar);
    }

    @Transactional
    public void associarResponsavel(Long usuarioId, Long restauranteId){
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);

        restaurante.adicionarResponsaveis(usuario);
    }

    @Transactional
    public void desassociarResponsavel(Long usuarioId, Long restauranteId){
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);

        restaurante.removerResponsaveis(usuario);
    }


    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId){
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);

        restaurante.aceitaFormaPagamento(formaPagamento);
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId,Long formaPagamentoId){
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);

        restaurante.naoaceitaFormaPagamento(formaPagamento);
    }

    /*@Transactional
    public void abrir(Long restauranteId){
        Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
        restauranteAtual.abrir();
    }

    @Transactional
    public void fechar(Long restauranteId){
        Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
        restauranteAtual.fechar();
    }*/
}
