@technology(Docker)
container BookingServiceContainer {
    deployment technology Docker::_deployment.Docker
    deploys BookingContext::BookingService {
        aspects {
            Docker::_aspects.Dockerfile('
                CMD java -jar booking-service.jar
                COPY booking-service.jar .
            ')
        }
        default values {
            basic endpoints {
                protocol = HTTP
                port = 8083
            }
        }
    }
}
