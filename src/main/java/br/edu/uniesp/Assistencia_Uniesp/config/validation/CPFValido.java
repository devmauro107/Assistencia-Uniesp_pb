package br.edu.uniesp.Assistencia_Uniesp.config.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = CPFValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CPFValido {

    String message() default "{cliente.cpf.invalido}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
