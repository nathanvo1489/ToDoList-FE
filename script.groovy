pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', credentialsId: 'jenkins-github-login' ,url: 'https://github.com/nathanvo1489/ToDoList-FE.git'
            }
        }

        // must build with prefix username to push to Dockerhub
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t nathanvo1489/js_react_fe_image:latest .'
            }
        }

        stage('Dockerhub Login') {
            steps {
                withCredentials([
                    usernamePassword(credentialsId:'jenkins-dockerhub-login', usernameVariable:'DOCKER_USER', passwordVariable:'DOCKER_PASS')
                ]) {
                    sh '''
                        docker login -u ${DOCKER_USER} -p ${DOCKER_PASS}
                    '''
                }
            }
        }

        stage('Push Img to Dockerhub') {
            steps {
                sh '''
                    docker push nathanvo1489/js_react_fe_image:latest
                '''
            }
        }

        // stage('Deploy Container') {
        //     steps {
        //         sh'''
        //             docker rm js_react_fe -f || true
        //             docker run --name js_react_fe -d -p 3001:80 js_react_fe_image
        //         '''
        //     }
        // }
        
        // stage('Deploy Container') {
        //     steps {
        //         // chuyển image qua bên server run docker save | load
        //         sh'''
        //         '''
        //     }
        // }
    }
}