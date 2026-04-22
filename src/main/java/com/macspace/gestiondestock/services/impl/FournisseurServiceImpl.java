package com.macspace.gestiondestock.services.impl;

import com.macspace.gestiondestock.dto.AdresseDto;
import com.macspace.gestiondestock.annotation.Auditable;

import com.macspace.gestiondestock.dto.FournisseurDto;
import com.macspace.gestiondestock.exception.EntityNotFoundException;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.model.Adresse;
import com.macspace.gestiondestock.model.CommandeFournisseur;
import com.macspace.gestiondestock.model.Fournisseur;
import com.macspace.gestiondestock.repository.AdresseRepository;
import com.macspace.gestiondestock.repository.CommandeFournisseurRepository;
import com.macspace.gestiondestock.repository.FournisseurRepository;
import com.macspace.gestiondestock.services.FournisseurService;
import com.macspace.gestiondestock.validator.FournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implémentation du service pour la gestion des fournisseurs dans MacSpace.
 *
 * Gère la création, la recherche et la suppression des fournisseurs.
 * L'adresse est persistée avant le fournisseur pour respecter
 * les contraintes de clé étrangère JPA.
 */
@Service
@Slf4j
public class FournisseurServiceImpl implements FournisseurService {

    private final FournisseurRepository fournisseurRepository;
    private final CommandeFournisseurRepository commandeFournisseurRepository;
    private final AdresseRepository adresseRepository;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param fournisseurRepository         Repository JPA des fournisseurs.
     * @param commandeFournisseurRepository Repository JPA des commandes fournisseurs.
     * @param adresseRepository             Repository JPA des adresses.
     */
    @Autowired
    public FournisseurServiceImpl(
            FournisseurRepository fournisseurRepository,
            CommandeFournisseurRepository commandeFournisseurRepository,
            AdresseRepository adresseRepository) {
        this.fournisseurRepository = fournisseurRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.adresseRepository = adresseRepository;
    }

    /**
     * {@inheritDoc}
     * Sauvegarde l'adresse avant le fournisseur si elle existe.
     */
    @Auditable(entite = "fournisseur", action = "CREATE_UPDATE")
    @Override
    public FournisseurDto save(FournisseurDto dto) {
        List<String> errors = FournisseurValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Le fournisseur n'est pas valide {}", dto);
            throw new InvalidEntityException(
                    "Le fournisseur n'est pas valide",
                    ErrorCodes.FOURNISSEUR_NOT_VALID,
                    errors
            );
        }

        /* 1. Sauvegarder l'adresse en premier si elle existe */
        Adresse adresseSauvegardee = null;
        if (dto.getAdresse() != null) {
            adresseSauvegardee = adresseRepository.save(
                    AdresseDto.toEntity(dto.getAdresse())
            );
            dto.setAdresse(AdresseDto.fromEntity(adresseSauvegardee));
        }

        /* 2. UPDATE si id présent, INSERT sinon */
        Fournisseur fournisseur;
        if (dto.getId() != null) {
            /* Mode modification - récupérer le fournisseur existant */
            fournisseur = fournisseurRepository.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Aucun fournisseur avec l'ID = " + dto.getId(),
                            ErrorCodes.FOURNISSEUR_NOT_FOUND
                    ));
        } else {
            /* Mode création */
            fournisseur = new Fournisseur();
        }

        fournisseur.setNom(dto.getNom());
        fournisseur.setPrenom(dto.getPrenom());
        fournisseur.setEmail(dto.getEmail());
        fournisseur.setNumTel(dto.getNumTel());
        fournisseur.setPhoto(dto.getPhoto());
        fournisseur.setAdresse(adresseSauvegardee);
        fournisseur.setIdEntreprise(dto.getIdEntreprise());

        /* 3. Sauvegarder le fournisseur */
        return FournisseurDto.fromEntity(fournisseurRepository.save(fournisseur));
    }

    /**
     * {@inheritDoc}
     */
    @Auditable(entite = "fournisseur", action = "READ")
    @Override
    public FournisseurDto findById(Integer id) {
        if (id == null) {
            log.error("L'ID du fournisseur est nul");
            return null;
        }
        return fournisseurRepository.findById(id)
                .map(FournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun fournisseur avec l'ID = " + id + " n'a été trouvé",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FournisseurDto findByEmail(String email) {
        if (email == null || email.isBlank()) {
            log.error("L'email du fournisseur est nul");
            return null;
        }
        return fournisseurRepository.findFournisseurByEmail(email)
                .map(FournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun fournisseur avec l'email = " + email + " n'a été trouvé",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FournisseurDto> findAllByNom(String nom) {
        return fournisseurRepository.findAllByNom(nom)
                .stream()
                .map(FournisseurDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurRepository.findAll().stream()
                .map(FournisseurDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FournisseurDto> findAllByIdEntreprise(Integer idEntreprise) {
        return fournisseurRepository.findAllByIdEntreprise(idEntreprise)
                .stream()
                .map(FournisseurDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Auditable(entite = "fournisseur", action = "DELETE")
    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'ID du fournisseur est nul");
            return;
        }
        List<CommandeFournisseur> commandesFournisseur =
                commandeFournisseurRepository.findAllByFournisseurId(id);
        if (!commandesFournisseur.isEmpty()) {
            throw new InvalidOperationException(
                    "Impossible de supprimer un fournisseur qui a déjà des commandes",
                    ErrorCodes.FOURNISSEUR_ALREADY_IN_USE
            );
        }
        fournisseurRepository.deleteById(id);
    }
}