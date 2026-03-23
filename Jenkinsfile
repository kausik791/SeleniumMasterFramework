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
         timeout(time: 30, unit: 'MINUTES')
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
                 bat 'docker compose down --timeout 5 || exit 0'
                 bat '''
                     for /f "tokens=*" %%i in ('docker ps -aq --filter "name=javaseleniumpipeline"') do (
                         docker kill --signal=9 %%i 2>nul
                         docker rm -f %%i 2>nul
                     )
                     exit /b 0
                 '''
                 bat 'docker rm -f selenium-hub || exit 0'
                 bat 'docker network rm javaseleniumpipeline_selenium-grid || exit 0'
                 bat 'docker compose up -d --scale chrome=2 --scale firefox=2'
             }
         }

         stage('Wait for Grid') {
             steps {
                 echo 'Waiting for Selenium Grid to be ready...'
                 script {
                     int maxRetries = 36
                     int retry = 0
                     boolean ready = false

                     while (!ready && retry < maxRetries) {
                         int status = bat(
                             returnStatus: true,
                             script: '''
 docker compose logs selenium-hub --tail=200 | findstr /C:"Started Selenium Hub" >nul
 if errorlevel 1 exit /b 1
 docker compose logs selenium-hub --tail=200 | findstr /C:"from DOWN to UP" >nul
 if errorlevel 1 exit /b 1
 exit /b 0
 '''
                         )

                         if (status == 0) {
                             echo "Selenium Grid is READY on retry ${retry + 1}"
                             ready = true
                         } else {
                             echo "Grid not ready (status=${status}) retry ${retry + 1}/${maxRetries}"
                             sleep time: 10, unit: 'SECONDS'
                             retry++
                         }
                     }

                     if (!ready) {
                         echo 'Grid failed to become ready. Dumping diagnostics...'
                         bat 'docker compose ps'
                         bat 'docker compose logs selenium-hub --tail=200'
                         error('Selenium Grid did not become ready in time')
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
                 bat 'docker run --rm --network javaseleniumpipeline_selenium-grid -v "%cd%":/workspace -w /workspace -v "%USERPROFILE%\\.m2":/root/.m2 maven:3.9.9-eclipse-temurin-21 mvn -B clean test -Dgrid=true -Denv=STAGE -DgridUrl=http://selenium-hub:4444'
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
             bat 'docker compose down --timeout 5 || exit 0'
             bat '''
                 for /f "tokens=*" %%i in ('docker ps -aq --filter "name=javaseleniumpipeline"') do (
                     docker kill --signal=9 %%i 2>nul
                     docker rm -f %%i 2>nul
                 )
                 exit /b 0
             '''
             bat 'docker rm -f selenium-hub || exit 0'
             bat 'docker network rm javaseleniumpipeline_selenium-grid || exit 0'
         }

         success {
             echo 'All tests passed!'
         }

         failure {
             echo 'Some tests failed. Check Allure report for details.'
         }
     }
 }