package com.macspace.gestiondestock.services.impl;

import com.macspace.gestiondestock.dto.AdresseDto;
import com.macspace.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import com.macspace.gestiondestock.dto.UtilisateurDto;
import com.macspace.gestiondestock.exception.EntityNotFoundException;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.model.Utilisateur;
import com.macspace.gestiondestock.repository.AdresseRepository;
import com.macspace.gestiondestock.repository.UtilisateurRepository;
import com.macspace.gestiondestock.services.UtilisateurService;
import com.macspace.gestiondestock.validator.UtilisateurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service pour la gestion des utilisateurs dans MacSpace.
 *
 * Gère la création, la recherche, la suppression
 * et le changement de mot de passe des utilisateurs.
 * L'adresse est persistée avant l'utilisateur pour
 * respecter les contraintes de clé étrangère JPA.
 */
@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdresseRepository adresseRepository;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param utilisateurRepository Repository JPA des utilisateurs.
     * @param passwordEncoder       Encodeur de mot de passe BCrypt.
     * @param adresseRepository     Repository JPA des adresses.
     */
    @Autowired
    public UtilisateurServiceImpl(
            UtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder,
            AdresseRepository adresseRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.adresseRepository = adresseRepository;
    }

    /**
     * {@inheritDoc}
     * Sauvegarde l'adresse avant l'utilisateur si elle n'est pas
     * encore persistée, puis encode le mot de passe avant persistance.
     */
    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        List<String> errors = UtilisateurValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("L'utilisateur n'est pas valide {}", dto);
            throw new InvalidEntityException(
                    "L'utilisateur n'est pas valide",
                    ErrorCodes.UTILISATEUR_NOT_VALID,
                    errors
            );
        }
        if (userAlreadyExists(dto.getEmail())) {
            throw new InvalidEntityException(
                    "Un autre utilisateur avec le même email existe déjà",
                    ErrorCodes.UTILISATEUR_ALREADY_EXISTS,
                    Collections.singletonList(
                            "Un autre utilisateur avec le même email existe déjà"
                    )
            );
        }

        // Sauvegarder l'adresse en premier si elle n'est pas encore persistée
        if (dto.getAdresse() != null) {
            if (dto.getAdresse().getId() == null) {
                AdresseDto adresseSauvegardee = AdresseDto.fromEntity(
                        adresseRepository.save(AdresseDto.toEntity(dto.getAdresse()))
                );
                dto.setAdresse(adresseSauvegardee);
            }
            // Si l'adresse a déjà un ID, elle est déjà persistée — on ne fait rien
        }

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        return UtilisateurDto.fromEntity(
                utilisateurRepository.save(
                        UtilisateurDto.toEntity(dto)
                )
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UtilisateurDto findById(Integer id) {
        if (id == null) {
            log.error("L'ID de l'utilisateur est nul");
            return null;
        }
        return utilisateurRepository.findById(id)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun utilisateur avec l'ID = " + id + " n'a été trouvé",
                        ErrorCodes.UTILISATEUR_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UtilisateurDto findByEmail(String email) {
        return utilisateurRepository.findUtilisateurByEmail(email)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun utilisateur avec l'email = " + email + " n'a été trouvé",
                        ErrorCodes.UTILISATEUR_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(UtilisateurDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilisateurDto> findAllByEntreprise(Integer idEntreprise) {
        return utilisateurRepository.findAllByEntrepriseId(idEntreprise)
                .stream()
                .map(UtilisateurDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilisateurDto> findAllByFonction(String fonction) {
        return utilisateurRepository.findAllByFonction(fonction)
                .stream()
                .map(UtilisateurDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'ID de l'utilisateur est nul");
            return;
        }
        utilisateurRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto) {
        validate(dto);
        Optional<Utilisateur> utilisateurOptional =
                utilisateurRepository.findById(dto.getId());
        if (utilisateurOptional.isEmpty()) {
            log.warn("Aucun utilisateur avec l'ID {}", dto.getId());
            throw new EntityNotFoundException(
                    "Aucun utilisateur avec l'ID " + dto.getId() + " n'a été trouvé",
                    ErrorCodes.UTILISATEUR_NOT_FOUND
            );
        }
        Utilisateur utilisateur = utilisateurOptional.get();
        utilisateur.setPassword(passwordEncoder.encode(dto.getMotDePasse()));
        return UtilisateurDto.fromEntity(
                utilisateurRepository.save(utilisateur)
        );
    }

    // ===== Méthodes privées =====

    /**
     * Vérifie si un utilisateur avec le même email existe déjà.
     *
     * @param email L'email à vérifier.
     * @return true si un utilisateur existe déjà avec cet email.
     */
    private boolean userAlreadyExists(String email) {
        Optional<Utilisateur> user =
                utilisateurRepository.findUtilisateurByEmail(email);
        return user.isPresent();
    }

    /**
     * Valide les données du DTO de changement de mot de passe.
     *
     * @param dto Le DTO à valider.
     * @throws InvalidOperationException Si les données sont invalides.
     */
    private void validate(ChangerMotDePasseUtilisateurDto dto) {
        if (dto == null) {
            log.warn("Impossible de modifier le mot de passe avec un objet null");
            throw new InvalidOperationException(
                    "Aucune information fournie pour changer le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID
            );
        }
        if (dto.getId() == null) {
            log.warn("Impossible de modifier le mot de passe avec un ID null");
            throw new InvalidOperationException(
                    "ID utilisateur null : impossible de modifier le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID
            );
        }
        if (!StringUtils.hasLength(dto.getMotDePasse()) ||
                !StringUtils.hasLength(dto.getConfirmMotDePasse())) {
            log.warn("Impossible de modifier le mot de passe avec un mot de passe null");
            throw new InvalidOperationException(
                    "Mot de passe null : impossible de modifier le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID
            );
        }
        if (!dto.getMotDePasse().equals(dto.getConfirmMotDePasse())) {
            log.warn("Les deux mots de passe sont différents");
            throw new InvalidOperationException(
                    "Les mots de passe ne correspondent pas",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID
            );
        }
    }
}