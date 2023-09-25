package helpdesk.services;

import helpdesk.models.Pessoa;
import helpdesk.models.Tecnico;
import helpdesk.models.dtos.TecnicoDTO;
import helpdesk.repositories.TecnicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record TecnicoService(TecnicoRepository tecnicoRepository) {

    public Tecnico salvarTecnico(TecnicoDTO tecnicoDTO) {
        tecnicoDTO.setId(null);
        tecnicoDTO.setSenha(tecnicoDTO.getSenha());
        validaPorCpfEEmail(tecnicoDTO);
        Tecnico novoTecnico = new Tecnico(tecnicoDTO);
        return tecnicoRepository.save(novoTecnico);
    }

    public Tecnico buscarPorId(Long id) {
        return tecnicoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tecnico não encontrado."));
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
