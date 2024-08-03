package com.macspace.gestiondestock.services.impl;

import com.macspace.gestiondestock.dto.EntrepriseDto;
import com.macspace.gestiondestock.dto.RolesDto;
import com.macspace.gestiondestock.dto.UtilisateurDto;
import com.macspace.gestiondestock.exception.EntityNotFoundException;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import com.macspace.gestiondestock.repository.EntrepriseRepository;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.repository.RolesRepository;
import com.macspace.gestiondestock.services.EntrepriseService;
import com.macspace.gestiondestock.services.UtilisateurService;
import com.macspace.gestiondestock.validator.EntrepriseValidator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service d'implémentation pour la gestion des entreprises.
 */
@Transactional(rollbackOn = Exception.class)
@Service
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {

    private EntrepriseRepository entrepriseRepository;
    private UtilisateurService utilisateurService;
    private RolesRepository rolesRepository;

    /**
     * Constructeur avec injection de dépendances pour les repositories d'entreprises, d'utilisateurs et de rôles.
     *
     * @param entrepriseRepository le repository pour les entreprises
     * @param utilisateurService le service pour les utilisateurs
     * @param rolesRepository le repository pour les rôles
     */
    @Autowired
    public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository, UtilisateurService utilisateurService,
                                 RolesRepository rolesRepository) {
        this.entrepriseRepository = entrepriseRepository;
        this.utilisateurService = utilisateurService;
        this.rolesRepository = rolesRepository;
    }

    /**
     * Enregistre ou met à jour une entreprise.
     *
     * @param dto le DTO de l'entreprise à enregistrer ou mettre à jour
     * @return le DTO de l'entreprise enregistrée ou mise à jour
     * @throws InvalidEntityException si l'entreprise n'est pas valide
     */
    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
        List<String> errors = EntrepriseValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Entreprise is not valid {}", dto);
            throw new InvalidEntityException("L'entreprise n'est pas valide", ErrorCodes.ENTREPRISE_NOT_VALID, errors);
        }
        EntrepriseDto savedEntreprise = EntrepriseDto.fromEntity(
                entrepriseRepository.save(EntrepriseDto.toEntity(dto))
        );

        UtilisateurDto utilisateur = fromEntreprise(savedEntreprise);

        UtilisateurDto savedUser = utilisateurService.save(utilisateur);

        RolesDto rolesDto = RolesDto.builder()
                .roleName("ADMIN")
                .utilisateur(savedUser)
                .build();

        rolesRepository.save(RolesDto.toEntity(rolesDto));

        return savedEntreprise;
    }

    /**
     * Convertit un DTO d'entreprise en DTO d'utilisateur.
     *
     * @param dto le DTO de l'entreprise
     * @return le DTO de l'utilisateur
     */
    private UtilisateurDto fromEntreprise(EntrepriseDto dto) {
        return UtilisateurDto.builder()
                .adresse(dto.getAdresse())
                .nom(dto.getNom())
                .prenom(dto.getCodeFiscal())
                .email(dto.getEmail())
                .password(generateRandomPassword())
                .entreprise(dto)
                .photo(dto.getPhoto())
                .build();
    }

    /**
     * Génère un mot de passe aléatoire.
     *
     * @return le mot de passe généré
     */
    private String generateRandomPassword() {
        return "som3R@nd0mP@$$word";
    }

    /**
     * Recherche une entreprise par son identifiant.
     *
     * @param id l'identifiant de l'entreprise
     * @return le DTO de l'entreprise trouvée
     * @throws EntityNotFoundException si aucune entreprise n'est trouvée avec l'identifiant donné
     */
    @Override
    public EntrepriseDto findById(Integer id) {
        if (id == null) {
            log.error("Entreprise ID is null");
            return null;
        }
        return entrepriseRepository.findById(id)
                .map(EntrepriseDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune entreprise avec l'ID = " + id + " n'a ete trouve dans la BDD",
                        ErrorCodes.ENTREPRISE_NOT_FOUND)
                );
    }

    /**
     * Retourne la liste de toutes les entreprises.
     *
     * @return la liste des DTOs des entreprises
     */
    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseRepository.findAll().stream()
                .map(EntrepriseDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Supprime une entreprise par son identifiant.
     *
     * @param id l'identifiant de l'entreprise à supprimer
     */
    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Entreprise ID is null");
            return;
        }
        entrepriseRepository.deleteById(id);
    }
}
