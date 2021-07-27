node {
    def mvnHome
    def commit_id
    def to = emailextrecipients([
              [$class: 'CulpritsRecipientProvider'],
              [$class: 'DevelopersRecipientProvider'],
              [$class: 'RequesterRecipientProvider']
      ])

    try{
       stage('Preparation') {
               checkout scm
               mvnHome = tool 'maven'
               sh "git rev-parse --short HEAD > .git/commit-id"
               commit_id = readFile('.git/commit-id').trim()
       }

       stage('Unit Test') {
               sh "'${mvnHome}/bin/mvn' clean test"
       }

       stage('Integration Test') {
              sh "'${mvnHome}/bin/mvn' verify"
       }

       stage('Docker build/push') {
               def app = docker.build "zombief0/recipe-api:${commit_id}"
               app.push()
       }

       stage('Run docker container') {
               sh "docker image prune -a -f"
               sh "docker container run -d -e VIRTUAL_HOST=apirecipe.normanmbouende.com --name recipe-api zombief0/recipe-api:${commit_id}"
       }

    } catch(e) {
        currentBuild.result = "FAILURE";
        def subject = "${env.JOB_NAME} - Build #${env.BUILD_NUMBER} ${currentBuild.result}"
        def content = '${JELLY_SCRIPT,template="html"}'

        if(to != null && !to.isEmpty()) {
          emailext(body: content, mimeType: 'text/html',
             replyTo: '$DEFAULT_REPLYTO', subject: subject,
             to: to, attachLog: true )
        }

        throw e;
      }

}
