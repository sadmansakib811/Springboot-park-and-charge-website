@technology(Docker)
container StationServiceContainer {
    deployment technology Docker::_deployment.Docker
    deploys StationContext::StationService {
        aspects {
            Docker::_aspects.Dockerfile('
                CMD java -jar station-service.jar
                COPY station-service.jar .
            ')
        }
        default values {
            basic endpoints {
                protocol = HTTP
                port = 8082
            }
        }
    }
}
