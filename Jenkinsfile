pipeline {
    agent any
    parameters {

        string(name: 'DOCKER_IMAGE_NAME', defaultValue:'java-calculator', description:'Name of the Image')

        string(name: 'JAR_NAME', defaultValue:'calculadora', description:'Name of the .jar file')

        //string(name: 'CONTAINER_NAME', defaultValue: 'java-mvn', description:'Docker Container Name')

        //string(name: 'DOCKER_PORT', defaultValue: '3000', description:'Docker Container Host Port')
    }
        
    stages{
        
        stage('SonarQube analysis') {
            steps{
                withMaven(maven: 'mvn') {
                    sh "mvn clean package"
                }
                withSonarQubeEnv(credentialsId: 'token', installationName: 'sonarqube') { // You can override the credential to be used
                sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
                //sh 'mvn sonar:sonar \
                   // -Dsonar.projectKey=Cloud7:TarefaCalculadora \
                   // -Dsonar.host.url=http://localhost:9000 \
                    //-Dsonar.login=632ed5de555469417baeafc58aebf35f8a3d4f13'
                //
                }
            }
        }
          

        stage("Build Jar"){
            steps{
                sh 'javac *.java'
                sh 'jar cfe "$JAR_NAME".jar Calculator *.class'

            }
        }

        stage("store artifact on Nexus") {
            steps{
                withCredentials([usernameColonPassword(credentialsId: 'admin', variable: 'USERPASS')]) {
                sh 'curl -v -u "$USERPASS" --upload-file /var/jenkins_home/workspace/Calculadora-Pipeline/"$JAR_NAME".jar http://localhost:8081/repository/my-raw/'
                }
            }
        }

        stage('Create Docker Image') {
            steps {
                sh 'docker build -t "$IMAGE_NAME":v1.0 .'
            }
        }

        stage('Push Image to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'admin', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                sh 'docker login -u "$USERNAME" -p "$PASSWORD" localhost:8082'
                sh "docker tag ${DOCKER_IMAGE_NAME}:v1.0 localhost:8082/${DOCKER_IMAGE_NAME}:v1.0"
                sh "docker push localhost:8082/${DOCKER_IMAGE_NAME}:v1.0"
                }
            }
        }
        stage('Clear WorkSpace') {
            steps {
                cleanWs()
            }
        }
    } 
}
