package br.edu.uniesp.Assistencia_Uniesp.internal.equipamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EquipamentoRequest(
        @NotBlank(message = "{equipamento.tipo.obrigatorio}")
        @Size(max = 80, message = "{equipamento.tipo.tamanho}")
        String tipo,

        @NotBlank(message = "{equipamento.marca.obrigatorio}")
        @Size(max = 80, message = "{equipamento.marca.tamanho}")
        String marca,

        @NotBlank(message = "{equipamento.modelo.obrigatorio}")
        @Size(max = 100, message = "{equipamento.modelo.tamanho}")
        String modelo,

        @Size(max = 100, message = "{equipamento.numero_serie.tamanho}")
        String numeroSerie,

        @Size(max = 255, message = "{equipamento.descricao_problema.tamanho}")
        String descricaoProblema,

        @NotNull(message = "{equipamento.cliente_id.obrigatorio}")
        Long clienteId
) {}