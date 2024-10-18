/*
 * Copyright 2024 OnePlat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.oneplat.teamkeeper.attendance.configuration;

import java.util.Date;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import co.oneplat.teamkeeper.attendance.api.auth.exception.LoginException;
import co.oneplat.teamkeeper.attendance.api.auth.exception.LoginExceptionType;

@Component
public class JwtTokenGenerator {

//    @Value("${jwt.secret-key}")
    private String secretKey;
//    @Value("${jwt.expire-length}")
    private long expireTimeMilliSecond;

    public String generateToken(final String id) {
        final Claims claims = Jwts.claims();
        claims.put("memberId", id);
        final Date now = new Date();
        final Date expiredDate = new Date(now.getTime() + expireTimeMilliSecond);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String extractMemberId(final String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .get("memberId")
                    .toString();
        } catch (final ExpiredJwtException exception) {
            throw new LoginException(LoginExceptionType.EXPIRED_ACCESS_TOKEN);
        } catch (final Exception exception) {
            throw new LoginException(LoginExceptionType.INVALID_ACCESS_TOKEN);
        }
    }
}
