@technology(Docker)
container EurekaServerContainer {
    deployment technology Docker::_deployment.Docker
    deploys EurekaServerContext::EurekaServer {
        aspects {
            Docker::_aspects.Dockerfile('
                CMD java -jar eureka-server.jar
                COPY eureka-server.jar .
            ')
        }
        default values {
            basic endpoints {
                protocol = HTTP
                port = 8761
            }
        }
    }
}
