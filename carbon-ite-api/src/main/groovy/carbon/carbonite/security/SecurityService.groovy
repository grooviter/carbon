package carbon.carbonite.security

import carbon.carbonite.user.User

/**
 * All operations to enforce security
 *
 * @since 0.2.0
 */
interface SecurityService {

    /**
     * Looks for a user matching the provided username and password
     *
     * @param username the user's username
     * @param password the user's password
     * @return a {@link User} or null if the credentials don't match any user
     * @since 0.2.0
     */
    User login(String username, String password)

    /**
     * Finds a given user from the provided token
     *
     * @param token the token to get a {@link User} from
     * @return a {@link User} or null if the token doesn't match any user
     * @since 0.2.0
     */
    User findUserByToken(String token)

    /**
     * Validates whether the provided token is valid or not
     *
     * @param token the token to validate
     * @return true if it's valid, false otherwise
     * @since 0.2.0
     */
    Boolean isValidToken(String token)
}
