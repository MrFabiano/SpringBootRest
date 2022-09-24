package com.algafoodapi.domain.exception.handlerexception;

import com.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algafoodapi.domain.exception.EntidadeNaoEncontraException;
import com.algafoodapi.domain.exception.NegocioException;
import com.algafoodapi.enums.ProblemType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String USER_MESSAGE = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o" +
            "problema persistir, entre em contato com o administrador do sistema";

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders  headers, HttpStatus status,
                                                         WebRequest request) {

        return hanldeValidationInternal(ex,  headers, status, request, ex.getBindingResult());
    }

    protected ResponseEntity<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex,  HttpHeaders  headers,
                                                                             HttpStatus status, WebRequest request) {
        return hanldeValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }
    
    private ResponseEntity<Object> handledeValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detalhe = "Um ou mais campos estão inválidos. Faça o preenchimento e tente novamente mais tarde";


        List<Problem.Object> problemFields = bindingResult.getAllErrors().stream()
                .map(objectError -> {

                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();
                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return Problem.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        Problem problem = createProblemBuilder(status, problemType, detalhe)
                .userMessage(detalhe)
                .objects(problemFields)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detalhe = "Um ou mais campos estão inválidos. Faça o preenchimento e tente novamente mais tarde";

        BindingResult bindingResult = ex.getBindingResult();
        List<Problem.Object> problemFields = bindingResult.getAllErrors().stream()
                .map(objectError -> {

                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();
                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return Problem.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        Problem problem = createProblemBuilder(status, problemType, detalhe)
                .userMessage(detalhe)
                .objects(problemFields)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }
    
    private ResponseEntity<Object> hanldeValidationInternal(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request, BindingResult bindingResult) {
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detalhe = "Um ou mais campos estão inválidos. Faça o preenchimento e tente novamente mais tarde";


        List<Problem.Object> problemFields = bindingResult.getAllErrors().stream()
                .map(objectError -> {

                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();
                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return Problem.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        Problem problem = createProblemBuilder(status, problemType, detalhe)
                .userMessage(detalhe)
                .objects(problemFields)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCausa = ExceptionUtils.getRootCause(ex);
        if(rootCausa instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCausa, headers, status, request);
        } else if(rootCausa instanceof PropertyBindingException){
            return handlePropertyBindingException((PropertyBindingException) rootCausa, headers, status, request);
        }

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detalhe = "O corpo da requisição está invalido, Verifique a sintaxe.";

        Problem problem = createProblemBuilder(status, problemType, detalhe)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

     private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
                                                                 HttpStatus status, WebRequest request){
        String path = ex.getPath().stream()
                .map(r -> r.getFieldName())
                .collect(Collectors.joining("."));

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detalhe = String.format("A propriedade '%s' recebeu um valor '%s' "
                + "que é de um tipo invalido. Corrija e informe um valor compativel com o tipo %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

         Problem problem = createProblemBuilder(status, problemType, detalhe)
                 .userMessage(USER_MESSAGE)
                 .build();

         return handleExceptionInternal(ex, problem, headers, status, request);
     }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request){
        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detalhe = String.format("A propriedade '%s' não existe '%s' "
                        + "que é de um tipo invalido. Corrija e informe um valor compativel com o tipo",
                path, ex.getPropertyName());

        Problem problem = createProblemBuilder(status, problemType, detalhe)
                .userMessage(USER_MESSAGE)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

       private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders headers,
                                                                                HttpStatus status, WebRequest request){
        if(ex instanceof MethodArgumentTypeMismatchException){
            return handleMethodArgumentTypeMismatchException(ex, headers, status, request);
        }
        ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
        String detalhe = String.format("O parametro de URL '%s' recebeu o valor '%s' que é de um tipo" +
                "invalido, Corrija e informe um valor compativel com o tipo %s",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        Problem problem = createProblemBuilder(status, problemType, detalhe)
                .userMessage(USER_MESSAGE)
                .build();

           return handleExceptionInternal(ex, problem, headers, status, request);
       }

    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                 HttpStatus status, WebRequest request){
        if(ex instanceof NoHandlerFoundException){
            return handleNoHandlerFoundException(ex, headers, status, request);
        }
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detalhe = String.format("O recuros '%s' que você tentou acessar é inexistente",
                ex.getRequestURL());

        Problem problem = createProblemBuilder(status, problemType, detalhe)
                .userMessage(USER_MESSAGE)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerExceptionHandler(Exception ex, WebRequest request){

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.ERRO_SISTEMA;
        String detalhe = USER_MESSAGE;
                ex.printStackTrace();

        Problem problem = createProblemBuilder(status, problemType, detalhe)
                .userMessage(detalhe)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontraException.class)
    public ResponseEntity<?> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontraException ex,
                                                                  WebRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detalhe = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detalhe)
                .userMessage(detalhe)
                .build();
       /* Problem problem = Problem.builder()
                .status(status.value())
                .tipo("https://algafood.com.br/entidade-nao-encontrada")
                .titulo("Entidade não encontrada")
                .detalhe(ex.getMessage())
                .build();*/
        return handleExceptionInternal(ex, problem,
                new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> tratarNegocioException(NegocioException ex, WebRequest request){

               HttpStatus status = HttpStatus.BAD_REQUEST;
               ProblemType problemType = ProblemType.ENTIDADE_NAO_PERMITIDA;
               String detalhe = ex.getMessage();

               Problem problem = createProblemBuilder(status, problemType,detalhe)
                       .userMessage(detalhe)
                       .build();

               return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> tratarEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request){

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
        String detalhe = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detalhe)
                .userMessage(detalhe)
                .build();

        return handleExceptionInternal(ex, problem,
                new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if(body == null){
            body = Problem.builder()
                    .dataAtual(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss")))
                    .titulo(status.getReasonPhrase())
                    .userMessage(USER_MESSAGE)
                    .status(status.value())
                    .build();
        }else if(body instanceof String)//intanciado um corpo para string "instanceof"
        body = Problem.builder()
                .dataAtual(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss")))
                .titulo((String) body)
                .status(status.value())
                .userMessage(USER_MESSAGE)
                .build();

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status,
                                                        ProblemType problemType, String detalhe){

        return Problem.builder()
                .status(status.value())
                .tipo(problemType.getUri())
                .titulo(problemType.getTitulo())
                .dataAtual(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss")))
                .detalhe(detalhe);
    }


    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(r -> r.getFieldName())
                .collect(Collectors.joining("."));
    }
}
