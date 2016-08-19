# jenkins-as-code-starter-project
A neat little project that uses our jenkins utils and helps you to get started and start testing your scripts


## Usage

1. Clone this repo
1. Rename it to something meaningful for your project
1. Change the git remote URL to wherever your jobs will live
1. In `settings.gradle`, change the `rootProject.name` to match your new project name
1. Run `./gradlew build` from the project root directory
1. Install and run a local Jenkins 
1. Profit! Start writing your jobs in jobs dir. 

## Creating your jobs in Jenkins

### Creating the seed job

Ultimately, you'll need a "seed job" to run your job files in Jenkins, essentially following [these instructions](https://github.com/jenkinsci/job-dsl-plugin#basic-usage)

Assuming you're using our [jenkins-automation](https://github.com/cfpb/jenkins-automation/) niceties, 
your seed job will be configured with "multi-scm" and 2 git repos: the `jenkins-automation` repo, 
and the repo that you're creating by cloning this `jenkins-as-code-starter-project`

### Local development helper

For local development, as you iterate on your job files, it's usually nice to tweak your file locally, 
create those jobs in a local jenkins, and then push to GitHub when they're working as expected.

To do so, use the gradle `rest` task, which we've included in `jenkins-automation` and lifted nearly verbatim from https://github.com/sheehan/job-dsl-gradle-example (thanks!)


`./gradlew rest -Dpattern=<pattern> -DbaseUrl=<baseUrl> [-Dusername=<username>] [-Dpassword=<password>]`

* `pattern` - [ant-style path pattern](https://ant.apache.org/manual/dirtasks.html) of files to include
* `baseUrl` - base URL of Jenkins server
* `username` - Jenkins username, if secured
* `password` - Jenkins password or token, if secured
* `JAC_ENVIRONMENT` - An environment variable we set in all of our Jenkinses. Defaults to "dev". Used by our `EnvironmentUtils`. See example usage in `jobs/jobs.groovy` in this repo
* `JAC_HOST` - An environment variable we set in all of our Jenkinses. Defaults to "aws".

This uses a task named `rest` to execute `jenkins.automation.rest.RestApiScriptRunner`, 
which lives in the `jenkins-automation` repo and is available here since `jenkins-automation` is a dependency.

Finally, install and run Jenkins locally: 
* download the `.war` file from [https://jenkins.io/](https://jenkins.io/) 
* run with `nohup java -jar path-to-file/jenkins.war --httpPort=8081`

#### Examples

Run all jobs files in the `jobs` directory against a locally running Jenkins

`./gradlew rest -Dpattern=jobs/** -DbaseUrl=http://localhost:8081`

Run all jobs defined in`jobs/jobs.groovy` against a locally running Jenkins.

`./gradlew rest -Dpattern=jobs/jobs.groovy -DbaseUrl=http://localhost:8081`

Same as above, but override the default JAC_ENVIRONMENT variable if those jobs behave different based on that variable
`./gradlew rest -Dpattern=jobs/jobs.groovy -DbaseUrl=http://localhost:8081 -DJAC_ENVIRONMENT=prod`


Run everything in the `jobs` directory against a remote Jenkins

`./gradlew rest -Dpattern=jobs/** -DbaseUrl=http://dev.jenkins -Dusername=foo -Dpassword=bar`
