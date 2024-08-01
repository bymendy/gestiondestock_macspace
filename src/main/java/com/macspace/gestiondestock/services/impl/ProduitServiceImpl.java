package com.macspace.gestiondestock.services.impl;

import com.macspace.gestiondestock.dto.ProduitDto;
import com.macspace.gestiondestock.exception.EntityNotFoundException;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import com.macspace.gestiondestock.model.Produits;
import com.macspace.gestiondestock.repository.ProduitRepository;
import com.macspace.gestiondestock.services.ProduitService;
import com.macspace.gestiondestock.validator.ProduitsValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implémentation du service pour la gestion des produits.
 * <p>
 * Cette classe implémente les méthodes définies dans l'interface {@link ProduitService}
 * pour fournir les fonctionnalités de création, lecture, mise à jour et suppression (CRUD)
 * des entités {@link ProduitDto}.
 * </p>
 */
@Service
@Slf4j
public class ProduitServiceImpl implements ProduitService {

    private ProduitRepository produitRepository;

    @Autowired
    public ProduitServiceImpl(
            ProduitRepository produitRepository
    ){
        this.produitRepository = produitRepository;
    }

    /**
     * Enregistre un nouveau produit ou met à jour un produit existant.
     *
     * @param dto Le produit à enregistrer ou à mettre à jour.
     * @return Le produit enregistré ou mis à jour.
     */
    @Override
    public ProduitDto save(ProduitDto dto) {
        // TODO: Implémenter la logique pour enregistrer ou mettre à jour un produit
        List<String> errors = ProduitsValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("Produit is not valid {}", dto);
            throw new InvalidEntityException("Le produit n'est pas valide", ErrorCodes.PRODUIT_NOT_VALID, errors);
        }
        return ProduitDto.fromEntity(
                produitRepository.save(
                        ProduitDto.toEntity(dto)
                )
        );
    }

    /**
     * Recherche un produit par son identifiant unique.
     *
     * @param id L'identifiant unique du produit.
     * @return Le produit correspondant à l'identifiant, ou null si aucun produit n'est trouvé.
     */
    @Override
    public ProduitDto findById(Integer id) {
        if (id == null){
            log.error("Produit ID is null");
        }
        // TODO: Implémenter la logique pour rechercher un produit par son identifiant
        Optional<Produits> produits =produitRepository.findById(id);

        return Optional.of(ProduitDto.fromEntity(produits.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        " Aucun produit avec l'ID = "+ id +" n'a ete trouve dans la base de donnée ",
                        ErrorCodes.PRODUIT_NOT_FOUND)
        );
    }

    /**
     * Recherche un produit par son code unique.
     *
     * @param codeProduit Le code unique du produit.
     * @return Le produit correspondant au code, ou null si aucun produit n'est trouvé.
     */
    @Override
    public ProduitDto findByCodeProduit(String codeProduit) {
        if (StringUtils.hasLength(codeProduit)){
            log.error("Produit ID is null");
        }
        Optional<Produits> produits= produitRepository.findProduitByCodeProduit(codeProduit);
        // TODO: Implémenter la logique pour rechercher un produit par son code
        return Optional.of(ProduitDto.fromEntity(produits.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        " Aucun produit avec le CODE = "+ codeProduit +" n'a ete trouve dans la base de donnée ",
                        ErrorCodes.PRODUIT_NOT_FOUND)
        );
    }

    /**
     * Récupère la liste de tous les produits.
     *
     * @return La liste de tous les produits.
     */
    @Override
    public List<ProduitDto> findAll() {
        // TODO: Implémenter la logique pour récupérer la liste de tous les produits
        return produitRepository.findAll().stream()
                .map(ProduitDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Supprime un produit par son identifiant unique.
     *
     * @param id L'identifiant unique du produit à supprimer.
     */
    @Override
    public void delete(Integer id) {
        // TODO: Implémenter la logique pour supprimer un produit par son identifiant
        if ( id == null){
            log.error(("Produit ID is null"));
            return;
        }
        produitRepository.deleteById(id);
    }
}