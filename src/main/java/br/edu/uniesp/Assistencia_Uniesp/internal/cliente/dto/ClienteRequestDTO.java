package br.edu.uniesp.Assistencia_Uniesp.internal.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteRequestDTO(
        @NotBlank(message = "{cliente.nome.obrigatorio}")
        @Size(min = 3, max = 100, message = "{cliente.nome.tamanho}")
        String nome,

        @NotBlank(message = "{cliente.cpf.obrigatorio}")
        @CPF(message = "{cliente.cpf.invalido}")
        String cpf,

        @NotBlank(message = "{cliente.email.obrigatorio}")
        @Email(message = "{cliente.email.invalido}")
        String email
) {}