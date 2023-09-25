package helpdesk.controllers;

import helpdesk.models.Tecnico;
import helpdesk.models.dtos.TecnicoDTO;
import helpdesk.services.TecnicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/tecnicos")
public record TecnicoController(TecnicoService tecnicoService) {

    @PostMapping
    public ResponseEntity<TecnicoDTO> salvarTecnico(@Valid @RequestBody TecnicoDTO tecnicoDTO) {
        Tecnico newObj = tecnicoService.salvarTecnico(tecnicoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(uri).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new TecnicoDTO(tecnicoService.buscarPorId(id)));
    }
}
