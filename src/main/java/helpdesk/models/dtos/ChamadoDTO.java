package helpdesk.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import helpdesk.models.Chamado;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChamadoDTO {

    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura = LocalDate.now();

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;

    @NotNull(message = "O campo PRIORIDADE é requerido.")
    private Integer prioridade;

    @NotNull(message = "O campo STATUS é requerido.")
    private Integer status;

    @NotNull(message = "O campo TITULO é requerido.")
    private String titulo;

    @NotNull(message = "O campo OBSERVAÇÕES é requerido.")
    private String observacoes;

    @NotNull(message = "O campo TECNICO é requerido.")
    private Long tecnico;

    @NotNull(message = "O campo CLIENTE é requerido.")
    private Long cliente;

    private String nomeTecnico;

    private String nomeCliente;

    public ChamadoDTO(Chamado chamado) {
        this.id = chamado.getId();
        this.dataAbertura = chamado.getDataAbertura();
        this.dataFechamento = chamado.getDataFechamento();
        this.prioridade = chamado.getPrioridade().getCodigo();
        this.status = chamado.getStatus().getCodigo();
        this.titulo = chamado.getTitulo();
        this.observacoes = chamado.getObservacoes();
        this.tecnico = chamado.getTecnico().getId();
        this.cliente = chamado.getCliente().getId();
        this.nomeCliente = chamado.getCliente().getNome();
        this.nomeTecnico = chamado.getTecnico().getNome();
    }
}
