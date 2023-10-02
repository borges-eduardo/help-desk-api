package helpdesk.controllers;

import helpdesk.models.Tecnico;
import helpdesk.models.dtos.TecnicoDTO;
import helpdesk.services.TecnicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tecnicos")
public record TecnicoController(TecnicoService tecnicoService) {

    @PostMapping
    public ResponseEntity<TecnicoDTO> CriarTecnico(@Valid @RequestBody TecnicoDTO tecnicoDTO) {
        Tecnico novoTecnico = tecnicoService.CriarTecnico(tecnicoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoTecnico.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(uri).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> BuscarTecnicoPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new TecnicoDTO(tecnicoService.BuscarTecnicoPorId(id)));
    }

    @GetMapping
    public ResponseEntity<Page<TecnicoDTO>> BuscarTodosTecnicos(Pageable pageable) {
        Page<Tecnico> page = tecnicoService.BuscarTodosTecnicos(pageable);
        Page<TecnicoDTO> pageDTO = page.map(TecnicoDTO::new);

        return ResponseEntity.status(HttpStatus.OK).body(pageDTO);
    }

}
