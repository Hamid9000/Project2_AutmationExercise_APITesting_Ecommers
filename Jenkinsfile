pipeline {

    agent any

    parameters {
        choice(
            name: 'ENV',
            choices: ['qa', 'stage', 'prod'],
            description: 'Select environment'
        )

        choice(
            name: 'SUITE',
            choices: ['smoke', 'regression', 'functional'],
            description: 'Select TestNG suite'
        )
    }

    tools {
        jdk 'JDK21'
        maven 'Maven'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'master',
                    url: 'YOUR_GITHUB_REPOSITORY_URL'
            }
        }

        stage('Clean') {
            steps {
                bat 'mvn clean'
            }
        }

        stage('Run Tests') {
            steps {
                bat "mvn test -Denv=${params.ENV} -Dsuite=${params.SUITE}"
            }
        }

        stage('Publish Test Results') {
            steps {
                junit(
                    testResults: '**/target/surefire-reports/*.xml',
                    allowEmptyResults: true
                )
            }
        }
    }

    post {

        always {
            echo 'Test execution completed.'
        }

        success {
            echo 'Automation tests passed.'
        }

        failure {
            echo 'Automation tests failed.'
        }
    }
}