package helpdesk.controllers;

import helpdesk.models.Chamado;
import helpdesk.models.dtos.ChamadoDTO;
import helpdesk.services.ChamadoService;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/chamados")
public record ChamadoController(ChamadoService chamadoService) {

    @PostMapping
    public ResponseEntity<ChamadoDTO> criarChamado(@Valid @RequestBody ChamadoDTO chamadoDTO) {
        Chamado chamado = chamadoService.criarChamado(chamadoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(chamado.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChamadoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ChamadoDTO(chamadoService.buscarPorId(id)));
    }

    @GetMapping
    public ResponseEntity<Page<ChamadoDTO>> buscarTodosChamados(Pageable pageable) {
        Page<Chamado> page = chamadoService.buscarTodosChamados(pageable);
        Page<ChamadoDTO> pageDTO = page.map(ChamadoDTO::new);

        return ResponseEntity.status(HttpStatus.OK).body(pageDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChamadoDTO> atualizarChamado(@PathVariable Long id, @Valid @RequestBody ChamadoDTO chamadoDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(new ChamadoDTO(chamadoService.atualizarChamado(id, chamadoDTO)));
    }
}
