package com.macspace.gestiondestock.aspect;

import com.macspace.gestiondestock.annotation.Auditable;
import com.macspace.gestiondestock.model.audit.AuditLog;
import com.macspace.gestiondestock.model.auth.ExtendedUser;
import com.macspace.gestiondestock.services.audit.AuditLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Aspect AOP MacSpace — intercepte les méthodes annotées @Auditable
 * et enregistre automatiquement les actions dans l'audit log.
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditLogService auditLogService;

    @jakarta.annotation.PostConstruct
    public void init() {
        log.info("✅ AuditAspect chargé et actif !");
    }

    /**
     * Intercepte toutes les méthodes annotées avec @Auditable.
     */
    @Around("@annotation(com.macspace.gestiondestock.annotation.Auditable) && @annotation(auditable)")
    public Object intercepter(ProceedingJoinPoint joinPoint,
                              Auditable auditable) throws Throwable {
        log.info("🔍 AuditAspect déclenché sur : {}", joinPoint.getSignature().getName());
        Object result = null;
        try {
            /* Exécuter la méthode originale */
            result = joinPoint.proceed();

            /* Récupérer l'utilisateur connecté */
            String utilisateurNom = "anonyme";
            Integer idEntreprise = null;

            Authentication auth = SecurityContextHolder
                    .getContext().getAuthentication();

            if (auth != null && auth.getPrincipal() instanceof ExtendedUser user) {
                utilisateurNom = user.getUsername();
                idEntreprise = user.getIdEntreprise();
            }

            /* Récupérer l'adresse IP */
            String ipAddress = getIpAddress();

            /* Construire et enregistrer le log */
            AuditLog auditLog = AuditLog.builder()
                    .utilisateurNom(utilisateurNom)
                    .action(auditable.action())
                    .entite(auditable.entite())
                    .detail(joinPoint.getSignature().getName())
                    .ipAddress(ipAddress)
                    .idEntreprise(idEntreprise)
                    .build();

            auditLogService.enregistrer(auditLog);

        } catch (Throwable e) {
            log.error("Erreur dans AuditAspect : {}", e.getMessage());
            throw e;
        }
        return result;
    }

    /**
     * Récupère l'adresse IP de la requête HTTP courante.
     */
    private String getIpAddress() {
        try {
            ServletRequestAttributes attrs =
                    (ServletRequestAttributes) RequestContextHolder
                            .getRequestAttributes();
            if (attrs != null) {
                HttpServletRequest request = attrs.getRequest();
                String ip = request.getHeader("X-Forwarded-For");
                return (ip != null && !ip.isEmpty())
                        ? ip.split(",")[0]
                        : request.getRemoteAddr();
            }
        } catch (Exception e) {
            log.warn("Impossible de récupérer l'adresse IP : {}", e.getMessage());
        }
        return "inconnue";
    }
}