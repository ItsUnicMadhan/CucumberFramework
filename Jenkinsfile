pipeline {
    agent { label 'WinNode' }
    stages {
    	stage('Delete workspace'){

          steps{

             deleteDir()

          }

      	}
        stage('Git_Clone'){
            steps {
              git 'https://github.com/ItsUnicMadhan/CucumberFramework.git'  
            }
        }
        stage ('Maven_Build') {
            steps {
                bat 'mvn clean install'
            }
        }
        stage ('Test') {
            steps {
                bat 'mvn -D clean test'
            }
            post {                 
                always {
                        junit testResults: '**target/junit-results.xml'
                    
           }
        }
    }
}
}
