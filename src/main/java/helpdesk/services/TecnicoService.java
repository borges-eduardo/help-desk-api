package helpdesk.services;

import helpdesk.models.Tecnico;
import helpdesk.models.dtos.TecnicoDTO;
import helpdesk.repositories.TecnicoRepository;
import helpdesk.services.exceptions.DataIntegrityViolationException;
import helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record TecnicoService(TecnicoRepository tecnicoRepository) {

    public Tecnico CriarTecnico(TecnicoDTO tecnicoDTO) {
        tecnicoDTO.setId(null);
        validaPorCpfEEmail(tecnicoDTO);
        Tecnico tecnico = new Tecnico(tecnicoDTO);
        return tecnicoRepository.save(tecnico);
    }

    public Tecnico BuscarTecnicoPorId(Long id) {
        return tecnicoRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Tecnico com ID '%s' não encontrado.", id)));
    }

    public Page<Tecnico> BuscarTodosTecnicos(Pageable pageable) {
        return tecnicoRepository.findAll(pageable);
    }

    public Tecnico atualizarTecnico(Long id, TecnicoDTO tecnicoDTO) {
        tecnicoDTO.setId(id);
        Tecnico tecnico = BuscarTecnicoPorId(id);
        validaPorCpfEEmail(tecnicoDTO);
        tecnico = new Tecnico(tecnicoDTO);
        return tecnicoRepository.save(tecnico);
    }

    private void validaPorCpfEEmail(TecnicoDTO tecnicoDTO) {
        Optional<Tecnico> tecnico = tecnicoRepository.findByCpf(tecnicoDTO.getCpf());
        if (tecnico.isPresent() && tecnico.get().getId() != tecnicoDTO.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }
        tecnico = tecnicoRepository.findByEmail(tecnicoDTO.getEmail());
        if (tecnico.isPresent() && tecnico.get().getId() != tecnicoDTO.getId()) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
        }
    }
}
