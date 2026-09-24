package br.edu.uniesp.Assistencia_Uniesp.internal.equipamento.controller;


import br.edu.uniesp.Assistencia_Uniesp.internal.equipamento.dto.EquipamentoRequest;
import br.edu.uniesp.Assistencia_Uniesp.internal.equipamento.dto.EquipamentoResponse;
import br.edu.uniesp.Assistencia_Uniesp.internal.equipamento.service.EquipamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/equipamentos")
@RequiredArgsConstructor
public class EquipamentoController {

    private final EquipamentoService equipamentoService;

    @PostMapping
    public ResponseEntity<EquipamentoResponse> cadastrar(
            @RequestBody @Valid EquipamentoRequest dto,
            UriComponentsBuilder uriBuilder
    ) {
        EquipamentoResponse response = equipamentoService.cadastrar(dto);
        URI uri = uriBuilder.path("/equipamentos/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<EquipamentoResponse>> listarTodos(
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(equipamentoService.listarTodos(pageable));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Page<EquipamentoResponse>> listarPorCliente(
            @PathVariable Long clienteId,
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(equipamentoService.listarPorCliente(clienteId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(equipamentoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid EquipamentoRequest dto
    ) {
        return ResponseEntity.ok(equipamentoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        equipamentoService.inativar(id);
        return ResponseEntity.noContent().build();
    }
}
