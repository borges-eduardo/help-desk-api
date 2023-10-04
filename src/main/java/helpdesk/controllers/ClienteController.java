package helpdesk.controllers;

import helpdesk.models.Cliente;
import helpdesk.models.dtos.ClienteDTO;
import helpdesk.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/clientes")
public record ClienteController(ClienteService clienteService) {

    @PostMapping
    public ResponseEntity<ClienteDTO> criarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente novoCliente = clienteService.criarCliente(clienteDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoCliente.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(uri).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ClienteDTO(clienteService.buscarClientePorId(id)));
    }

    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> buscarTodosClientes(Pageable pageable) {
        Page<Cliente> page = clienteService.buscarTodosClientes(pageable);
        Page<ClienteDTO> pageDTO = page.map(ClienteDTO::new);

        return ResponseEntity.status(HttpStatus.OK).body(pageDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> atualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(new ClienteDTO(clienteService.atualizarCliente(id, clienteDTO)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        clienteService.excluirCliente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
