# Spring Boot Auth Server, use Github as the authentication source.

There are two apps in this project.

**resource-server-github:**
- It has tollData service which will be consumed by UI microservice.
- Services can only be consumed by passing token given by Github authorization server.
- Token will be authenticated by github using the url mentioned in application.properties.
