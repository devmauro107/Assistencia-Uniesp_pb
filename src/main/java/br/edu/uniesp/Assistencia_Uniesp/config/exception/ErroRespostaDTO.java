package br.edu.uniesp.Assistencia_Uniesp.config.exception;

import java.time.Instant;
import java.util.List;

public record ErroRespostaDTO(
        Instant timestamp,
        Integer status,
        String erro,
        String mensagem,
        String caminho,
        List<CampoInvalidoDTO> campos
) {
    public record CampoInvalidoDTO(String campo, String mensagem) {}

    public static ErroRespostaDTO simples(Integer status, String erro, String mensagem, String caminho) {
        return new ErroRespostaDTO(Instant.now(), status, erro, mensagem, caminho, null);
    }

    public static ErroRespostaDTO comCampos(Integer status, String erro, String mensagem, String caminho, List<CampoInvalidoDTO> campos) {
        return new ErroRespostaDTO(Instant.now(), status, erro, mensagem, caminho, campos);
    }
}
