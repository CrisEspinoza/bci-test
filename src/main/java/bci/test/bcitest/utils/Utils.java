package bci.test.bcitest.utils;

import bci.test.bcitest.configuration.JWTAuthorizationFilter;
import bci.test.bcitest.model.User;
import bci.test.bcitest.repository.PhoneRepository;
import bci.test.bcitest.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    @Autowired
    private UserRepository userRepository;

    @Value("${user.password}")
    public final static String passwordRE = "^[0-9,$]*$";

    @Value("${user.email}")
    public final static String emailRE = "[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}";

    public static ResponseEntity<?> exceptionCatch (String message, Integer status){
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("mensaje", message);
        return ResponseEntity.status(status).body(response);
    }

    public static ResponseEntity<?> responseTry (Object json){
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("mensaje", json);
        return ResponseEntity.ok(response);
    }

    public static String generateJWTToken(String id, String username) {
        String secretKey = "bci";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId(id)
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1800000)) // 30 minutes
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    public User getUserFromToken(String token) throws Exception {
        JWTAuthorizationFilter jwtAuthorizationFilter = new JWTAuthorizationFilter();
        String email = jwtAuthorizationFilter.getUuidFromToken(token);
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new Exception("El usuario no esta dentro de la base de datos");
        return user;
    }
}
