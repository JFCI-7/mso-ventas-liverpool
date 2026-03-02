package com.liverpool.msoventas.shared.domain.model;

import lombok.Getter;

/**
 * Contenedor generico que encapsula el resultado de un caso de uso.
 *
 * <p>Un {@code Result<T>} puede representar exito (con datos) o fallo
 * (con mensaje descriptivo y tipo de error). Su uso elimina el manejo de
 * excepciones como flujo de control entre la capa de aplicacion y los
 * adaptadores de entrada.</p>
 *
 * @param <T> tipo del dato encapsulado en caso de exito
 */
@Getter
public class Result<T> {

    private final T data;
    private final String errorMessage;
    private final ErrorType errorType;
    private final boolean success;

    private Result(T data, String errorMessage, ErrorType errorType, boolean success) {
        this.data = data;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
        this.success = success;
    }

    /**
     * Crea un resultado exitoso con el dato producido.
     *
     * @param <T>  tipo del dato
     * @param data dato resultante de la operacion; puede ser {@code null}
     *             cuando la operacion no devuelve valor (p. ej. delete)
     * @return instancia de {@code Result} que representa exito
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data, null, null, true);
    }

    /**
     * Crea un resultado de fallo con el mensaje y tipo de error correspondiente.
     *
     * @param <T>          tipo del dato esperado (nunca se asigna en fallo)
     * @param errorMessage descripcion legible del error ocurrido
     * @param errorType    categoria del error segun {@link ErrorType}
     * @return instancia de {@code Result} que representa fallo
     */
    public static <T> Result<T> failure(String errorMessage, ErrorType errorType) {
        return new Result<>(null, errorMessage, errorType, false);
    }

    /**
     * Indica si la operacion fue exitosa.
     *
     * @return {@code true} si el resultado es exito
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Indica si la operacion resulto en fallo.
     *
     * @return {@code true} si el resultado es fallo
     */
    public boolean isFailure() {
        return !success;
    }

}
