package com.macspace.gestiondestock.services.audit;

import com.macspace.gestiondestock.model.audit.AuditLog;
import com.macspace.gestiondestock.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implémentation du service d'audit MacSpace.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public void enregistrer(AuditLog auditLog) {
        try {
            auditLogRepository.save(auditLog);
            log.info("Audit log enregistré : {} sur {} par utilisateur {}",
                    auditLog.getAction(),
                    auditLog.getEntite(),
                    auditLog.getUtilisateurId());
        } catch (Exception e) {
            log.error("Erreur lors de l'enregistrement du log d'audit : {}", e.getMessage());
        }
    }

    @Override
    public List<AuditLog> findByEntreprise(Integer idEntreprise) {
        return auditLogRepository
                .findByIdEntrepriseOrderByCreatedAtDesc(idEntreprise);
    }

    @Override
    public List<AuditLog> findByEntite(String entite, Integer idEntreprise) {
        return auditLogRepository
                .findByEntiteAndIdEntrepriseOrderByCreatedAtDesc(entite, idEntreprise);
    }

    @Override
    public List<AuditLog> findByUtilisateur(Long utilisateurId) {
        return auditLogRepository
                .findByUtilisateurIdOrderByCreatedAtDesc(utilisateurId);
    }
}