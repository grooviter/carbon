package carbon.carbonite.graphql.internal

import carbon.carbonite.graphql.Context
import carbon.carbonite.graphql.ContextBuilder
import carbon.carbonite.security.SecurityService
import carbon.carbonite.user.User
import io.micronaut.http.HttpRequest

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Builds a GraphQL context with the JWT authenticated user.
 *
 * @since 0.2.0
 */
@Singleton
class JWTContextBuilder implements ContextBuilder {

    @Inject
    SecurityService securityService

    @Override
    Object build(HttpRequest httpRequest) {
        return httpRequest
                .headers
                .authorization
                .flatMap(this.&extractToken)
                .flatMap(this.&checkToken)
                .orElseGet(Context.&newInstance)
    }

    private Optional<Context> checkToken(String token) {
        return Optional
                .ofNullable(token)
                .filter(securityService.&isValidToken)
                .flatMap(this.&resolveUser) as Optional<Context>
    }

    private Optional<String> extractToken(String authorization) {
        return Optional
                .ofNullable(authorization)
                .map { auth -> auth.replace("Bearer ", "") }
    }

    private Optional<Context> resolveUser(String token) {
        return Optional.of(token)
                .map(securityService.&findUserByToken)
                .map { User user ->  new Context(user:user) }
    }
}
