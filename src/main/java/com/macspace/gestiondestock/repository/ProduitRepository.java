package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Produits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité {@link Produits}.
 * <p>
 * Cette interface permet d'effectuer des opérations CRUD (Créer, Lire, Mettre à jour, Supprimer)
 * sur les entités {@link Produits}. Elle étend l'interface {@link JpaRepository}, ce qui fournit
 * des méthodes prédéfinies pour interagir avec la base de données.
 * </p>
 *
 */
public interface ProduitRepository extends JpaRepository<Produits, Integer> {

    /**
     * Trouve un produit en fonction de son code.
     * <p>
     * Cette méthode retourne un {@link Optional} contenant le produit correspondant au code fourni
     * ou vide si aucun produit ne correspond.
     * </p>
     *
     * @param codeProduit le code du produit à rechercher
     * @return un {@link Optional} contenant le produit trouvé, ou vide si aucun produit ne correspond
     */
    Optional<Produits> findProduitByCodeProduit(String codeProduit);

    // Exemple de requête JPQL commentée pour la recherche de produits par code et désignation en ignorant la casse.
    /*
    @Query("SELECT p FROM Produits p WHERE p.codeProduit = :codeProduit AND p.designation = :designation")
    List<Produits> findByCodeProduitIgnoreCaseAndDesignationIgnoreCase(@Param("codeProduit") String codeProduit, @Param("designation") String designation);
    */
}
