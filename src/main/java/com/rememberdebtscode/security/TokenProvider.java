package com.rememberdebtscode.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.validity-in-seconds}")
    private long jwtValidityInSeconds;

    private Key key;
    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        // Generar la clave para firmar el JWT a partir del secreto configurado
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));

        // Inicializar el parser JWT con la clave generada para firmar y validar tokens
        jwtParser = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build();
    }

    // TODO: Método para crear el token JWT con los detalles del usuario autenticado
    public String createAccessToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Integer id = userPrincipal.getId();
        String email = authentication.getName();
        String role = authentication.getAuthorities().stream().findFirst().orElseThrow().getAuthority();

        return Jwts.builder()
                .setSubject(email)
                .claim("id", id)
                .claim("role", role)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date(System.currentTimeMillis() + jwtValidityInSeconds * 1000))
                .compact();
    }

    // TODO: Método para obtener la autenticación a partir del token JWT
    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        String role = claims.get("role").toString();
        Integer id = claims.get("id", Integer.class);
        String email = claims.getSubject();
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

        // Crea el principal personalizado
        UserPrincipal principal = new UserPrincipal(id, email, "", authorities, null);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // TODO: Método para validar el token JWT (si está correctamente firmado y no ha
    // expirado)
    public boolean validateToken(String token) {
        try {
            // TODO: Parsear el token JWT para verificar su validez
            jwtParser.parseClaimsJws(token);
            return true; // El token es válido
        } catch (JwtException e) {
            return false; // El token no es válido
        }
    }
}