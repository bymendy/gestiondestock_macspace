package com.macspace.gestiondestock.handlers;

import com.macspace.gestiondestock.exception.EntityNotFoundException;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Gestionnaire global des exceptions pour les contrôleurs REST.
 * <p>
 * Cette classe intercepte les exceptions spécifiques et renvoie des réponses
 * HTTP appropriées avec des détails sur l'erreur.
 * </p>
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Gère les exceptions de type EntityNotFoundException.
     *
     * @param exception L'exception interceptée.
     * @param webRequest L'objet WebRequest contenant les détails de la requête.
     * @return Un objet ResponseEntity contenant les détails de l'erreur et le statut HTTP.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleException(EntityNotFoundException exception, WebRequest webRequest) {
        final HttpStatus notFound = HttpStatus.NOT_FOUND;

        // Construction de l'objet ErrorDto avec les détails de l'erreur
        final ErrorDto errorDto = ErrorDto.builder()
                .code(exception.getErrorCode())
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorDto, notFound);
    }

    /**
     * Gère les exceptions de type InvalidEntityException.
     *
     * @param exception L'exception interceptée.
     * @param webRequest L'objet WebRequest contenant les détails de la requête.
     * @return Un objet ResponseEntity contenant les détails de l'erreur et le statut HTTP.
     */
    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<ErrorDto> handleException(InvalidEntityException exception, WebRequest webRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        // Construction de l'objet ErrorDto avec les détails de l'erreur
        final ErrorDto errorDto = ErrorDto.builder()
                .code(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errors(exception.getErrors())
                .build();

        return new ResponseEntity<>(errorDto, badRequest);
    }
}