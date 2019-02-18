package carbon.carbonite.scripts.internal

import carbon.carbonite.scripts.Repository
import carbon.carbonite.scripts.Script
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import groovy.transform.CompileStatic

import javax.inject.Inject
import javax.inject.Singleton
import javax.sql.DataSource

/**
 * Database access for {@link Script} instances
 *
 * @since 0.2.0
 */
@Singleton
@CompileStatic
class JdbcRepository implements Repository {

    /**
     * Configured {@link DataSource}
     *
     * @since 0.2.0
     */
    @Inject
    DataSource dataSource

    @Override
    List<Script> list() {
        String query = $/
            WITH x AS (
              SELECT
                DISTINCT(script),
                FIRST_VALUE(uuid) OVER (PARTITION BY script ORDER BY created_at DESC) AS latest
              FROM script_ledger) SELECT
                s.uuid,
                s.name,
                s.description, 
                sl.status
              FROM 
                x, 
                scripts s, 
                script_ledger sl
              WHERE
                s.uuid = x.script AND
                sl.uuid = x.latest          
        /$

        return new Sql(dataSource)
            .rows(query)
            .collect(JdbcRepository.&toScript)
    }

    private static Script toScript(GroovyRowResult row) {
        return new Script(
            uuid: row.get("uuid") as UUID,
            name: row.get("name"),
            description: row.get("description"),
            status: row.get('status')
        )
    }
}
