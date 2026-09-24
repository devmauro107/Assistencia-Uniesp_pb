package br.edu.uniesp.Assistencia_Uniesp.internal.cliente.service;

import br.edu.uniesp.Assistencia_Uniesp.config.exception.RecursoNaoEncontradoException;
import br.edu.uniesp.Assistencia_Uniesp.config.exception.RegraNegocioException;
import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.dto.ClienteRequest;
import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.dto.ClienteResponse;
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
    public ClienteResponse cadastrar(ClienteRequest dto) {
        String cpfLimpo = sanitizarCpf(dto.cpf());
        String emailSanitizado = sanitizarEmail(dto.email());
        String nomeSanitizado = dto.nome().trim();

        validarUnicidadeCadastro(cpfLimpo, emailSanitizado);

        Cliente novoCliente = new Cliente(nomeSanitizado, cpfLimpo, emailSanitizado);

        Cliente salvo = clienteRepository.save(novoCliente);
        return new ClienteResponse(salvo);
    }

    @Transactional(readOnly = true)
    public Page<ClienteResponse> listarTodos(Pageable pageable) {
        return clienteRepository.findAll(pageable)
                .map(ClienteResponse::new);
    }

    public ClienteResponse buscarPorId(Long id) {
        Cliente cliente = buscarEntidadePorId(id);
        return new ClienteResponse(cliente);
    }

    @Transactional
    public ClienteResponse atualizar(Long id, ClienteRequest dto) {
        Cliente cliente = clienteRepository.findById(id)
                .filter(Cliente::getAtivo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado ou inativo com o ID: " + id));

        cliente.atualizarDados(dto.nome().trim(), dto.email().trim().toLowerCase());

        return new ClienteResponse(cliente);
    }

    @Transactional
    public void inativar(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .filter(Cliente::getAtivo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado ou inativo com o ID: " + id));

        cliente.inativar();
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
