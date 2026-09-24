package br.edu.uniesp.Assistencia_Uniesp.internal.equipamento.service;

import br.edu.uniesp.Assistencia_Uniesp.config.exception.RecursoNaoEncontradoException;
import br.edu.uniesp.Assistencia_Uniesp.config.exception.RegraNegocioException;
import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.entity.Cliente;
import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.repository.ClienteRepository;
import br.edu.uniesp.Assistencia_Uniesp.internal.equipamento.dto.EquipamentoRequest;
import br.edu.uniesp.Assistencia_Uniesp.internal.equipamento.dto.EquipamentoResponse;
import br.edu.uniesp.Assistencia_Uniesp.internal.equipamento.entity.Equipamento;
import br.edu.uniesp.Assistencia_Uniesp.internal.equipamento.repository.EquipamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public EquipamentoResponse cadastrar(EquipamentoRequest dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .filter(Cliente::getAtivo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente ativo não encontrado com o ID: " + dto.clienteId()));

        String numeroSerieSanitizado = sanitizarNumeroSerie(dto.numeroSerie());

        if (numeroSerieSanitizado != null && equipamentoRepository.existsByNumeroSerie(numeroSerieSanitizado)) {
            throw new RegraNegocioException("Já existe um equipamento cadastrado com o número de série informado.");
        }

        Equipamento equipamento = new Equipamento(
                dto.tipo().trim(),
                dto.marca().trim(),
                dto.modelo().trim(),
                numeroSerieSanitizado,
                dto.descricaoProblema() != null ? dto.descricaoProblema().trim() : null,
                cliente
        );

        Equipamento salvo = equipamentoRepository.save(equipamento);
        return new EquipamentoResponse(salvo);
    }

    @Transactional
    public EquipamentoResponse atualizar(Long id, EquipamentoRequest dto) {
        Equipamento equipamento = equipamentoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Equipamento não encontrado com o ID: " + id));

        Cliente cliente = equipamento.getCliente();
        if (!cliente.getId().equals(dto.clienteId())) {
            cliente = clienteRepository.findById(dto.clienteId())
                    .filter(Cliente::getAtivo)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente ativo não encontrado com o ID: " + dto.clienteId()));
        }

        String numeroSerieSanitizado = sanitizarNumeroSerie(dto.numeroSerie());

        if (numeroSerieSanitizado != null && equipamentoRepository.existsByNumeroSerieAndIdNot(numeroSerieSanitizado, id)) {
            throw new RegraNegocioException("Já existe outro equipamento cadastrado com o número de série informado.");
        }

        equipamento.atualizarDados(
                dto.tipo().trim(),
                dto.marca().trim(),
                dto.modelo().trim(),
                numeroSerieSanitizado,
                dto.descricaoProblema() != null ? dto.descricaoProblema().trim() : null,
                cliente
        );

        return new EquipamentoResponse(equipamento);
    }


    public Page<EquipamentoResponse> listarTodos(Pageable pageable) {
        return equipamentoRepository.findAllByAtivoTrue(pageable)
                .map(EquipamentoResponse::new);
    }

    public Page<EquipamentoResponse> listarPorCliente(Long clienteId, Pageable pageable) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado com o ID: " + clienteId);
        }
        return equipamentoRepository.findAllByClienteIdAndAtivoTrue(clienteId, pageable)
                .map(EquipamentoResponse::new);
    }

    public EquipamentoResponse buscarPorId(Long id) {
        Equipamento equipamento = equipamentoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Equipamento não encontrado com o ID: " + id));
        return new EquipamentoResponse(equipamento);
    }

    @Transactional
    public void inativar(Long id) {
        Equipamento equipamento = equipamentoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Equipamento não encontrado com o ID: " + id));

        equipamento.inativar();
    }

    private String sanitizarNumeroSerie(String numeroSerie) {
        if (numeroSerie == null || numeroSerie.isBlank()) {
            return null;
        }
        return numeroSerie.trim();
    }
}
