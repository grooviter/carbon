package carbon.carbonite.security.internal

import carbon.carbonite.security.JWTService
import carbon.carbonite.user.User
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import groovy.transform.CompileStatic

import javax.inject.Inject
import javax.inject.Singleton
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

/**
 * JWT helpers using Auth0 library
 *
 * @since 0.2.0
 */
@Singleton
@CompileStatic
class Auth0Service implements JWTService {

    /**
     * The algorithm chosen to sign/cipher the tokens
     *
     * @since 0.2.0
     */
    @Inject
    Algorithm algorithm

    @Override
    Optional<String> createToken(User user) {
        return safelyTry { JWT
                .create()
                .withIssuer('carbonite')
                .withIssuedAt(new Date())
                .sign(algorithm)
        }
    }

    @Override
    Optional<DecodedJWT> verifyToken(String token) {
        return safelyTry { JWT
                .require(algorithm)
                .withIssuer("carbonite")
                .build()
                .verify(token)
        }
    }

    @Override
    String hash(String text) {
        if (text) {
            return MessageDigest
                    .getInstance("SHA-256")
                    .digest(text.getBytes(StandardCharsets.UTF_8))
                    .encodeHex()
                    .toString()
        }
    }

    private <T> Optional<T> safelyTry(Closure<T> execution) {
        try {
            return Optional.ofNullable(execution())
        } catch (Throwable e) {
            return Optional.empty()
        }
    }
}
