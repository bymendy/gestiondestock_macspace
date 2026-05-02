package com.macspace.gestiondestock.config;

import com.macspace.gestiondestock.services.auth.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

/**
 * Configuration de la sécurité Spring Security pour MacSpace.
 * Configure JWT, CORS, les routes publiques/privées,
 * le DaoAuthenticationProvider et la gestion des sessions sans état.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final ApplicationUserDetailsService applicationUserDetailsService;
    private final ApplicationRequestFilter applicationRequestFilter;
    private final AuthenticationConfiguration authenticationConfiguration;

    /**
     * Constructeur avec injection de dépendances.
     */
    @Autowired
    public SecurityConfiguration(
            ApplicationUserDetailsService applicationUserDetailsService,
            ApplicationRequestFilter applicationRequestFilter,
            AuthenticationConfiguration authenticationConfiguration) {
        this.applicationUserDetailsService = applicationUserDetailsService;
        this.applicationRequestFilter = applicationRequestFilter;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    /**
     * Configure le DaoAuthenticationProvider avec le UserDetailsService
     * et le PasswordEncoder BCrypt pour l'authentification.
     *
     * @return Le DaoAuthenticationProvider configuré.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(applicationUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Expose l'AuthenticationManager depuis la configuration Spring.
     *
     * @return L'AuthenticationManager configuré.
     * @throws Exception En cas d'erreur de configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Définit le PasswordEncoder BCrypt pour le hashage des mots de passe.
     *
     * @return Le BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configure le filtre CORS pour MacSpace.
     * Autorise toutes les origines, headers et méthodes HTTP nécessaires.
     *
     * @return Le CorsFilter configuré.
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.setAllowedHeaders(Arrays.asList(
                "Origin", "Content-Type", "Accept", "Authorization"));
        config.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * Configure la chaîne de filtres de sécurité Spring Security.
     * Définit les routes publiques, les routes sécurisées,
     * la politique de session stateless et les filtres JWT.
     *
     * @param http La configuration HttpSecurity.
     * @return La SecurityFilterChain configurée.
     * @throws Exception En cas d'erreur de configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .addFilterBefore(corsFilter(), SessionManagementFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                new AntPathRequestMatcher("/**/authenticate"),
                                new AntPathRequestMatcher("/**/entreprises/create"),
                                new AntPathRequestMatcher("/**/utilisateurs/create"),
                                new AntPathRequestMatcher("/**/datawarehouse/init"),
                                new AntPathRequestMatcher("/api-docs"),
                                new AntPathRequestMatcher("/api-docs/**"),
                                new AntPathRequestMatcher("/v2/api-docs"),
                                new AntPathRequestMatcher("/v3/api-docs"),
                                new AntPathRequestMatcher("/v3/api-docs/**"),
                                new AntPathRequestMatcher("/swagger-resources"),
                                new AntPathRequestMatcher("/swagger-resources/**"),
                                new AntPathRequestMatcher("/configuration/ui"),
                                new AntPathRequestMatcher("/configuration/security"),
                                new AntPathRequestMatcher("/swagger-ui.html"),
                                new AntPathRequestMatcher("/swagger-ui/**"),
                                new AntPathRequestMatcher("/webjars/**")
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(applicationRequestFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}