import jenkins.automation.utils.FlowJobBuilder

def job= new FlowJobBuilder(
        name: 'GeneratedFlowJob',
        description: 'this our first stab at it',
        jobs:['job1', 'job2']
).build(this);

job.with{
    logRotator{
        numToKeep(365)
    }

}

