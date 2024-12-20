pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                bat 'echo "Building project"'
                bat 'build_script.bat'
            }
        }
        stage('Test') {
            steps {
                bat 'echo "Running tests"'
                bat 'run_tests.bat'
            }
        }
    }
}