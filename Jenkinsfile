/*

pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
    }

    tools {
        maven 'Maven'
        jdk 'JDK21'
    }

    stages {

        stage('Checkout') {
            steps {
                deleteDir()
                checkout scm
            }
        }

        stage('Clean Workspace') {
            steps {
                bat 'mvn clean'
            }
        }

        stage('Run Tests - Chrome and Firefox') {
            steps {
                bat 'if not exist target\\allure-results mkdir target\\allure-results'
                writeFile file: 'target/allure-results/environment.properties',
                          text: 'Browser=Chrome+Firefox\nEnv=QA\nOS=Windows\n'
                bat 'mvn test'
            }
        }
    }

    post {
        always {
            echo 'Generating Allure Report...'
            allure includeProperties: true,
                   jdk: '',
                   results: [[path: 'target/allure-results']]
        }
        success { echo 'All tests passed!' }
        failure { echo 'Some tests failed. Check Allure report for details.' }
    }
}



 */

 pipeline {
     agent any

     options {
         skipDefaultCheckout(true)
     }

     tools {
         maven 'Maven'
         jdk 'JDK21'
     }

     stages {

         stage('Checkout') {
             steps {
                 deleteDir()
                 checkout scm
             }
         }

         stage('Start Selenium Grid') {
             steps {
                 echo 'Starting Selenium Grid...'
                 bat 'docker compose up -d'
             }
         }

         stage('Wait for Grid') {
             steps {
                 echo 'Waiting for Grid to be ready...'
                 bat 'timeout /t 15'
             }
         }

         stage('Clean Workspace') {
             steps {
                 bat 'mvn clean'
             }
         }

         stage('Run Tests (Grid + Parallel)') {
             steps {
                 bat 'if not exist target\\allure-results mkdir target\\allure-results'
                 writeFile file: 'target/allure-results/environment.properties',
                           text: 'Execution=Grid\nBrowsers=Chrome+Firefox\nEnv=QA\n'
                 bat 'mvn test -Dgrid=true'
             }
         }
     }

     post {
         always {
             echo 'Generating Allure Report...'
             allure includeProperties: true,
                    jdk: '',
                    results: [[path: 'target/allure-results']]

             echo 'Stopping Selenium Grid...'
             bat 'docker compose down'
         }
         success {
             echo 'All tests passed!'
         }
         failure {
             echo 'Some tests failed. Check Allure report for details.'
         }
     }
 }
