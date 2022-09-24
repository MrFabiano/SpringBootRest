package com.algafoodapi.config.openapi;

import com.algafoodapi.domain.dto.CozinhaDTO;
import com.algafoodapi.domain.exception.handlerexception.Problem;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;


@EnableSwagger2
@Configuration
@EnableWebMvc
public class SpringFoxConfig implements WebMvcConfigurer {

    @Bean
    public Docket apiDocket() {
        var typeResolver = new TypeResolver();

            return new Docket(DocumentationType.SWAGGER_2)
                    .groupName("V1")
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.algafoodapi.controller"))
                    .paths(PathSelectors.ant("/v1/**"))
                    .build()
                    .useDefaultResponseMessages(false)
                    .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
                    .globalResponseMessage(RequestMethod.POST, globalGetResponseMessagesPost())
                    .globalResponseMessage(RequestMethod.PUT, globalGetResponseMessagesPut())
                    .globalResponseMessage(RequestMethod.DELETE, globalGetResponseMessagesDelete())
//                    .globalOperationParameters(Arrays.asList(
//                            new ParameterBuilder()
//                                    .name("campos")
//                                    .description("Nomes separados por virgula")
//                                    .parameterType("query")
//                                    .modelRef(new ModelRef("string"))
//                                    .build()
//                    ))
                    .additionalModels(typeResolver.resolve(Problem.class))
                    .ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class, File.class)
                    .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                    .alternateTypeRules(AlternateTypeRules.newRule(
                            typeResolver.resolve(Page.class, CozinhaDTO.class), CozinhasModelOpenApi.class
                    ))
                    .apiInfo(apiInfo())
                    .tags(new Tag("Cidades", "Gerencia as cidades"),
                            new Tag("Grupo", "Gerencia os grupos de usuários"),
                            new Tag("Cozinha", "Gerencia as cozinhas"),
                            new Tag("Forma-Pagamento", "Gerencia as formas de pagamento"),
                            new Tag("Usuario", "Gerencia os Usuários"),
                            new Tag("Pedido", "Gerencia os pedidos"),
                            new Tag("Restaurantes", "Gerencia os restaurantes"),
                            new Tag("Estado", "Gerencia os estados"),
                            new Tag("Fluxo-Pedido", "Gerencia o fluxo pedido"),
                            new Tag("Usuario-Responsavel", "Gerencia os usuarios responsaveis"),
                            new Tag("Produto", "Gerencia os produtos"),
                            new Tag("Foto-Produto", "Gerencia as fotos do produto"),
                            new Tag("Usuario-Grupo", "Gerencia os usuarios ao grupo"),
                            new Tag("Estaticas", "Gerencia as estaticas de vendas"));



    }

    private List<ResponseMessage> globalGetResponseMessages(){
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(Integer.parseInt(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                        .message("Erro interno do servidor")
                        .build(),
                new ResponseMessageBuilder()
                        .code(Integer.parseInt(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value())))
                        .message("Não encontrado")
                        .build()
                );
    }

    private List<ResponseMessage> globalGetResponseMessagesPost(){
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(Integer.parseInt(String.valueOf(HttpStatus.BAD_REQUEST.value())))
                        .message("Requesiçao invalida(tente novamente)")
                        .build(),
                new ResponseMessageBuilder()
                        .code(Integer.parseInt(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                        .message("Erro interno do servidor")
                        .build(),
           new ResponseMessageBuilder()
                .code(Integer.parseInt(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value())))
                .message("Recurso nao possui representaçao que poderia ser aceita pelo consumidor")
                .build(),

        new ResponseMessageBuilder()
                .code(Integer.parseInt(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())))
                .message("Requisiçao recusada porque o corpo esta em um formato nao suportado")
                .build()
                );
    }

    private List<ResponseMessage> globalGetResponseMessagesPut(){
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(Integer.parseInt(String.valueOf(HttpStatus.BAD_REQUEST.value())))
                        .message("Requesiçao invalida(tente novamente)")
                        .build(),
                new ResponseMessageBuilder()
                        .code(Integer.parseInt(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                        .message("Erro interno do servidor")
                        .build(),
                new ResponseMessageBuilder()
                        .code(Integer.parseInt(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value())))
                        .message("Recurso nao possui representaçao que poderia ser aceita pelo consumidor")
                        .build(),

                new ResponseMessageBuilder()
                        .code(Integer.parseInt(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())))
                        .message("Requisiçao recusada porque o corpo esta em um formato nao suportado")
                        .build()
        );
    }

    private List<ResponseMessage> globalGetResponseMessagesDelete(){
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(Integer.parseInt(String.valueOf(HttpStatus.BAD_REQUEST.value())))
                        .message("Requesiçao invalida(tente novamente)")
                        .build(),
                new ResponseMessageBuilder()
                        .code(Integer.parseInt(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                        .message("Erro interno do servidor")
                        .build()
        );
    }


    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("AlgaFood - API")
                .description("API - aberta para clientes e restaurantes")
                .version("1")
                .contact(new Contact("AlgaFood", "Https://www.algafoood.com.br", "contato@algafood.com"))
                .build();
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
