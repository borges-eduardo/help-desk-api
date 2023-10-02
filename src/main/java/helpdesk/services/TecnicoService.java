package helpdesk.services;

import helpdesk.models.Pessoa;
import helpdesk.models.Tecnico;
import helpdesk.models.dtos.TecnicoDTO;
import helpdesk.repositories.TecnicoRepository;
import helpdesk.services.exceptions.ObjectNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record TecnicoService(TecnicoRepository tecnicoRepository) {

    public Tecnico create(TecnicoDTO tecnicoDTO) {
        tecnicoDTO.setId(null);
        validaPorCpfEEmail(tecnicoDTO);
        Tecnico tecnico = new Tecnico(tecnicoDTO);
        return tecnicoRepository.save(tecnico);
    }

    public Tecnico findById(Long id) {
        return tecnicoRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Tecnico com ID '%s' não encontrado.", id)));
    }

    public Page<Tecnico> findAll(Pageable pageable) {
        return tecnicoRepository.findAll(pageable);
    }

    private void validaPorCpfEEmail(TecnicoDTO objDTO) {
        Optional<Tecnico> obj = tecnicoRepository.findByCpf(objDTO.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }

        obj = tecnicoRepository.findByEmail(objDTO.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
        }
    }
}
