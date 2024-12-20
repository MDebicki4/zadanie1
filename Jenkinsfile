pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Run Tests') {
            steps {
                bat 'mvn clean test -Dtest=org.example.RunnerTest'
            }
        }
    }
}