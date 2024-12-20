pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn failsafe:integration-test failsafe:verify'
            }
        }
    }

    post {
        always {
            junit '**/target/failsafe-reports/*.xml'
        }
        failure {
            echo 'Build failed. Check logs for details.'
        }
    }
}