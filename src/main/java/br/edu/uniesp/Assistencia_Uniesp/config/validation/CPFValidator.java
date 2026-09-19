package br.edu.uniesp.Assistencia_Uniesp.config.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPFValido, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // Deixa a obrigatoriedade para o @NotBlank
        }

        String cpfLimpo = value.replaceAll("\\D", "");

        if (cpfLimpo.length() != 11 || cpfLimpo.matches("(\\d)\\1{10}")) {
            return false;
        }

        return calcularDigito(cpfLimpo, 9) == (cpfLimpo.charAt(9) - '0')
                && calcularDigito(cpfLimpo, 10) == (cpfLimpo.charAt(10) - '0');
    }

    private int calcularDigito(String str, int pesoMax) {
        int soma = 0;
        int peso = pesoMax + 1;
        for (int i = 0; i < pesoMax; i++) {
            soma += (str.charAt(i) - '0') * (peso - i);
        }
        int resto = 11 - (soma % 11);
        return (resto >= 10) ? 0 : resto;
    }
}
