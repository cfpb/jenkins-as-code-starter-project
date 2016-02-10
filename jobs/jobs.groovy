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
new FlowJobBuilder(
        name: 'GeneratedFlowJob',
        description: 'this our first stab at it',
        jobs: ['job1', 'job2']
).build(this).with {
    // example of extending a job with the job-dsl api
    logRotator {
        numToKeep(365)
    }
}

new BaseJobBuilder(
        name: "job1",
        description: "One job"
).build(this).with {
    steps {
        shell("echo ${env.getEnv()}")
    }
}
new BaseJobBuilder(
        name: "job2",
        description: "Two job"
).build(this).with {
    steps {
        shell("echo ${env.getEnv()}")
    }
}