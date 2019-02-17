package carbon.carbonite.scripts

import groovy.sql.Sql

import javax.inject.Singleton

@Singleton
class SqlRepository {

    Sql sql

    List<Script> list(Integer offset, Integer max) {
        return [
            new Script(name: 'nasdaq-100', description: 'nasdaq-100 stock values'),
            new Script(name: 'soccer-day', description: 'spanish soccer league')
        ]
    }
}
