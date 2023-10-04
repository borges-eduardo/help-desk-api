package helpdesk.services;

import helpdesk.models.Chamado;
import helpdesk.repositories.ChamadoRepository;
import helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record ChamadoService(ChamadoRepository chamadoRepository) {

    public Chamado buscarPorId(Long id) {
        return chamadoRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Chamado com ID '%s' não encontrado.", id)));
    }
}
