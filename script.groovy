pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', credentialsId: 'github-login' ,url: 'https://github.com/nathanvo1489/Proj_ToDoList.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t js_react_fe_image ./frontend'
            }
        }

        // stage('Deploy Container') {
        //     steps {
        //         sh'''
        //             docker rm js_nextjs -f || true
        //             docker run --name js_nextjs -d -p 3001:3000 js_nextjs_image
        //         '''
        //     }
        // }
        
        stage('Deploy Container') {
            steps {
                // chuyển image qua bên server run docker save | load
                sh'''
                '''
            }
        }
    }
}