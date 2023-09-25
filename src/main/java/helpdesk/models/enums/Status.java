package helpdesk.models.enums;

import java.util.Arrays;

public enum Status {

    ABERTO(0, "ABERTO"),
    ANDAMENTO(1, "ANDAMENTO"),
    ENCERRADO(2, "ENCERRADO");

    private Integer codigo;
    private String descricao;

    Status(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Status toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }

        return Arrays.stream(Status.values())
                .filter(perfil -> codigo.equals(perfil.getCodigo()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Status inválido"));
    }
}
