/*package com.algafoodapi.jpa;

import com.algafoodapi.AlgafoodapiApplication;
import com.algafoodapi.domain.model.Cozinha;
import com.algafoodapi.domain.model.Restaurante;
import com.algafoodapi.domain.repository.CozinhaRepository;
import com.algafoodapi.domain.repository.RestauranteRepository;
import com.algafoodapi.infrastructure.repository.RestauranteRepositoryImpl;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.List;

public class ConsultaCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodapiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

        List<Cozinha> todasCozinhas = cozinhaRepository.listar();

        for (Cozinha cozinha : todasCozinhas) {
            System.out.println(cozinha.getNome());
        }
    }
}
*/