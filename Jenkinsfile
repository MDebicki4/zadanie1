pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK 17'
    }
    environment {
        MAVEN_OPTS = '-Dfile.encoding=UTF-8 -Dwebdriver.chrome.args=--headless'
        LANG = 'en_US.UTF-8'
    }

    stages {
        stage('Build') {
            steps {
                script {
                    bat 'mvn clean install'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    bat 'mvn clean test'
                }
            }
        }
    }
}