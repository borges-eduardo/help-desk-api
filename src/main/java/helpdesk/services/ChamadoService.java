package helpdesk.services;

import helpdesk.models.Chamado;
import helpdesk.models.Cliente;
import helpdesk.models.Tecnico;
import helpdesk.models.dtos.ChamadoDTO;
import helpdesk.models.enums.Prioridade;
import helpdesk.models.enums.Status;
import helpdesk.repositories.ChamadoRepository;
import helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public record ChamadoService(
        ChamadoRepository chamadoRepository,
        TecnicoService tecnicoService,
        ClienteService clienteService
){

    public Chamado criarChamado(ChamadoDTO chamadoDTO) {
        return chamadoRepository.save(novoChamado(chamadoDTO));
    }

    public Chamado buscarPorId(Long id) {
        return chamadoRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Chamado com ID '%s' não encontrado.", id)));
    }

    public Page<Chamado> buscarTodosChamados(Pageable pageable) {
        return chamadoRepository.findAll(pageable);
    }

    public Chamado atualizarChamado(Long id, ChamadoDTO chamadoDTO) {
        chamadoDTO.setId(id);
        Chamado chamado = buscarPorId(id);
        chamado = novoChamado(chamadoDTO);
        return chamadoRepository.save(chamado);
    }

    private Chamado novoChamado(ChamadoDTO chamadoDTO) {
        Tecnico tecnico = tecnicoService.buscarTecnicoPorId(chamadoDTO.getTecnico());
        Cliente cliente = clienteService.buscarClientePorId(chamadoDTO.getCliente());

        Chamado chamado = new Chamado();
        if(chamadoDTO.getId() != null) {
            chamado.setId(chamadoDTO.getId());
        }

        if(chamadoDTO.getStatus().equals(2)) {
            chamado.setDataFechamento(LocalDate.now());
        }

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(Prioridade.toEnum(chamadoDTO.getPrioridade()));
        chamado.setStatus(Status.toEnum(chamadoDTO.getStatus()));
        chamado.setTitulo(chamadoDTO.getTitulo());
        chamado.setObservacoes(chamadoDTO.getObservacoes());
        return chamado;
    }
}
