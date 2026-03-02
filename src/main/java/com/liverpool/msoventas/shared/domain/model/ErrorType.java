package com.liverpool.msoventas.shared.domain.model;

/**
 * Tipos de error estandarizados utilizados en el patron {@link Result}.
 *
 * <p>Cada constante representa una categoria de fallo que el dominio reporta
 * al adaptador de entrada, permitiendole traducir el error al codigo HTTP
 * correspondiente sin acoplar la logica de negocio a conceptos HTTP.</p>
 */
public enum ErrorType {

    /** El recurso solicitado no fue encontrado en el sistema. */
    NOT_FOUND,

    /** Ya existe un recurso con los mismos datos unicos (p. ej. email duplicado). */
    CONFLICT,

    /** Los datos de entrada no cumplen las reglas de negocio o validacion. */
    VALIDATION_ERROR,

    /** Error inesperado en la capa de infraestructura o de aplicacion. */
    INTERNAL_ERROR

}
