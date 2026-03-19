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

        stage('Run Tests - Parallel Browsers') {
            parallel {

                stage('Chrome Tests') {
                    steps {
                        bat 'if not exist target\\allure-results-chrome mkdir target\\allure-results-chrome'
                        // Write environment BEFORE tests so it's always present
                        writeFile file: 'target/allure-results-chrome/environment.properties',
                                  text: 'Browser=Chrome\nEnv=QA\nOS=Windows\n'
                        bat 'mvn test -Dbrowser=CHROME -Dallure.results.directory=target/allure-results-chrome'
                        // Verify Chrome results were generated
                        bat 'echo === Chrome result count: && dir /b target\\allure-results-chrome | find /c ".json"'
                    }
                }

                stage('Firefox Tests') {
                    steps {
                        bat 'if not exist target\\allure-results-firefox mkdir target\\allure-results-firefox'
                        // Write environment BEFORE tests so it's always present
                        writeFile file: 'target/allure-results-firefox/environment.properties',
                                  text: 'Browser=Firefox\nEnv=QA\nOS=Windows\n'
                        bat 'mvn test -Dbrowser=FIREFOX -Dallure.results.directory=target/allure-results-firefox'
                        // Verify Firefox results were generated
                        bat 'echo === Firefox result count: && dir /b target\\allure-results-firefox | find /c ".json"'
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Merging Allure Results...'

            // Show what exists in each folder before merging
            bat 'echo === Chrome Results === && dir target\\allure-results-chrome'
            bat 'echo === Firefox Results === && dir target\\allure-results-firefox'

            // Merge both results into single folder
            bat '''
                if not exist target\\allure-results-merged mkdir target\\allure-results-merged
                xcopy /Y /S /I target\\allure-results-chrome\\* target\\allure-results-merged\\
                xcopy /Y /S /I target\\allure-results-firefox\\* target\\allure-results-merged\\
            '''

            echo 'Generating Merged Allure Report...'
            allure includeProperties: true,
                   jdk: '',
                   results: [
                       [path: 'target/allure-results-merged']
                   ]
        }

        success {
            echo 'All tests passed!'
        }

        failure {
            echo 'Some tests failed. Check Allure report for details.'
        }
    }
}