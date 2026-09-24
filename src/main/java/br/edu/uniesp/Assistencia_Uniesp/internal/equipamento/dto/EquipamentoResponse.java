package br.edu.uniesp.Assistencia_Uniesp.internal.equipamento.dto;

import br.edu.uniesp.Assistencia_Uniesp.internal.equipamento.entity.Equipamento;

public record EquipamentoResponse(
        Long id,
        String tipo,
        String marca,
        String modelo,
        String numeroSerie,
        String descricaoProblema,
        Boolean ativo,
        Long clienteId,
        String clienteNome
) {
    public EquipamentoResponse(Equipamento equipamento) {
        this(
                equipamento.getId(),
                equipamento.getTipo(),
                equipamento.getMarca(),
                equipamento.getModelo(),
                equipamento.getNumeroSerie(),
                equipamento.getDescricaoProblema(),
                equipamento.getAtivo(),
                equipamento.getCliente().getId(),
                equipamento.getCliente().getNome()
        );
    }
}
