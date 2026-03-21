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
                 echo 'Waiting for Selenium Grid to be ready...'

                 script {
                     def isReady = false
                     int retry = 0

                     while (!isReady && retry < 20) {
                         try {
                             def response = bat(
                                 script: 'curl -s http://localhost:4444/status',
                                 returnStdout: true
                             ).trim()

                             echo "Grid response: ${response}"

                             if (response.toLowerCase().contains("ready")) {
                                 isReady = true
                                 echo 'Selenium Grid is READY ✅'
                             } else {
                                 echo "Grid not ready yet... retry ${retry}"
                                 sleep time: 5, unit: 'SECONDS'
                                 retry++
                             }

                         } catch (Exception e) {
                             echo "Grid not reachable yet... retry ${retry}"
                             sleep time: 5, unit: 'SECONDS'
                             retry++
                         }
                     }

                     if (!isReady) {
                         error("❌ Selenium Grid did not become ready in time")
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