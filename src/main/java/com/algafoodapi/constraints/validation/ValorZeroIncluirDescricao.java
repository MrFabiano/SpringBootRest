package com.algafoodapi.constraints.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ValorZeroIncuirDescricaoValidatior.class})
public @interface ValorZeroIncluirDescricao {


    String message() default "descrição obrigatoria valida";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String valorField();
    String descricaoField();
    String descricaoObrigatoria();

}
