pipeline {
    agent { label 'WinNode1' }
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
                        cucumber buildStatus: 'null', 
                        customCssFiles: '', 
                        customJsFiles: '', 
                        failedFeaturesNumber: -1, 
                        failedScenariosNumber: -1, 
                        failedStepsNumber: -1, 
                        fileIncludePattern: '**/*.json', 
                        pendingStepsNumber: -1, 
                        skippedStepsNumber: -1, 
                        sortingMethod: 'ALPHABETICAL', 
                        undefinedStepsNumber: -1
                }
            }
        }
    }
}
