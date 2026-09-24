package br.edu.uniesp.Assistencia_Uniesp.internal.equipamento.repository;

import br.edu.uniesp.Assistencia_Uniesp.internal.equipamento.entity.Equipamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {

    // Consulta otimizada para validação de unicidade de número de série
    boolean existsByNumeroSerie(String numeroSerie);

    // Listagem paginada apenas dos equipamentos com status ativo
    Page<Equipamento> findAllByAtivoTrue(Pageable pageable);

    // Listagem dos equipamentos pertencentes a um cliente específico
    Page<Equipamento> findAllByClienteIdAndAtivoTrue(Long clienteId, Pageable pageable);

    // Busca detalhada respeitando soft delete
    Optional<Equipamento> findByIdAndAtivoTrue(Long id);

    // Verifica se existe um equipamento com o número de série informado, exceto o equipamento com o ID especificado
    boolean existsByNumeroSerieAndIdNot(String numeroSerie, Long id);
}
