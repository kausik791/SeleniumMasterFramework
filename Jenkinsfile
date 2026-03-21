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
         timeout(time: 20, unit: 'MINUTES')
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
                 bat 'docker compose up -d --scale chrome=2 --scale firefox=2'
             }
         }

         stage('Wait for Grid') {
             steps {
                 echo 'Waiting for Selenium Grid...'
                 script {
                     int retries = 10
                     for (int i = 0; i < retries; i++) {
                         def status = bat(
                             script: 'curl -s http://localhost:4444/status',
                             returnStdout: true
                         ).trim()

                         if (status.contains('"ready":true')) {
                             echo 'Grid is ready!'
                             break
                         }

                         echo 'Grid not ready yet... retrying'
                         sleep 5
                     }
                 }
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
             bat 'docker compose down || exit 0'
         }

         success {
             echo 'All tests passed!'
         }

         failure {
             echo 'Some tests failed. Check Allure report for details.'
         }
     }
 }