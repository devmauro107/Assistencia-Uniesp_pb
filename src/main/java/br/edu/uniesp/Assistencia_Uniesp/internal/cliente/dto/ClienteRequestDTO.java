package br.edu.uniesp.Assistencia_Uniesp.internal.cliente.dto;

import br.edu.uniesp.Assistencia_Uniesp.config.validation.CPFValido;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(
        @NotBlank(message = "{cliente.nome.obrigatorio}")
        @Size(min = 3, max = 100, message = "{cliente.nome.tamanho}")
        String nome,

        @NotBlank(message = "{cliente.cpf.obrigatorio}")
        @CPFValido
        String cpf,

        @NotBlank(message = "{cliente.email.obrigatorio}")
        @Email(message = "{cliente.email.invalido}")
        String email
) {}