/*package com.algafoodapi.jpa;

import com.algafoodapi.AlgafoodapiApplication;
import com.algafoodapi.domain.model.Cozinha;
import com.algafoodapi.domain.model.Restaurante;
import com.algafoodapi.domain.repository.CozinhaRepository;
import com.algafoodapi.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class InclusaoCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodapiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Brasileira");

        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Japonesa");

        cozinha =  cozinhaRepository.adicionar(cozinha);
        cozinha1 = cozinhaRepository.adicionar(cozinha1);

         System.out.printf("%d - %s\n", cozinha.getId(), cozinha.getNome());
         System.out.printf("%d - %s\n", cozinha1.getId(), cozinha1.getNome());
        }
    }

*/