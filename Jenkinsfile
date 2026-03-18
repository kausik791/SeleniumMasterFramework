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
                        // Run tests
                        bat 'mvn test -Dbrowser=CHROME -Dallure.results.directory=target/allure-results-chrome'

                        // Add environment info for Allure
                        writeFile file: 'target/allure-results-chrome/environment.properties',
                                  text: 'Browser=Chrome\nEnv=QA\n'

                    }
                }

                stage('Firefox Tests') {
                    steps {
                        // Run tests
                        bat 'mvn test -Dbrowser=FIREFOX -Dallure.results.directory=target/allure-results-firefox'

                        // Add environment info for Allure
                        writeFile file: 'target/allure-results-firefox/environment.properties',
                                  text: 'Browser=Firefox\nEnv=QA\n'
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Generating Merged Allure Report...'

            allure includeProperties: false,
                   jdk: '',
                   results: [
                       [path: 'target/allure-results-chrome'],
                       [path: 'target/allure-results-firefox']
                   ]
        }
    }
}