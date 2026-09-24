package br.edu.uniesp.Assistencia_Uniesp.internal.equipamento.entity;

import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.entity.Cliente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_equipamento")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String tipo;

    @Column(nullable = false, length = 80)
    private String marca;

    @Column(nullable = false, length = 100)
    private String modelo;

    @Column(name = "numero_serie", unique = true, length = 100)
    private String numeroSerie;

    @Column(length = 255)
    private String descricaoProblema;

    @Column(nullable = false)
    private Boolean ativo = Boolean.TRUE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    public Equipamento(String tipo, String marca, String modelo, String numeroSerie, String descricaoProblema, Cliente cliente) {
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        this.descricaoProblema = descricaoProblema;
        this.cliente = cliente;
        this.ativo = Boolean.TRUE;
    }

    public void atualizarDados(String tipo, String marca, String modelo, String numeroSerie, String descricaoProblema, Cliente cliente) {
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        this.descricaoProblema = descricaoProblema;
        if (cliente != null) {
            this.cliente = cliente;
        }
    }

    public void inativar() {
        this.ativo = Boolean.FALSE;
    }
}
