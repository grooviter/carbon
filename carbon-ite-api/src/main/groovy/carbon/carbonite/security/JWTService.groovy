package carbon.carbonite.security

import carbon.carbonite.user.User
import com.auth0.jwt.interfaces.DecodedJWT

/**
 * Represents the implementation of the most relevant JWT related operations
 *
 * @since 0.2.0
 */
interface JWTService {

    /**
     * Creates a JWT token from the information of a given {@link User}
     *
     * @param user the user to create a token from
     * @return a JWT token
     * @since 0.2.0
     */
    Optional<String> createToken(User user)

    /**
     * Verifies that the provided token is valid
     *
     * @param token the token to validate
     * @return true if it's valid false otherwise
     * @since 0.2.0
     */
    Optional<DecodedJWT> verifyToken(String token)

    /**
     * Hashes a given text using SHA-256 as hashing algorithm
     *
     * @param text the text we'd like to hash
     * @return the hash
     * @since 0.2.0
     */
    String hash(String text)
}
