package carbon.carbonite.user

/**
 * Contract representing database access over {@Link User}
 *
 * @since 0.2.0
 */
interface Repository {

    /**
     * Fetches a {@link User} with a specific {@link UUID}
     * @param uuid user's identifier
     * @return an instance of {@link carbon.carbonite.user.User} if identifier matches
     * the user or null otherwise
     * @since 0.2.0
     */
    User findUserByUUID(UUID uuid)

    /**
     * Fetches a {@link User} by the provided email
     *
     * @param email the user's email
     * @return a {@link User} or null if email doesn't match any user
     * @since 0.2.0
     */
    User findUserByEmail(String email)

    /**
     * Fetches a user by the provided email and password
     *
     * @param email user's email
     * @param password user's password
     * @return a {@link User} or null if the email/password don't match any user
     * @since 0.2.0
     */
    User findUserByEmailAndPassword(String email, String password)
}
