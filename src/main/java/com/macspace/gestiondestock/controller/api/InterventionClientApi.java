package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.InterventionClientDto;
import com.macspace.gestiondestock.dto.LigneInterventionClientDto;
import com.macspace.gestiondestock.model.EtatIntervention;
import io.swagger.annotations.Api;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.macspace.gestiondestock.utils.Constants.APP_ROOT;

@Api("interventionsclients")
public interface InterventionClientApi {

    /**
     * Sauvegarde une nouvelle intervention client.
     *
     * @param dto l'intervention client à sauvegarder
     * @return l'intervention client sauvegardée
     */
    @PostMapping(APP_ROOT + "/interventionsclients/create")
    ResponseEntity<InterventionClientDto> save(@RequestBody InterventionClientDto dto);

    /**
     * Met à jour l'état d'une intervention client.
     *
     * @param idIntervention l'identifiant de l'intervention
     * @param etatIntervention le nouvel état de l'intervention
     * @return l'intervention client mise à jour
     */
    @PatchMapping(APP_ROOT + "/interventionsclients/update/etat/{idIntervention}/{etatIntervention}")
    ResponseEntity<InterventionClientDto> updateEtatIntervention(@PathVariable("idIntervention") Integer idIntervention, @PathVariable("etatIntervention") EtatIntervention etatIntervention);

    /**
     * Met à jour la quantité d'un article dans une intervention client.
     *
     * @param idIntervention l'identifiant de l'intervention
     * @param idLigneIntervention l'identifiant de la ligne d'intervention
     * @param quantite la nouvelle quantité
     * @return l'intervention client mise à jour
     */
    @PatchMapping(APP_ROOT + "/interventionsclients/update/quantite/{idIntervention}/{idLigneIntervention}/{quantite}")
    ResponseEntity<InterventionClientDto> updateQuantiteIntervention(@PathVariable("idIntervention") Integer idIntervention,
                                                                     @PathVariable("idLigneIntervention") Integer idLigneIntervention, @PathVariable("quantite") BigDecimal quantite);

    /**
     * Met à jour les informations d'un client dans une intervention client.
     *
     * @param idIntervention l'identifiant de l'intervention
     * @param idClient l'identifiant du client
     * @return l'intervention client mise à jour
     */
    @PatchMapping(APP_ROOT + "/interventionsclients/update/client/{idIntervention}/{idClient}")
    ResponseEntity<InterventionClientDto> updateClient(@PathVariable("idIntervention") Integer idIntervention, @PathVariable("idClient") Integer idClient);

    /**
     * Met à jour les informations d'un produit dans une intervention client.
     *
     * @param idIntervention l'identifiant de l'intervention
     * @param idLigneIntervention l'identifiant de la ligne d'intervention
     * @param idProduit l'identifiant du produit
     * @return l'intervention client mise à jour
     */
    @PatchMapping(APP_ROOT + "/interventionsclients/update/produit/{idIntervention}/{idLigneIntervention}/{idProduit}")
    ResponseEntity<InterventionClientDto> updateProduit(@PathVariable("idIntervention") Integer idIntervention,
                                                        @PathVariable("idLigneIntervention") Integer idLigneIntervention, @PathVariable("idProduit") Integer idProduit);

    /**
     * Supprime un produit d'une intervention client.
     *
     * @param idIntervention l'identifiant de l'intervention
     * @param idLigneIntervention l'identifiant de la ligne d'intervention
     * @return l'intervention client mise à jour après la suppression du produit
     */
    @DeleteMapping(APP_ROOT + "/interventionsclients/delete/produit/{idIntervention}/{idLigneIntervention}")
    ResponseEntity<InterventionClientDto> deleteProduit(@PathVariable("idIntervention") Integer idIntervention, @PathVariable("idLigneIntervention") Integer idLigneIntervention);

    /**
     * Récupère une intervention client par son identifiant.
     *
     * @param idInterventionClient l'identifiant de l'intervention
     * @return l'intervention client trouvée
     */
    @GetMapping(APP_ROOT + "/interventionsclients/{idInterventionClient}")
    ResponseEntity<InterventionClientDto> findById(@PathVariable Integer idInterventionClient);

    /**
     * Récupère une intervention client par son code.
     *
     * @param code le code de l'intervention
     * @return l'intervention client trouvée
     */
    @GetMapping(APP_ROOT + "/interventionsclients/filter/{codeInterventionClient}")
    ResponseEntity<InterventionClientDto> findByCode(@PathVariable("codeInterventionClient") String code);

    /**
     * Récupère toutes les interventions clients.
     *
     * @return la liste de toutes les interventions clients
     */
    @GetMapping(APP_ROOT + "/interventionsclients/all")
    ResponseEntity<List<InterventionClientDto>> findAll();

    /**
     * Récupère toutes les lignes d'une intervention client par l'identifiant de l'intervention.
     *
     * @param idIntervention l'identifiant de l'intervention
     * @return la liste des lignes de l'intervention client
     */
    @GetMapping(APP_ROOT + "/interventionsclients/lignesIntervention/{idIntervention}")
    ResponseEntity<List<LigneInterventionClientDto>> findAllLignesInterventionsClientByInterventionClientId(@PathVariable("idIntervention") Integer idIntervention);

    /**
     * Supprime une intervention client par son identifiant.
     *
     * @param id l'identifiant de l'intervention
     * @return une réponse vide indiquant que la suppression a réussi
     */
    @DeleteMapping(APP_ROOT + "/interventionsclients/delete/{idInterventionClient}")
    ResponseEntity<Void> delete(@PathVariable("idInterventionClient") Integer id);

}
