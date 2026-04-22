package com.macspace.gestiondestock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation personnalisée MacSpace.
 * Marque les méthodes à tracer dans l'audit log.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {

    /** Nom de l'entité concernée : client, intervention, produit... */
    String entite();

    /** Action effectuée : CREATE, UPDATE, DELETE, READ */
    String action();
}