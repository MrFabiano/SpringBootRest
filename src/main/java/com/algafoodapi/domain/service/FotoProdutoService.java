package com.algafoodapi.domain.service;

import com.algafoodapi.domain.exception.FotoProdutoExceptionNaoEncontrada;
import com.algafoodapi.domain.model.FotoProduto;
import com.algafoodapi.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class FotoProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorageService;


    @Transactional
    public FotoProduto salvarFoto(FotoProduto fotoProduto, InputStream dadosArquivo){
        Long restauranteId = fotoProduto.getRestauranteId();
        Long produtoId = fotoProduto.getProduto().getId();
        String nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(fotoProduto.getNomeArquivo());
        Optional<String> nomeArquivoExistente = Optional.empty();
        //String nomeArquivoExistente = null;


        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
        if(fotoExistente.isPresent()){
                 nomeArquivoExistente = Optional.ofNullable(fotoExistente.get().getNomeArquivo());
                 produtoRepository.delete(fotoExistente.get());
                 //produtoRepository.delete(fotoProduto);
        }
        fotoProduto.setNomeArquivo(nomeNovoArquivo);
        fotoProduto = produtoRepository.save(fotoProduto);
        produtoRepository.flush();

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(fotoProduto.getNomeArquivo())
                .contentType(fotoProduto.getContentType())
                .inputStream(dadosArquivo)
                .build();

        fotoStorageService.substituir(String.valueOf(nomeArquivoExistente), novaFoto);

        return fotoProduto;
    }

    @Transactional
    public void excluir(Long restauranteId, Long produtoId){
        FotoProduto fotoProduto = buscarOuFalhar(restauranteId, produtoId);

        produtoRepository.delete(fotoProduto);
        produtoRepository.flush();

        fotoStorageService.remover(fotoProduto.getNomeArquivo());
    }

    public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId){
        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoExceptionNaoEncontrada(restauranteId, produtoId));
    }
}
