package carbon.carbonite.user

import groovy.transform.Immutable

/**
 * Represents an authenticated user
 *
 * @since 0.2.0
 */
@Immutable
class User {
    /**
     * User's identifier
     *
     * @since 0.2.0
     */
    UUID uuid

    /**
     * User's name
     *
     * @since 0.2.0
     */
    String name

    /**
     * User's email
     *
     * @since 0.2.0
     */
    String email
}
