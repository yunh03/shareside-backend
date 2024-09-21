package kr.yuns.shareside_backend.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import kr.yuns.shareside_backend.domain.auth.data.entity.User;
import kr.yuns.shareside_backend.domain.auth.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.security.Key;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    private Key key;

    // Access Token: 1시간 유효
    private final long accessTokenValidMillisecond = 1000L * 60 * 60;

    // Refresh Token: 2주 유효
    private final long refreshTokenValidMillisecond = 1000L * 60 * 60 * 24 * 14;

    @PostConstruct
    public void init() {
        try {
            if (secretKey == null || secretKey.trim().isEmpty()) {
                throw new IllegalArgumentException("Secret key must not be null or empty");
            }
            key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        } catch (IllegalArgumentException e) {
            log.error("Failed to initialize JwtTokenProvider", e);
            throw e;
        }
    }

    public String createAccessToken(String email, List<String> roles) {
        return createToken(email, roles, accessTokenValidMillisecond);
    }

    public String createRefreshToken(String email) {
        return createToken(email, new ArrayList<>(), refreshTokenValidMillisecond);
    }

    private String createToken(String email, List<String> roles, long validMillisecond) {
        log.info("JwtTokenProvider / createToken(): 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);
    
        Date now = new Date();
    
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validMillisecond))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    
        log.info("JwtTokenProvider / createToken(): 토큰 생성 완료");
        return token;
    }

    public Authentication getAuthentication(String token) {
        log.info("Getting authentication for token: {}", token);

        try {
            String email = getUserEmail(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            log.info("Authentication retrieved for user: {}", userDetails.getUsername());
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } catch (UsernameNotFoundException e) {
            log.error("User not found for email: {}", token, e);
            return null;
        }
    }

    public String getUserEmail(String token){
        log.info("JwtTokenProvider / getUserEmail(): 토큰 기반 회원 구별 정보 추출");
        String info = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        log.info("JwtTokenProvider / getUserEmail(): 토큰 기반 회원 구별 정보 추출 완료, info : {}", info);
        return info;
    }
    
    public boolean validateToken(String token){
        log.info("JwtTokenProvider / validateToken(): 토큰 유효 체크 시작");
    
        try{
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            log.info("JwtTokenProvider / validateToken(): 토큰 유효 체크 완료");
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e){
            log.info("JwtTokenProvider / validateToken(): 토큰 유효 체크 예외 발생");
            return false;
        }
    }
    
    public User getUserEntity(String token) {
        log.info("JwtTokenProvider / getUserId(): 토큰 기반 회원 고유 ID 정보 추출");
        String info = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        return userRepository.getByEmail(info);
    }

    public String refreshToken(String refreshToken, String email) {
        log.info("JwtTokenProvider / refreshToken(): 리프레시 토큰으로 액세스 토큰 재발급 시작");

        if (validateToken(refreshToken)) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return createAccessToken(email, roles);
        } else {
            log.error("JwtTokenProvider / refreshToken(): 리프레시 토큰이 유효하지 않음");
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }
    }

    public String extractToken(HttpServletRequest request){
        log.info("JwtTokenProvider / extractToken(): 헤더 토큰 값 분리");
        String token = request.getHeader("Authorization");
        if(!token.isEmpty() && token != null){
            token = token.replace("Bearer ","").trim();
        }
        log.info("JwtTokenProvider / extractToken(): 분리된 토큰 값 {}", token);
        return token;
    }

    // HTTP Header에서 Token 추출
    public String resolveToken(HttpServletRequest request) {
        log.info("JwtTokenProvider / resolveToken(): HTTP 헤더에서 Token 값 추출");
        return request.getHeader("X-AUTH-TOKEN");
    }
}