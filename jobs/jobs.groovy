import jenkins.automation.builders.*
import jenkins.automation.utils.EnvironmentUtils

// the "JAC_ENVIRONMENT" global variable is set in all of our Jenkinses
// if you're testing this in a local Jenkins, you must configure this global variable.
// Set it to "DEV", "STAGE", or "PROD" depending on what you want to test
// If you're not sure, set it to "DEV"

def env = EnvironmentUtils.getInstance("${JAC_ENVIRONMENT}")
println "Environment is " + env.getEnv()

if (env.isDev()) {
    //do environment-specific things here
}

// See http://cfpb.github.io/jenkins-automation/ for the niceties provided by our jenkins-automation project

// Our custom builders are simply wrappers around job-dsl job types (https://github.com/jenkinsci/job-dsl-plugin)
// In the `with {}` closure, you have full access to the job-dsl API (https://jenkinsci.github.io/job-dsl-plugin/)
// and anything else job-dsl supports, such as the `configure` block (https://github.com/jenkinsci/job-dsl-plugin/wiki/The-Configure-Block)

def pipelineScript = """
    pipeline {
        agent { label 'master' }
        stages {
            stage('hello') {
                steps {
                    sh 'echo "Hello World"'
                }
            }
        }
    }
"""

new PipelineJobBuilder(
        name: 'Hello-Pipeline-Job',
        description: 'Example Pipeline job',
        pipelineScript: pipelineScript,
        sandboxFlag: false
).build(this).with {
    // example of extending a job with the job-dsl api
    logRotator {
        numToKeep(60)
    }
}

new BaseJobBuilder(
        name: "Hello-Freestyle-Job",
        description: "Example Freestyle job"
).build(this).with {
    steps {
        shell("echo ${env.getEnv()}")
    }
}
