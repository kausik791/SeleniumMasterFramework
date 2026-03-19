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
} */

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
            echo 'Merging Allure Results...'
            bat '''
                if not exist target\\allure-results-merged mkdir target\\allure-results-merged
                xcopy /Y /S /I target\\allure-results-chrome\\* target\\allure-results-merged\\
                xcopy /Y /S /I target\\allure-results-firefox\\* target\\allure-results-merged\\
            '''
            echo 'Generating Merged Allure Report...'
            allure includeProperties: true,
                   jdk: '',
                   results: [[path: 'target/allure-results-merged']]
        }
        success { echo 'All tests passed!' }
        failure { echo 'Some tests failed. Check Allure report for details.' }
    }
}

