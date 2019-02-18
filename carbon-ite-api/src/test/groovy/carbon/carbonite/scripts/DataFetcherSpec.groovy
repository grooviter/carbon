package carbon.carbonite.scripts

import carbon.carbonite.graphql.TestUtils
import carbon.carbonite.scripts.internal.DataFetcherImpl
import graphql.schema.DataFetchingEnvironment
import spock.lang.Specification

/**
 * Tests {@link DataFetcher}
 *
 * @since 0.2.0
 */
class DataFetcherSpec extends Specification {

    void 'runningScripts: list running scripts'() {
        given: 'a mocked repository'
        Repository repository = Stub(Repository) {
            list() >> (1..howMany).collect { new Script() }
        }

        and: 'using it in the fetcher implementation'
        DataFetcher fetcher = new DataFetcherImpl(repository: repository)

        when: 'invoking runningScripts()'
        DataFetchingEnvironment environment = TestUtils.environmentWithArgs()
        List<Script> scriptList = fetcher.runningScripts(environment)

        then: 'we should get only one result'
        scriptList.size() == howMany

        where: 'possible number of scripts are'
        howMany << [1, 2, 3, 4, 5, 6]
    }
}
