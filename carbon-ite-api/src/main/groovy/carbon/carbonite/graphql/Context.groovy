package carbon.carbonite.graphql

import carbon.carbonite.user.User
import groovy.transform.Immutable

/**
 * Represents a GraphQL context
 *
 * @since 0.2.0
 */
@Immutable
class Context {

    /**
     * The current authenticated {@link User} if any
     *
     * @since 0.2.0
     */
    User user
}
