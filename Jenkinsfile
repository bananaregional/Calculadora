pipeline
{
    agent any

    parameters
    {
        string(name: 'DOCKER_IMAGE_NAME', defaultValue: 'default_name', description: 'Docker image name')
        string(name: 'DOCKER_CONTAINER_NAME', defaultValue: 'default_name', description: 'Docker container name')
        string(name: 'DOCKER_PORT', defaultValue: '3000', description: 'Docker port')
    }

    environment {
        LOGIN_ID    = credentials('admin')
        LOGIN_PW = credentials('admin')
    }

    stages
    {
        
        stage ('Build .jar')
            steps  
            {
                sh 'javac --release 8 *.java'
                sh 'jar cfe teste.jar calculadora *.class'
            }
  
        stage('Docker build')
        {
            steps
            {
                sh "docker build -t ${DOCKER_IMAGE_NAME}:v1.0 ."

                sh "docker login -u admin -p admin localhost:8082"
                sh "docker tag ${DOCKER_IMAGE_NAME}:v1.0 localhost:8082/${DOCKER_IMAGE_NAME}:v1.0"
            }
        }
        
        stage('Docker run')
        {
            steps
            {
               // sh "docker rm -f ${DOCKER_CONTAINER_NAME}"
               // sh "docker run -d --privileged -p ${DOCKER_PORT}:8080 --name ${DOCKER_CONTAINER_NAME} ${DOCKER_IMAGE_NAME}"

                sh "docker push localhost:8082/${DOCKER_IMAGE_NAME}:v1.0"
                
            }
        }
         stage('Upload to Nexus Raw Repo')
        {
            steps
            {
                sh "curl -v --user 'admin:admin' --upload-file ./*.jar http://localhost:8081/repository/raw/artefacto.jar"
            }
 
        stage('Clean up')
        {
            steps
            {
                cleanWs()
            }
        }
    }
}