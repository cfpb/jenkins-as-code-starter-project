import groovy.io.FileType
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.dsl.MemoryJobManagement
import spock.lang.Specification

class LoadAllScriptsTests extends Specification {
    void 'test my flow build job'() {

        given:
        JobManagement jm = new MemoryJobManagement()
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


