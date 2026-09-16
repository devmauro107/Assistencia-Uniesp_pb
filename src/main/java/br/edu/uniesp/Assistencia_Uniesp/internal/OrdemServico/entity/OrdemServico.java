package br.edu.uniesp.Assistencia_Uniesp.internal.OrdemServico.entity;

import br.edu.uniesp.Assistencia_Uniesp.internal.OrdemServico.enums.Prioridade;
import br.edu.uniesp.Assistencia_Uniesp.internal.OrdemServico.enums.StatusOrdemServico;
import br.edu.uniesp.Assistencia_Uniesp.internal.equipamento.entity.Equipamento;
import br.edu.uniesp.Assistencia_Uniesp.internal.tecnico.entity.Tecnico;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ordem_serviço")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusOrdemServico status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Prioridade prioridade;

    @Column(name = "data_abertura", nullable = false)
    private LocalDateTime dataAbertura;

    @Column(name = "data_conclusão")
    private LocalDateTime dataConclusao;

    @Column(name = "descrição_defeito", nullable = false, columnDefinition = "TEXT")
    private String descricaoDefeito;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipamento_id", nullable = false)
    private Equipamento equipamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "técnico_id")
    private Tecnico tecnico;
}
