package carbon.carbonite.security.internal

import com.auth0.jwt.algorithms.Algorithm
import io.micronaut.context.annotation.Value

import javax.inject.Provider
import javax.inject.Singleton

/**
 * Provides an instance of type {@link Algorithm} which will
 * be used to verify and create JWT tokens
 *
 * @since 0.2.0
 */
@Singleton
class AlgorithmProvider implements Provider<Algorithm> {

    /**
     * Secret taken from configuration
     *
     * @since 0.2.0
     */
    @Value('jwt.secret')
    String secret

    @Override
    Algorithm get() {
        return Algorithm.HMAC256(secret)
    }
}
