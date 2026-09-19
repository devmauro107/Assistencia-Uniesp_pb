package br.edu.uniesp.Assistencia_Uniesp.config.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroRespostaDTO> tratarRecursoNaoEncontrado(
            RecursoNaoEncontradoException ex,
            HttpServletRequest request
    ) {
        ErroRespostaDTO corpo = ErroRespostaDTO.simples(
                HttpStatus.NOT_FOUND.value(),
                "Recurso Não Encontrado",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(corpo);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroRespostaDTO> tratarRegraNegocio(
            RegraNegocioException ex,
            HttpServletRequest request
    ) {
        ErroRespostaDTO corpo = ErroRespostaDTO.simples(
                HttpStatus.CONFLICT.value(),
                "Conflito de Regra de Negócio",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(corpo);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroRespostaDTO> tratarValidacaoCampos(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<ErroRespostaDTO.CampoInvalidoDTO> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> new ErroRespostaDTO.CampoInvalidoDTO(f.getField(), f.getDefaultMessage()))
                .toList();

        ErroRespostaDTO corpo = ErroRespostaDTO.comCampos(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação",
                "Um ou mais campos contêm valores inválidos.",
                request.getRequestURI(),
                erros
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }
}
