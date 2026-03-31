package com.macspace.gestiondestock.services.impl;

import com.macspace.gestiondestock.dto.AdresseDto;
import com.macspace.gestiondestock.dto.EntrepriseDto;
import com.macspace.gestiondestock.dto.UtilisateurDto;
import com.macspace.gestiondestock.exception.EntityNotFoundException;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import com.macspace.gestiondestock.model.Adresse;
import com.macspace.gestiondestock.model.Entreprise;
import com.macspace.gestiondestock.model.Role;
import com.macspace.gestiondestock.model.RoleType;
import com.macspace.gestiondestock.repository.AdresseRepository;
import com.macspace.gestiondestock.repository.EntrepriseRepository;
import com.macspace.gestiondestock.repository.RoleRepository;
import com.macspace.gestiondestock.repository.UtilisateurRepository;
import com.macspace.gestiondestock.services.EntrepriseService;
import com.macspace.gestiondestock.services.UtilisateurService;
import com.macspace.gestiondestock.validator.EntrepriseValidator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implémentation du service pour la gestion des entreprises dans MacSpace.
 *
 * Gère la création d'une entreprise avec son adresse,
 * son utilisateur admin et son rôle associé.
 * Toutes les entités sont persistées dans le bon ordre
 * pour respecter les contraintes de clé étrangère JPA.
 */
@Transactional(rollbackOn = Exception.class)
@Service
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {

    private final EntrepriseRepository entrepriseRepository;
    private final UtilisateurService utilisateurService;
    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdresseRepository adresseRepository;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param entrepriseRepository  Repository JPA des entreprises.
     * @param utilisateurService    Service de gestion des utilisateurs.
     * @param utilisateurRepository Repository JPA des utilisateurs.
     * @param rolesRepository       Repository JPA des rôles.
     * @param passwordEncoder       Encodeur de mot de passe BCrypt.
     * @param adresseRepository     Repository JPA des adresses.
     */
    @Autowired
    public EntrepriseServiceImpl(
            EntrepriseRepository entrepriseRepository,
            UtilisateurService utilisateurService,
            UtilisateurRepository utilisateurRepository,
            RoleRepository rolesRepository,
            PasswordEncoder passwordEncoder,
            AdresseRepository adresseRepository) {
        this.entrepriseRepository = entrepriseRepository;
        this.utilisateurService = utilisateurService;
        this.utilisateurRepository = utilisateurRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.adresseRepository = adresseRepository;
    }

    /**
     * {@inheritDoc}
     * Ordre de persistance :
     * 1. Adresse
     * 2. Entreprise
     * 3. Utilisateur admin
     * 4. Rôle ADMIN
     */
    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
        List<String> errors = EntrepriseValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("L'entreprise n'est pas valide {}", dto);
            throw new InvalidEntityException(
                    "L'entreprise n'est pas valide",
                    ErrorCodes.ENTREPRISE_NOT_VALID,
                    errors
            );
        }

        // 1. Sauvegarder l'adresse → récupérer l'entité attachée à la session
        Adresse adresseSauvegardee = adresseRepository.save(
                AdresseDto.toEntity(dto.getAdresse())
        );
        dto.setAdresse(AdresseDto.fromEntity(adresseSauvegardee));

        // 2. Construire l'entité Entreprise avec l'adresse attachée
        Entreprise entreprise = new Entreprise();
        entreprise.setNom(dto.getNom());
        entreprise.setDescription(dto.getDescription());
        entreprise.setCodeFiscal(dto.getCodeFiscal());
        entreprise.setPhoto(dto.getPhoto());
        entreprise.setEmail(dto.getEmail());
        entreprise.setNumTel(dto.getNumTel());
        entreprise.setSiteWeb(dto.getSiteWeb());
        entreprise.setAdresse(adresseSauvegardee);

        // 3. Sauvegarder l'entreprise
        EntrepriseDto savedEntreprise = EntrepriseDto.fromEntity(
                entrepriseRepository.save(entreprise)
        );

        // 4. Créer l'utilisateur admin
        UtilisateurDto utilisateur = fromEntreprise(savedEntreprise);
        UtilisateurDto savedUser = utilisateurService.save(utilisateur);

        // 5. Assigner le rôle ADMIN avec l'entité Utilisateur attachée à la session
        Role role = new Role();
        role.setRoleName(RoleType.ROLE_ADMIN);
        role.setUtilisateur(
                utilisateurRepository.findById(savedUser.getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Utilisateur non trouvé après sauvegarde",
                                ErrorCodes.UTILISATEUR_NOT_FOUND
                        ))
        );
        rolesRepository.save(role);

        return savedEntreprise;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntrepriseDto findById(Integer id) {
        if (id == null) {
            log.error("L'ID de l'entreprise est nul");
            return null;
        }
        return entrepriseRepository.findById(id)
                .map(EntrepriseDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune entreprise avec l'ID = " + id + " n'a été trouvée",
                        ErrorCodes.ENTREPRISE_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntrepriseDto findByNom(String nom) {
        if (nom == null || nom.isBlank()) {
            log.error("Le nom de l'entreprise est nul");
            return null;
        }
        return entrepriseRepository.findEntrepriseByNom(nom)
                .map(EntrepriseDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune entreprise avec le nom = " + nom + " n'a été trouvée",
                        ErrorCodes.ENTREPRISE_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntrepriseDto findByEmail(String email) {
        if (email == null || email.isBlank()) {
            log.error("L'email de l'entreprise est nul");
            return null;
        }
        return entrepriseRepository.findEntrepriseByEmail(email)
                .map(EntrepriseDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune entreprise avec l'email = " + email + " n'a été trouvée",
                        ErrorCodes.ENTREPRISE_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntrepriseDto findByCodeFiscal(String codeFiscal) {
        if (codeFiscal == null || codeFiscal.isBlank()) {
            log.error("Le code fiscal de l'entreprise est nul");
            return null;
        }
        return entrepriseRepository.findEntrepriseByCodeFiscal(codeFiscal)
                .map(EntrepriseDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune entreprise avec le code fiscal = " + codeFiscal + " n'a été trouvée",
                        ErrorCodes.ENTREPRISE_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseRepository.findAll().stream()
                .map(EntrepriseDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'ID de l'entreprise est nul");
            return;
        }
        if (!entrepriseRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Aucune entreprise avec l'ID = " + id + " n'a été trouvée",
                    ErrorCodes.ENTREPRISE_NOT_FOUND
            );
        }
        entrepriseRepository.deleteById(id);
    }

    // ===== Méthodes privées =====

    /**
     * Crée un DTO utilisateur admin à partir d'une entreprise.
     * L'utilisateur admin n'a pas d'adresse séparée.
     *
     * @param dto Le DTO de l'entreprise source.
     * @return Le DTO utilisateur admin prêt à être sauvegardé.
     */
    private UtilisateurDto fromEntreprise(EntrepriseDto dto) {
        return UtilisateurDto.builder()
                .adresse(null)
                .nom(dto.getNom())
                .prenom(dto.getCodeFiscal())
                .email(dto.getEmail())
                .password(generateRandomPassword())
                .entreprise(dto)
                .photo(dto.getPhoto())
                .build();
    }

    /**
     * Génère un mot de passe aléatoire encodé avec BCrypt.
     *
     * @return Le mot de passe encodé.
     */
    private String generateRandomPassword() {
        return passwordEncoder.encode(UUID.randomUUID().toString());
    }
}