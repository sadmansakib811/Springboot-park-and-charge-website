@technology(Docker)
container ConfigServiceContainer {
    deployment technology Docker::_deployment.Docker
    deploys ConfigServiceContext::ConfigService {
        aspects {
            Docker::_aspects.Dockerfile('
                CMD java -jar config-service.jar
                COPY config-service.jar .
            ')
        }
        default values {
            basic endpoints {
                protocol = HTTP
                port = 8888
            }
        }
    }
}
