package carbon.carbonite.user.internal

import carbon.carbonite.user.Repository
import carbon.carbonite.user.User
import groovy.sql.GroovyRowResult
import groovy.sql.Sql

import javax.inject.Inject

/**
 * Repository implementation to handle database
 * access over {@link User}
 *
 * @since 0.2.0
 */
class JdbcRepository implements Repository {

    /**
     * Database connection
     *
     * @since 0.2.0
     */
    @Inject
    Sql sql

    @Override
    User findUserByUUID(UUID uuid) {
        return sql
            .firstRow("SELECT * FROM users WHERE uuid = ?", uuid)
            .findResult(this.&toUser)
    }

    @Override
    User findUserByEmail(String email) {
        return sql
            .firstRow("SELECT * FROM users WHERE email = ?", email)
            .findResult(this.&toUser)
    }

    @Override
    User findUserByEmailAndPassword(String username, String password) {
        return sql
            .firstRow("SELECT * FROM users WHERE email = ? AND password = ?", password)
            .findResult(this.&toUser)
    }

    private static User toUser(GroovyRowResult result) {
        return new User(
            uuid: result.get("uuid") as UUID,
            name: result.get("name") as String,
            email: result.get("email") as String
        )
    }
}
