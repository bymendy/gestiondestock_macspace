package com.macspace.gestiondestock.handlers;

import com.macspace.gestiondestock.exception.EntityNotFoundException;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Gestionnaire global des exceptions REST dans MacSpace.
 * <p>
 * Intercepte toutes les exceptions levées par les controllers
 * et les services, et retourne des réponses HTTP structurées
 * via {@link ErrorDto} pour une communication claire avec le frontend.
 * </p>
 *
 * <p>Exceptions gérées :</p>
 * <ul>
 *     <li>{@link EntityNotFoundException} → HTTP 404</li>
 *     <li>{@link InvalidEntityException} → HTTP 400</li>
 *     <li>{@link InvalidOperationException} → HTTP 400</li>
 *     <li>{@link Exception} → HTTP 500 (toutes autres exceptions)</li>
 * </ul>
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Gère les exceptions {@link EntityNotFoundException}.
     * Levée quand une entité recherchée n'existe pas en base.
     * Retourne un statut HTTP 404 Not Found.
     *
     * @param exception L'exception interceptée.
     * @return La réponse HTTP 404 avec les détails de l'erreur.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEntityNotFoundException(
            EntityNotFoundException exception) {
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        final ErrorDto errorDto = ErrorDto.builder()
                .code(exception.getErrorCode())
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorDto, notFound);
    }

    /**
     * Gère les exceptions {@link InvalidEntityException}.
     * Levée quand les données d'une entité ne passent pas la validation.
     * Retourne un statut HTTP 400 Bad Request avec la liste des erreurs.
     *
     * @param exception L'exception interceptée.
     * @return La réponse HTTP 400 avec les détails et la liste des erreurs.
     */
    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<ErrorDto> handleInvalidEntityException(
            InvalidEntityException exception) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto = ErrorDto.builder()
                .code(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errors(exception.getErrors())
                .build();
        return new ResponseEntity<>(errorDto, badRequest);
    }

    /**
     * Gère les exceptions {@link InvalidOperationException}.
     * Levée quand une opération métier est invalide ou non autorisée.
     * Retourne un statut HTTP 400 Bad Request.
     *
     * @param exception L'exception interceptée.
     * @return La réponse HTTP 400 avec les détails de l'erreur.
     */
    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ErrorDto> handleInvalidOperationException(
            InvalidOperationException exception) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto = ErrorDto.builder()
                .code(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorDto, badRequest);
    }

    /**
     * Gère toutes les exceptions non prévues.
     * Catch-all pour toute exception non interceptée par les handlers
     * spécifiques ci-dessus. Retourne un statut HTTP 500 Internal Server Error.
     *
     * @param exception L'exception interceptée.
     * @return La réponse HTTP 500 avec le message d'erreur.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception exception) {
        final HttpStatus internalError = HttpStatus.INTERNAL_SERVER_ERROR;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpCode(internalError.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorDto, internalError);
    }
}