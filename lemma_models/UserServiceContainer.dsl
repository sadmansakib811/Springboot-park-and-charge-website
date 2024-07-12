@technology(Docker)
container UserServiceContainer {
    deployment technology Docker::_deployment.Docker
    deploys UserContext::UserService {
        aspects {
            Docker::_aspects.Dockerfile('
                CMD java -jar user-service.jar
                COPY user-service.jar .
            ')
        }
        default values {
            basic endpoints {
                protocol = HTTP
                port = 8081
            }
        }
    }
}
