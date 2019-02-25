package carbon.carbonite.security.internal

import carbon.carbonite.security.JWTService
import carbon.carbonite.security.SecurityService
import carbon.carbonite.user.Repository as UserRepository
import carbon.carbonite.user.User
import com.auth0.jwt.interfaces.DecodedJWT

import javax.inject.Inject

/**
 * Default implementation of the {@link SecurityService}
 *
 * @since 0.2.0
 */
class SecurityServiceImpl implements SecurityService {

    /**
     * Provides operations over JWT
     *
     * @since 0.2.0
     */
    @Inject
    JWTService jwtService

    /**
     * Provides database access over {@link User} instances
     *
     * @since 0.2.0
     */
    @Inject
    UserRepository userRepository

    @Override
    User login(String username, String password) {
        return userRepository.findUserByEmailAndPassword(username, password)
    }

    @Override
    User findUserByToken(String token) {
        return jwtService
                .verifyToken(token)
                .map { DecodedJWT jwt -> jwt.getClaim("user").asString() }
                .map(userRepository.&findUserByEmail)
                .orElse(null)
    }

    @Override
    Boolean isValidToken(String token) {
        return jwtService.verifyToken(token).isPresent()
    }
}
