package com.macspace.gestiondestock.utils;

/**
 * Utilitaire d'anonymisation des données sensibles MacSpace.
 * Applique les techniques de pseudonymisation et masquage
 * conformément aux principes RGPD et au cours Analyse et
 * transformation des données.
 */
public class AnonymisationUtils {

    private AnonymisationUtils() {}

    /**
     * Masque un email — ex: jean.dupont@gmail.com → je**@gmail.com
     */
    public static String masquerEmail(String email) {
        if (email == null || !email.contains("@")) return "***@***.***";
        String[] parts = email.split("@");
        String local = parts[0];
        String domaine = parts[1];
        if (local.length() <= 2) return "**@" + domaine;
        return local.substring(0, 2)
                + "*".repeat(local.length() - 2)
                + "@" + domaine;
    }

    /**
     * Masque un numéro de téléphone — ex: 0612345678 → 06****78
     */
    public static String masquerTelephone(String tel) {
        if (tel == null || tel.length() < 4) return "****";
        return tel.substring(0, 2)
                + "*".repeat(tel.length() - 4)
                + tel.substring(tel.length() - 2);
    }

    /**
     * Pseudonymise un nom/prénom — ex: Jean Dupont → Client_001
     */
    public static String pseudonymiser(int id) {
        return "Client_" + String.format("%03d", id);
    }
}