pipeline {
    agent any
     options {
            skipDefaultCheckout(true)   //  IMPORTANT
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

        stage('Run Tests - Parallel Browsers') {
            parallel {

                stage('Chrome Tests') {
                    steps {
                        bat 'mvn clean test -Dbrowser=CHROME -Dallure.results.directory=target/allure-results-chrome'
                    }
                }

                stage('Firefox Tests') {
                    steps {
                        bat 'mvn clean test -Dbrowser=FIREFOX -Dallure.results.directory=target/allure-results-firefox'
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Generating Allure Report...'

            allure includeProperties: false,
                   jdk: '',
                   results: [
                       [path: 'target/allure-results-chrome'],
                       [path: 'target/allure-results-firefox']
                   ]
        }
    }
}