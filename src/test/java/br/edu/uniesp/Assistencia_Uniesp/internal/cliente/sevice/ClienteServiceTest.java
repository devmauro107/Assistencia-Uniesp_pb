package br.edu.uniesp.Assistencia_Uniesp.internal.cliente.sevice;


import br.edu.uniesp.Assistencia_Uniesp.config.exception.RecursoNaoEncontradoException;
import br.edu.uniesp.Assistencia_Uniesp.config.exception.RegraNegocioException;
import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.dto.ClienteRequestDTO;
import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.dto.ClienteResponseDTO;
import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.entity.Cliente;
import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.repository.ClienteRepository;
import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.service.ClienteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("Deve cadastrar cliente com sucesso e sanitizar CPF e e-mail")
    void deveCadastrarClienteComSucesso() {
        ClienteRequestDTO dto = new ClienteRequestDTO("  Mauro Oliveira  ", "123.456.789-00", "  TESTE@EMAIL.COM  ");
        when(clienteRepository.existsByCpf("12345678900")).thenReturn(false);
        when(clienteRepository.existsByEmail("teste@email.com")).thenReturn(false);

        Cliente clienteSalvo = new Cliente("Mauro Oliveira", "12345678900", "teste@email.com");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteSalvo);

        ClienteResponseDTO resultado = clienteService.cadastrar(dto);

        assertNotNull(resultado);
        assertEquals("Mauro Oliveira", resultado.nome());
        assertEquals("12345678900", resultado.cpf());
        assertEquals("teste@email.com", resultado.email());

        ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);
        verify(clienteRepository).save(clienteCaptor.capture());
        Cliente clienteCapturado = clienteCaptor.getValue();
        assertEquals("12345678900", clienteCapturado.getCpf());
        assertEquals("teste@email.com", clienteCapturado.getEmail());
    }

    @Test
    @DisplayName("Deve lançar RegraNegocioException quando CPF já estiver cadastrado")
    void deveLancarExcecaoQuandoCpfDuplicado() {
        ClienteRequestDTO dto = new ClienteRequestDTO("Mauro", "123.456.789-00", "teste@email.com");
        when(clienteRepository.existsByCpf("12345678900")).thenReturn(true);

        assertThrows(RegraNegocioException.class, () -> clienteService.cadastrar(dto));
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar RecursoNaoEncontradoException ao buscar ID inexistente")
    void deveLancarExcecaoAoBuscarIdInexistente() {
        Long idInexistente = 99L;
        when(clienteRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> clienteService.buscarPorId(idInexistente));
    }
}
