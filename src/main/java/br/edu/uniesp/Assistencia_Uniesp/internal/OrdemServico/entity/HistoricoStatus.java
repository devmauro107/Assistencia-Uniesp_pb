package br.edu.uniesp.Assistencia_Uniesp.internal.OrdemServico.entity;

import br.edu.uniesp.Assistencia_Uniesp.internal.OrdemServico.enums.StatusOrdemServico;
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
@Table(name = "tb_historico_status")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class HistoricoStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_anterior", length = 30)
    private StatusOrdemServico statusAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_novo", nullable = false, length = 30)
    private StatusOrdemServico statusNovo;

    @Column(name = "data_mudanca", nullable = false)
    private LocalDateTime dataMudanca;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ordem_servico_id", nullable = false)
    private OrdemServico ordemServico;
}
