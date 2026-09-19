package br.edu.uniesp.Assistencia_Uniesp.internal.cliente.controller;

import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.dto.ClienteRequestDTO;
import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.dto.ClienteResponseDTO;
import br.edu.uniesp.Assistencia_Uniesp.internal.cliente.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteResponseDTO> cadastrar(
            @RequestBody @Valid ClienteRequestDTO dto,
            UriComponentsBuilder uriBuilder
    ) {
        ClienteResponseDTO response = clienteService.cadastrar(dto);
        URI uri = uriBuilder.path("/clientes/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(clienteService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ClienteRequestDTO dto
    ) {
        return ResponseEntity.ok(clienteService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        clienteService.inativar(id);
        return ResponseEntity.noContent().build();
    }
}
