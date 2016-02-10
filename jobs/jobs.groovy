import jenkins.automation.builders.*
import jenkins.automation.utils.EnvironmentUtils

//the "JAC_ENVIRONMENT" global variable is set in all of our Jenkinses
//if you're testing this in a local Jenkins, you must configure this global variable.
//Set it to "dev", "stage", or "prod" depending on what you want to test
//If you're not sure, set it to "dev"

def env = EnvironmentUtils.getInstance("${JAC_ENVIRONMENT}")
println "Environment is " + env.getEnv()

if (env.isDev()) {
    //do environment-specific things here
}

new FlowJobBuilder(
        name: 'GeneratedFlowJob',
        description: 'this our first stab at it',
        jobs: ['job1', 'job2']
).build(this).with {
    logRotator {
        numToKeep(365)
    }
}

new BaseJobBuilder(
        name: "job1",
        description: "A job"
).build(this).with {
    steps {
        shell("echo $env")
    }
}