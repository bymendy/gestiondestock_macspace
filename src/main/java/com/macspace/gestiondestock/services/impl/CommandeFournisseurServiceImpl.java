package com.macspace.gestiondestock.services.impl;

import com.macspace.gestiondestock.dto.*;
import com.macspace.gestiondestock.exception.EntityNotFoundException;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.model.*;
import com.macspace.gestiondestock.repository.CommandeFournisseurRepository;
import com.macspace.gestiondestock.repository.FournisseurRepository;
import com.macspace.gestiondestock.repository.LigneCommandeFournisseurRepository;
import com.macspace.gestiondestock.repository.ProduitRepository;
import com.macspace.gestiondestock.services.CommandeFournisseurService;
import com.macspace.gestiondestock.services.MvtStkService;
import com.macspace.gestiondestock.validator.CommandeFournisseurValidator;
import com.macspace.gestiondestock.validator.ProduitsValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {
    private CommandeFournisseurRepository commandeFournisseurRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    private FournisseurRepository fournisseurRepository;
    private ProduitRepository produitRepository;
    private MvtStkService mvtStkService;

    @Autowired
    public CommandeFournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository,
                                          FournisseurRepository fournisseurRepository, ProduitRepository produitRepository,
                                          LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository, MvtStkService mvtStkService) {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.produitRepository = produitRepository;
        this.mvtStkService = mvtStkService;
    }

    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {

        List<String> errors = CommandeFournisseurValidator.validate(dto);

        if (!errors.isEmpty()) {
            log.error("Commande fournisseur n'est pas valide");
            throw new InvalidEntityException("La commande fournisseur n'est pas valide", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID, errors);
        }

        if (dto.getId() != null && dto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(dto.getFournisseur().getId());
        if (fournisseur.isEmpty()) {
            log.warn("Fournisseur with ID {} was not found in the DB", dto.getFournisseur().getId());
            throw new EntityNotFoundException("Aucun fournisseur avec l'ID" + dto.getFournisseur().getId() + " n'a ete trouve dans la BDD",
                    ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }

        List<String> produitErrors = new ArrayList<>();

        if (dto.getLigneCommandeFournisseurs() != null) {
            dto.getLigneCommandeFournisseurs().forEach(ligCmdFrs -> {
                if (ligCmdFrs.getProduit() != null) {
                    Optional<Produits> produit = produitRepository.findById(ligCmdFrs.getProduit().getId());
                    if (produit.isEmpty()) {
                       produitErrors.add("Le produit avec l'ID " + ligCmdFrs.getProduit().getId() + " n'existe pas");
                    }
                } else {
                    produitErrors.add("Impossible d'enregister une commande avec un produit NULL");
                }
            });
        }

        if (!produitErrors.isEmpty()) {
            log.warn("");
            throw new InvalidEntityException("Produit n'existe pas dans la BDD", ErrorCodes.PRODUIT_NOT_FOUND, produitErrors);
        }
        dto.setDateCommande(Instant.now());
        CommandeFournisseur savedCmdFrs = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(dto));

        if (dto.getLigneCommandeFournisseurs() != null) {
            dto.getLigneCommandeFournisseurs().forEach(ligCmdFrs -> {
                LigneCommandeFournisseur ligneCommandeFournisseur = LigneCommandeFournisseurDto.toEntity(ligCmdFrs);
                ligneCommandeFournisseur.setCommandeFournisseur(savedCmdFrs);
                ligneCommandeFournisseur.setIdEntreprise(savedCmdFrs.getIdEntreprise());
                LigneCommandeFournisseur saveLigne = ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);

                effectuerEntree(saveLigne);
            });
        }

        return CommandeFournisseurDto.fromEntity(savedCmdFrs);
    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if (id == null) {
            log.error("Commande fournisseur ID is NULL");
            return null;
        }
        return commandeFournisseurRepository.findById(id)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande fournisseur n'a ete trouve avec l'ID " + id, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Commande fournisseur CODE is NULL");
            return null;
        }
        return commandeFournisseurRepository.findCommandeFournisseurByCode(code)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande fournisseur n'a ete trouve avec le CODE " + code, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurRepository.findAll().stream()
                .map(CommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande) {
        return ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Commande fournisseur ID is NULL");
            return;
        }
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(id);
        if (!ligneCommandeFournisseurs.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer une commande fournisseur deja utilisee",
                    ErrorCodes.COMMANDE_FOURNISSEUR_ALREADY_IN_USE);
        }
        commandeFournisseurRepository.deleteById(id);
    }

    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if (!StringUtils.hasLength(String.valueOf(etatCommande))) {
            log.error("L'etat de la commande fournisseur is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un etat null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        commandeFournisseur.setEtatCommande(etatCommande);

        CommandeFournisseur savedCommande = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur));
        if (commandeFournisseur.isCommandeLivree()) {
            updateMvtStk(idCommande);
        }
        return CommandeFournisseurDto.fromEntity(savedCommande);
    }

    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            log.error("L'ID de la ligne commande is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une quantite null ou ZERO",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = findLigneCommandeFournisseur(idLigneCommande);

        LigneCommandeFournisseur ligneCommandeFounisseur = ligneCommandeFournisseurOptional.get();
        ligneCommandeFounisseur.setQuantite(quantite);
        ligneCommandeFournisseurRepository.save(ligneCommandeFounisseur);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {
        checkIdCommande(idCommande);
        if (idFournisseur == null) {
            log.error("L'ID du fournisseur is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID fournisseur null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(idFournisseur);
        if (fournisseurOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucun fournisseur n'a ete trouve avec l'ID " + idFournisseur, ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }
        commandeFournisseur.setFournisseur(FournisseurDto.fromEntity(fournisseurOptional.get()));

        return CommandeFournisseurDto.fromEntity(
                commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur))
        );
    }

    @Override
    public CommandeFournisseurDto updateProduit(Integer idCommande, Integer idLigneCommande, Integer idProduit) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdProduit(idProduit, "nouvel");

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);

        Optional<LigneCommandeFournisseur> ligneCommandeFournisseur = findLigneCommandeFournisseur(idLigneCommande);

        Optional<Produits> produitOptional = produitRepository.findById(idProduit);
        if (produitOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucun Produit n'a ete trouve avec l'ID " + idProduit, ErrorCodes.PRODUIT_NOT_FOUND);
        }

        List<String> errors = ProduitsValidator.validate(ProduitDto.fromEntity(produitOptional.get()));
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Produit invalid", ErrorCodes.PRODUIT_NOT_VALID, errors);
        }

        LigneCommandeFournisseur ligneCommandeFournisseurToSaved = ligneCommandeFournisseur.get();
        ligneCommandeFournisseurToSaved.setProduit(produitOptional.get());
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseurToSaved);

        return commandeFournisseur;
    }


    @Override
    public CommandeFournisseurDto deleteProduit(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        // Just to check the LigneCommandeFournisseur and inform the fournisseur in case it is absent
        findLigneCommandeFournisseur(idLigneCommande);
        ligneCommandeFournisseurRepository.deleteById(idLigneCommande);

        return commandeFournisseur;
    }


    private CommandeFournisseurDto checkEtatCommande(Integer idCommande) {
        CommandeFournisseurDto commandeFournisseur = findById(idCommande);
        if (commandeFournisseur.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        return commandeFournisseur;
    }

    private Optional<LigneCommandeFournisseur> findLigneCommandeFournisseur(Integer idLigneCommande) {
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = ligneCommandeFournisseurRepository.findById(idLigneCommande);
        if (ligneCommandeFournisseurOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune ligne commande fournisseur n'a ete trouve avec l'ID " + idLigneCommande, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND);
        }
        return ligneCommandeFournisseurOptional;
    }

    private void checkIdCommande(Integer idCommande) {
        if (idCommande == null) {
            log.error("Commande fournisseur ID is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    private void checkIdLigneCommande(Integer idLigneCommande) {
        if (idLigneCommande == null) {
            log.error("L'ID de la ligne commande is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ligne de commande null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    private void checkIdProduit(Integer idProduit, String msg) {
        if (idProduit == null) {
            log.error("L'ID de " + msg + " is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un " + msg + " ID Produit null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    private void updateMvtStk(Integer idCommande) {
        List<LigneCommandeFournisseur> ligneCommandeFournisseur = ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande);
        ligneCommandeFournisseur.forEach(lig -> {
            effectuerEntree(lig);
        });
    }


    private void effectuerEntree(LigneCommandeFournisseur lig) {
        MvtStkDto mvtStkDto = MvtStkDto.builder()
                .produit(ProduitDto.fromEntity(lig.getProduit()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.ENTREE)
                .sourceMvt(SourceMvtStk.COMMANDE_FOURNISSEUR)
                .quantite(lig.getQuantite())
                .idEntreprise(lig.getIdEntreprise())
                .build();
        mvtStkService.entreeStock(mvtStkDto);
    }
}
