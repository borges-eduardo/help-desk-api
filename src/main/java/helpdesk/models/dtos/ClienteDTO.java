package helpdesk.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import helpdesk.models.Cliente;
import helpdesk.models.enums.Perfil;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ClienteDTO {

    protected Long id;

    @NotNull(message = "O campo NOME é requerido.")
    protected String nome;

    @NotNull(message = "O campo CPF é requerido.")
    @CPF
    protected String cpf;

    @NotNull(message = "O campo EMAIL é requerido.")
    protected String email;

    @NotNull(message = "O campo SENHA é requerido.")
    protected String senha;

    protected Set<Integer> perfis = new HashSet<>();

    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate dataCriacao = LocalDate.now();

    public ClienteDTO() {
        super();
        setPerfil(Perfil.CLIENTE);
    }

    public ClienteDTO(Cliente cliente) {
        super();
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.email = cliente.getEmail();
        this.senha = cliente.getSenha();
        this.perfis = cliente.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
        this.dataCriacao = cliente.getDataCriacao();
        setPerfil(Perfil.CLIENTE);
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }

    public void setPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }
}
