package carbon.carbonite.scripts

import carbon.carbonite.scripts.internal.DataFetcherImpl
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
            list() >> [new Script()]
        }

        and: 'using it in the fetcher implementation'
        DataFetcher fetcher = new DataFetcherImpl(repository: repository)

        when: 'invoking runningScripts()'
        List<Script> scriptList = fetcher.runningScripts(null)

        then: 'we should get only one result'
        scriptList.size() == 1
    }
}
