package carbon.carbonite.scripts

import carbon.carbonite.graphql.TestUtils
import carbon.carbonite.scripts.internal.ScriptFetcherImpl
import graphql.schema.DataFetchingEnvironment
import spock.lang.Specification

/**
 * Tests {@link ScriptFetcher}
 *
 * @since 0.2.0
 */
class ScriptFetcherSpec extends Specification {

    void 'runningScripts: list running scripts'() {
        given: 'a mocked repository'
        Repository repository = Stub(Repository) {
            list() >> (1..howMany).collect { new Script() }
        }

        and: 'using it in the fetcher implementation'
        ScriptFetcher fetcher = new ScriptFetcherImpl(repository: repository)

        when: 'invoking runningScripts()'
        DataFetchingEnvironment environment = TestUtils.environmentWithArgs()
        List<Script> scriptList = fetcher.runningScripts(environment)

        then: 'we should get only one result'
        scriptList.size() == howMany

        where: 'possible number of scripts are'
        howMany << [1, 2, 3, 4, 5, 6]
    }
}
