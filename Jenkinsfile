pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK 17'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Run Tests') {
            steps {
             bat 'java -version'
                bat 'mvn -v && mvn clean test -Dtest=org.example.RunnerTest'
            }
        }
    }
}