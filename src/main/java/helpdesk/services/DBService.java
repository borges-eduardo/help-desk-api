package helpdesk.services;

import helpdesk.models.Chamado;
import helpdesk.models.Cliente;
import helpdesk.models.Tecnico;
import helpdesk.models.enums.Perfil;
import helpdesk.models.enums.Prioridade;
import helpdesk.models.enums.Status;
import helpdesk.repositories.ChamadoRepository;
import helpdesk.repositories.PessoaRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class DBService {

    private final ChamadoRepository chamadoRepository;
    private final PessoaRepository pessoaRepository;
    private final BCryptPasswordEncoder encoder;

    public void instanciaDB() {

        Tecnico tecnico1 = new Tecnico(null, "Eduardo Borges", "636.924.350-70", "eduardo@email.com", encoder.encode("123"));
        tecnico1.setPerfis(Perfil.ADMIN);

        Cliente cliente1 = new Cliente(null, "Albert Einstein", "111.661.890-74", "einstein@email.com", encoder.encode("123"));
        Cliente cliente2 = new Cliente(null, "Isaac Newton", "322.429.140-06", "isaac@email.com", encoder.encode("123"));

        Chamado chamado1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 1", "Teste chamado 1", tecnico1, cliente1);
        Chamado chamado2 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Chamado 2", "Teste chamado 2", tecnico1, cliente2);

        pessoaRepository.saveAll(Arrays.asList(tecnico1, cliente1, cliente2));
        chamadoRepository.saveAll(Arrays.asList(chamado1, chamado2));
    }
}
