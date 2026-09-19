package br.edu.uniesp.Assistencia_Uniesp.internal.cliente.service;

import br.edu.uniesp.Assistencia_Uniesp.config.exception.RecursoNaoEncontradoException;
import br.edu.uniesp.Assistencia_Uniesp.config.exception.RegraNegocioException;
import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.dto.ClienteRequestDTO;
import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.dto.ClienteResponseDTO;
import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.entity.Cliente;
import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Transactional
    public ClienteResponseDTO cadastrar(ClienteRequestDTO dto) {
        String cpfLimpo = sanitizarCpf(dto.cpf());
        String emailSanitizado = sanitizarEmail(dto.email());
        String nomeSanitizado = dto.nome().trim();

        validarUnicidadeCadastro(cpfLimpo, emailSanitizado);

        Cliente novoCliente = new Cliente(nomeSanitizado, cpfLimpo, emailSanitizado);
        novoCliente.setNome(nomeSanitizado);
        novoCliente.setCpf(cpfLimpo);
        novoCliente.setEmail(emailSanitizado);
        novoCliente.setAtivo(Boolean.TRUE);

        Cliente salvo = clienteRepository.save(novoCliente);
        return new ClienteResponseDTO(salvo);
    }

    @Transactional(readOnly = true)
    public Page<ClienteResponseDTO> listarTodos(Pageable pageable) {
        return clienteRepository.findAll(pageable)
                .map(ClienteResponseDTO::new);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = buscarEntidadePorId(id);
        return new ClienteResponseDTO(cliente);
    }

    @Transactional
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        Cliente clienteExistente = buscarEntidadePorId(id);

        String cpfLimpo = sanitizarCpf(dto.cpf());
        String emailSanitizado = sanitizarEmail(dto.email());

        if (!clienteExistente.getCpf().equals(cpfLimpo) && clienteRepository.existsByCpf(cpfLimpo)) {
            throw new RegraNegocioException("CPF já cadastrado para outro cliente.");
        }

        if (!clienteExistente.getEmail().equalsIgnoreCase(emailSanitizado) && clienteRepository.existsByEmail(emailSanitizado)) {
            throw new RegraNegocioException("E-mail já cadastrado para outro cliente.");
        }

        clienteExistente.setNome(dto.nome().trim());
        clienteExistente.setCpf(cpfLimpo);
        clienteExistente.setEmail(emailSanitizado);

        return new ClienteResponseDTO(clienteExistente);
    }

    @Transactional
    public void inativar(Long id) {
        Cliente cliente = buscarEntidadePorId(id);
        cliente.setAtivo(Boolean.FALSE);
    }

    private Cliente buscarEntidadePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com ID: " + id));
    }

    private void validarUnicidadeCadastro(String cpf, String email) {
        if (clienteRepository.existsByCpf(cpf)) {
            throw new RegraNegocioException("Já existe um cliente cadastrado com este CPF.");
        }
        if (clienteRepository.existsByEmail(email)) {
            throw new RegraNegocioException("Já existe um cliente cadastrado com este e-mail.");
        }
    }

    private String sanitizarCpf(String cpf) {
        return cpf == null ? null : cpf.replaceAll("\\D", "");
    }

    private String sanitizarEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}
