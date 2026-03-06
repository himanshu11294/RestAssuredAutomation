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
                                sh '''
                                        if [ -x /opt/homebrew/bin/mvn ]; then
                                            MVN_CMD=/opt/homebrew/bin/mvn
                                        elif [ -x /usr/local/bin/mvn ]; then
                                            MVN_CMD=/usr/local/bin/mvn
                                        elif command -v mvn >/dev/null 2>&1; then
                                            MVN_CMD=$(command -v mvn)
                                        else
                                            echo "Maven executable not found."
                                            exit 127
                                        fi

                                        "$MVN_CMD" -B clean test
                                '''
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
