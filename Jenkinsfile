pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK 17'
    }
    environment {
        MAVEN_OPTS = '-Dfile.encoding=UTF-8 -Dselenide.browserSize=1920x1080 -Dselenide.headless=true'
        JAVA_TOOL_OPTIONS = '-Dfile.encoding=UTF-8'
        LANG = 'en_US.UTF-8'
    }

    stages {
        stage('Build') {
            steps {
                script {
                    bat """
                    chcp 65001 > nul
                    set MAVEN_OPTS=-Dfile.encoding=UTF-8
                    mvn clean install
                    """
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    bat """
                    chcp 65001 > nul
                    set MAVEN_OPTS=-Dfile.encoding=UTF-8
                    mvn clean test
                    """
                }
            }
        }
    }
}