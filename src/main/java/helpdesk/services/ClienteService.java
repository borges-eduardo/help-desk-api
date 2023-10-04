package helpdesk.services;

import helpdesk.models.Cliente;
import helpdesk.models.dtos.ClienteDTO;
import helpdesk.repositories.ClienteRepository;
import helpdesk.services.exceptions.DataIntegrityViolationException;
import helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record ClienteService(ClienteRepository clienteRepository) {

    public Cliente criarCliente(ClienteDTO clienteDTO) {
        clienteDTO.setId(null);
        validaPorCpfEEmail(clienteDTO);
        Cliente cliente = new Cliente(clienteDTO);
        return clienteRepository.save(cliente);
    }

    public Cliente buscarClientePorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Cliente com ID '%s' não encontrado.", id)));
    }

    public Page<Cliente> buscarTodosClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    public Cliente atualizarCliente(Long id, ClienteDTO clienteDTO) {
        clienteDTO.setId(id);
        Cliente cliente = buscarClientePorId(id);
        validaPorCpfEEmail(clienteDTO);
        cliente = new Cliente(clienteDTO);
        return clienteRepository.save(cliente);
    }

    public void excluirCliente(Long id) {
        Cliente cliente = buscarClientePorId(id);
        if(!cliente.getChamados().isEmpty()) {
            throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
        }
        clienteRepository.deleteById(id);
    }

    private void validaPorCpfEEmail(ClienteDTO clienteDTO) {
        Optional<Cliente> cliente = clienteRepository.findByCpf(clienteDTO.getCpf());
        if (cliente.isPresent() && cliente.get().getId() != clienteDTO.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }
        cliente = clienteRepository.findByEmail(clienteDTO.getEmail());
        if (cliente.isPresent() && cliente.get().getId() != clienteDTO.getId()) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
        }
    }
}
