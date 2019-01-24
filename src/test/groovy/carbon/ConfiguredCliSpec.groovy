package carbon

import spock.lang.Specification

/**
 * Checks {@link ConfiguredCli} class
 *
 * @since 0.1.0
 */
@SuppressWarnings('VariableTypeRequired')
class ConfiguredCliSpec extends Specification {

    void 'test create table with default key'() {
        given: 'a configured Sql object'
        def path = 'src/test/resources/carbon/sql.yaml'
        def sql = Cli.withConfig(path).sql().connection

        when: 'creating a new table'
        sql.execute '''
        DROP TABLE IF EXISTS TEST;
        CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255));
        '''

        then: 'no exception should be thrown'
        notThrown()
    }

    void 'test create table with default key failure'() {
        given: 'a configured Sql object'
        def path = 'src/test/resources/carbon/sql-empty.yaml'

        when: 'creating a new table'
        Cli.withConfig(path).sql().connection

        then: 'no exception should be thrown'
        thrown(AssertionError)
    }

    void 'test create table with custom key sucessfully'() {
        given: 'a configured Sql object'
        def path = 'src/test/resources/carbon/sql.yaml'
        def sql = Cli.withConfig(path).sql().getConnection('sql2')

        when: 'creating a new table'
        sql.execute '''
        DROP TABLE IF EXISTS TEST;
        CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255));
        '''

        then: 'no exception should be thrown'
        notThrown()
    }

    void 'test create table with custom key failure'() {
        given: 'a configured Sql object'
        def path = 'src/test/resources/carbon/sql.yaml'

        when: 'creating a new table'
        Cli.withConfig(path).sql().getConnection('sql3')

        then: 'no exception should be thrown'
        thrown(AssertionError)
    }

    void 'test create a zip file successfully'() {
        given: 'a source and destination files'
        def dir = File.createTempDir()
        def source = new File(dir, 'source.txt')
        def destination = new File(dir, 'dest.zip')

        and: 'create source file'
        source.text = 'something'

        when: 'zipping source file'
        Cli.withConfig().zip().zip("${dir}", "$destination")

        then: 'destination file should be created'
        destination.exists()
    }
}
