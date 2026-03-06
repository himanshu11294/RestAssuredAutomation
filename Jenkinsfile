pipeline {
    agent any

    triggers {
        githubPush()
    }

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn -B clean test'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml, target/surefire-reports/junitreports/*.xml'
            archiveArtifacts allowEmptyArchive: true, artifacts: 'test-output/extent-report/*.html, test-output/logs/*.log, target/surefire-reports/**'
        }
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed. Check test and report artifacts.'
        }
    }
}
