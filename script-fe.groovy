pipeline {
    agent any

    stages {
        stage('Checkout Frontend Code') {
            steps {
                git branch: 'main',
                credentialsId: 'jenkins-github-login',
                url: 'https://github.com/nathanvo1489/ToDoList-FE.git'
            }
        }
        //// CI ////
        // must build with prefix username to push to Dockerhub
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t nathanvo1489/js_react_fe_image:latest .'
            }
        }

        stage('Dockerhub Login') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId:'jenkins-dockerhub-login',
                        usernameVariable:'DOCKER_USER',
                        passwordVariable:'DOCKER_PASS')
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

        //// CD in server run ////
        // Rmb to check docker-compose.yml & .env file at the newest version //
        stage('Deploy Container') {
            steps {
                withCredentials([
                    sshUserPrivateKey(
                        credentialsId:'ssh-login',
                        keyFileVariable:'SSH_KEY',
                        usernameVariable:'SSH_USER'),
                    string(
                        credentialsId:'ip-server-run-fe',
                        variable:'IP_SERVER_RUN_FE')
                ]) {
                    // must manually docker login in server run
                    // after ls -la -> docker login -u nathanvo1489 -p (copy pass in 'jenkins-dockerhub-login')
                    sh '''
                        ssh -o StrictHostKeyChecking=no -i ${SSH_KEY} ${SSH_USER}@${IP_SERVER_RUN_FE}
                        ls -la

                        cd /home/ubuntu/ToDoList-FE/docker-compose/
                        docker compose pull js_react_fe
                        docker compose up -d --force-recreate js_react_fe
                        docker image prune -f
                    '''
                }
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
