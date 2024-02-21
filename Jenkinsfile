pipeline {
    agent any

    tools {
        maven 'Maven' // The name of the Maven installation to use.
        jdk 'Java' // The name of the JDK installation to use.
    }

    stages {
        stage('Build') {
            steps {
                // 'mvn' is the Maven command. If Maven is not on the PATH, use the full path to the Maven binary.
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml' // Publish JUnit test result report.
                }
            }
        }

        // Add more stages for deployment, etc., as needed.
    }

    post {
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
