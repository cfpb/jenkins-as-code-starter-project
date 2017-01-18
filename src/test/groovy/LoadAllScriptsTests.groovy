import groovy.io.FileType
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.dsl.MemoryJobManagement
import spock.lang.Ignore
import spock.lang.Specification

class LoadAllScriptsTests extends Specification {
    //@Ignore
    void 'test my flow build job'() {

        given:
        JobManagement jm = new MemoryJobManagement()

        //for the test, set the params we expect to exist in all our Jenkinses

        jm.parameters['JAC_ENVIRONMENT'] = "dev"
        jm.parameters['JAC_HOST'] = "aws"

        List<File> files = []

        new File('jobs').eachFileRecurse(FileType.FILES) {
            if (it.name.endsWith('.groovy')) {
                files << it
            }
        }

        //this helps resolve readFileFromWorkspace in test context
        // jm.availableFiles << [
        // 'scripts/run-fabric-deployment-script.sh' : new File('scripts/run-fabric-deployment-script.sh').text
        // ]

        when:
        files.each { file ->
            new DslScriptLoader(jm).runScript(file.text)
        }

        then:
        noExceptionThrown()
    }


}


