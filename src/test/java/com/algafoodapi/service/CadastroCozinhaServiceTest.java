package com.algafoodapi.service;


import com.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algafoodapi.domain.exception.EntidadeNaoEncontraException;
import com.algafoodapi.domain.model.Cozinha;
import com.algafoodapi.domain.service.CadastroCozinhaService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaServiceTest {

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Test
    public void testarCozinhaComSucesso(){
        //CENARIO
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("BRASILEIRA");

        //Ação
        cozinha = cadastroCozinhaService.salvar(cozinha);

        //Validacao
        Assertions.assertThat(cozinha);
        Assertions.assertThat(cozinha.getId()).isNotNull();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testarCozinhaComErro(){
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        novaCozinha = cadastroCozinhaService.salvar(novaCozinha);

        Assertions.assertThat(novaCozinha);
        Assertions.assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test(expected = EntidadeEmUsoException.class)
    public void deveFalharQuandoExcluirCozinha(){
        Cozinha cozinha = new Cozinha();
        cozinha.setId(2L);

        cadastroCozinhaService.excluir(cozinha.getId());
        Assertions.assertThat(cozinha);
    }

    @Test(expected = EntidadeNaoEncontraException.class)
    public void deveFalharCozinhaNaoEncontrada(){
        Cozinha cozinha = new Cozinha();
        cozinha.setId(100L);

        cadastroCozinhaService.excluir(cozinha.getId());
        Assertions.assertThat(cozinha);
        Assertions.assertThat(cozinha.getId());

    }
}
