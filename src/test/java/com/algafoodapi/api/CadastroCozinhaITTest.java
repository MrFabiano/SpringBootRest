package com.algafoodapi.api;

import com.algafoodapi.domain.model.Cozinha;
import com.algafoodapi.domain.repository.CozinhaRepository;
import com.algafoodapi.util.DataBaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaITTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DataBaseCleaner dataBaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private int quantidadeCozinhasCadastradas;
    private static Integer COZINHA_INEXISTENTE = 100;
    private File jsonCorretoCozinhaChinesa;


    //@Autowired
    //private Flyway flyway;

    @Before
    public void setUp() throws FileNotFoundException {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";
        jsonCorretoCozinhaChinesa = ResourceUtils.getFile("src/test/resources/json/correto/cozinha-chinesa.json");
       // flyway.migrate();
        dataBaseCleaner.clearTables();
        preparaDadosCozinha();

    }

    @Test


    public void testDeveRetornar200(){
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testRetornar200(){
        RestAssured.given()
                .pathParam("cozinhaId", 1)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.OK.value());
                //.body("nome", equalTo(cozinhaAmericana.getNome()));
    }
    @Test
    public void testDeveRetornar404(){
        RestAssured.given()
                .pathParam("cozinhaId", COZINHA_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testDeveConter2Cozinhas(){
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", Matchers.hasSize(quantidadeCozinhasCadastradas));
               // .body("nome", Matchers.hasItems("Indiana", "Tailandesa"));
    }

    @Test
    public void testDeveRetornar201Created(){
        RestAssured.given()
                .body(jsonCorretoCozinhaChinesa)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());

    }


    public Object preparaDadosCozinha(){
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Tailandesa");
        cozinhaRepository.save(cozinha);

        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Americana");
        cozinhaRepository.save(cozinha1);

        quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
        return null;
    }
}
