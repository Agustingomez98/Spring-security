package com.curso.springsecurity.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;
    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(UserDetails user,Map<String,Object> extraClaims) {

        Date issuedAt = new Date(System.currentTimeMillis()); //Milisegundo del momento que se generan
        Date expiration = new Date((EXPIRATION_IN_MINUTES * 60 *1000) + issuedAt.getTime()); //Fecha de expriacion en milisegundo

        String jwt = Jwts.builder() //Se setean los claims, que son las afirmaciones que se incluyen en el cuerpo del token y contine informacion sobre el usuario
                .setClaims(extraClaims)
                .setSubject(user.getUsername()) //Propietario de token
                .setIssuedAt(issuedAt) //Fecha de admision
                .setExpiration(expiration)
                //Propiedades del header
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .signWith(generateKey(), SignatureAlgorithm.HS256) //Firma y algoritmo
                .compact(); //Devuelve un String

        return jwt;
    }

    private Key generateKey() {

        byte[] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY); //Arreglo de bytes que equivale a mi contraseña, la decodifica y la guarda
        return Keys.hmacShaKeyFor(passwordDecoded); //Clase de utileria Keys
    }

    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {

        return Jwts.parserBuilder().setSigningKey(generateKey()).build() //
                .parseClaimsJws(jwt).getBody();
    }

    public String extractJwtFromRequest(HttpServletRequest request) {
        //1-Obntener encabezado http llamado Authorization

        String authorizationHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
            return null;
        }

        //2- Obtener token JWT desde el encabezado

        String jwt = authorizationHeader.split(" ")[1];
        return jwt;
    }

    public Date extractExpiration(String jwt) {
        return extractAllClaims(jwt).getExpiration();
    }
}
