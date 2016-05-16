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
        def params = jm.getParameters()
        params['JAC_ENVIRONMENT'] = "dev"
        params['JAC_HOST'] = "aws"

        List<File> files = []

        new File('jobs').eachFileRecurse(FileType.FILES) {
            if (it.name.endsWith('.groovy')) {
                files << it
            }
        }

        when:
        files.each{file->
            DslScriptLoader.runDslEngine file.text, jm
        }

        then:
        noExceptionThrown()
    }


}


