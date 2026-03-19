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
                        writeFile file: 'target/allure-results-chrome/environment.properties',
                                  text: 'Browser=Chrome\nEnv=QA\nOS=Windows\n'
                        bat 'mvn test -Dbrowser=CHROME -Dallure.results.directory=target/allure-results-chrome'
                    }
                }

                stage('Firefox Tests') {
                    steps {
                        bat 'if not exist target\\allure-results-firefox mkdir target\\allure-results-firefox'
                        writeFile file: 'target/allure-results-firefox/environment.properties',
                                  text: 'Browser=Firefox\nEnv=QA\nOS=Windows\n'
                        bat 'mvn test -Dbrowser=FIREFOX -Dallure.results.directory=target/allure-results-firefox'
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Generating Merged Allure Report...'
            bat 'if not exist target\\allure-results-merged mkdir target\\allure-results-merged'
            bat 'xcopy /Y /S target\\allure-results-chrome\\* target\\allure-results-merged\\'
            bat 'xcopy /Y /S target\\allure-results-firefox\\* target\\allure-results-merged\\'

            allure includeProperties: true,
                   jdk: '',
                   results: [
                       [path: 'target/allure-results-merged']
                   ]
        }
    }
}