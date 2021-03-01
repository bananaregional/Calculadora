pipeline
{
    agent any

    parameters
    {
        string(name: 'DOCKER_IMAGE_NAME', defaultValue: 'imagem_calculadora', description: 'Docker image name')
        string(name: 'JAR_NAME', defaultValue:'calculadora', description:'Name of the .jar file')
    }

    stages
    {
        
        stage ('Build .jar')
        {
            steps  
            {
                sh 'javac *.java'
                sh 'jar cfe teste.jar calculadora *.class'
            }
        }
        stage('Docker build')
        {
            steps
            {
                sh "docker build -t ${DOCKER_IMAGE_NAME}:v1.0 ."

                //sh "docker login -u $CREDENCIAIS_USR -p $CREDENCIAIS_PSW localhost:8082"
                
            }
        }
        
        stage('Push to Nexus')
        {
            steps
            withCredentials([usernamePassword(credentialsId: 'docker-login-nexus', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]){
               // sh "docker rm -f ${DOCKER_CONTAINER_NAME}"
               // sh "docker run -d --privileged -p ${DOCKER_PORT}:8080 --name ${DOCKER_CONTAINER_NAME} ${DOCKER_IMAGE_NAME}"
                sh 'docker login -u "$USERNAME" -p "$PASSWORD" localhost:8082'
                sh "docker tag ${DOCKER_IMAGE_NAME}:v1.0 localhost:8082/${DOCKER_IMAGE_NAME}:v1.0"
                sh "docker push localhost:8082/${DOCKER_IMAGE_NAME}:v1.0"
                
            }
        }
         stage('Upload to Nexus Raw Repo')
        {
            steps
                withCredentials([usernameColonPassword(credentialsId: 'curl-jenkinsfile-uploadArt-nexus', variable: 'USERPASS')]) {
                sh 'curl -v -u "$USERPASS" --upload-file /var/jenkins_home/workspace/Calculadora-Pipeline/"$JAR_NAME".jar http://nexus:8081/repository/raw/'
                // sh "docker login -u $CREDENCIAIS_USR -p $CREDENCIAIS_PSW localhost:8081"
                // sh "curl -v --user '$CREDENCIAIS_USR:$CREDENCIAIS_PSW' --upload-file ./*.jar http://localhost:8081/repository/raw/artefacto.jar"
            }
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
