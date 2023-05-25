package com.example.AppSysem.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    private static final String SECRET_KEY ="MR_POLA";
    private static final int TOKEN_VALIDITY = 3600 * 5;

   public String getUserNameFromToken(String token) {
       return getClaimsFromToken(token,Claims::getSubject);

   }

   private <T> T getClaimsFromToken(String token, Function<Claims,T>claimResolver){
       final Claims claims= getAllClaimsFromToken(token);
       return claimResolver.apply(claims);

   }

   private Claims getAllClaimsFromToken(String token){

       return Jwts.parser().setSigningKey(SECRET_KEY.getBytes(Charset.forName("UTF-8"))).parseClaimsJws(token).getBody();
   }

   public boolean validateToken(String token, UserDetails userDetails){
       String userName = getUserNameFromToken(token);
       return (userName.equals(userDetails.getUsername()) && isTokenExpired(token));

   }

   private boolean isTokenExpired(String token){
       final Date expirationDate = getExpirationDateFromToken(token);
       return expirationDate.before(new Date());

   }
   private Date getExpirationDateFromToken(String token){

       return getClaimsFromToken(token,Claims::getExpiration);
   }

   public String generateToken(UserDetails userDetails){
       Map<String,Object> claims = new HashMap<>();
       return Jwts.builder()
               .setClaims(claims)
               .setSubject(userDetails.getUsername())
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis()+TOKEN_VALIDITY *1000))
               .signWith(SignatureAlgorithm.HS512,SECRET_KEY.getBytes(Charset.forName("UTF-8")))
               .compact()
               ;

   }
}
