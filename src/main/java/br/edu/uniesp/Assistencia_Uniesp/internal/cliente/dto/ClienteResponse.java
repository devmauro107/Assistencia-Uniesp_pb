package br.edu.uniesp.Assistencia_Uniesp.internal.cliente.dto;

import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.entity.Cliente;

public record ClienteResponse(
        Long id,
        String nome,
        String cpf,
        String email,
        Boolean ativo
) {
    public ClienteResponse(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getEmail(),
                cliente.getAtivo()
        );
    }
}
