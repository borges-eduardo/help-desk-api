package helpdesk.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import helpdesk.models.enums.Prioridade;
import helpdesk.models.enums.Status;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity(name = "tb_chamado")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura = LocalDate.now();

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;

    private Prioridade prioridades;

    private Status status;

    private String titulo;

    private String observaçao;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;


    public Chamado(Long id, Prioridade prioridades, Status status, String titulo, String observaçao, Tecnico tecnico, Cliente cliente) {
        this.id = id;
        this.prioridades = prioridades;
        this.status = status;
        this.titulo = titulo;
        this.observaçao = observaçao;
        this.tecnico = tecnico;
        this.cliente = cliente;
    }
}
