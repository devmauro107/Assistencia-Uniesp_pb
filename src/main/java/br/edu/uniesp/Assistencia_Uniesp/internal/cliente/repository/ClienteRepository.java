package br.edu.uniesp.Assistencia_Uniesp.internal.cliente.repository;

import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    Optional<Cliente> findByCpf(String cpf);
}
