package helpdesk.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import helpdesk.models.dtos.TecnicoDTO;
import helpdesk.models.enums.Perfil;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity(name = "tb_tecnico")
public class Tecnico extends Pessoa {

    @JsonIgnore
    @OneToMany(mappedBy = "tecnico")
    private List<Chamado> chamados = new ArrayList<>();

    public Tecnico() {
        super();
        setPerfis(Perfil.TECNICO);
    }

    public Tecnico(Long id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        setPerfis(Perfil.CLIENTE);
    }

    public Tecnico(TecnicoDTO dto) {
        super();
        this.id = dto.getId();
        this.nome = dto.getNome();
        this.cpf = dto.getCpf();
        this.email = dto.getEmail();
        this.senha = dto.getSenha();
        this.perfis = dto.getPerfis().stream().map(Perfil::getCodigo).collect(Collectors.toSet());
        this.dataCriacao = dto.getDataCriacao();
    }

}
