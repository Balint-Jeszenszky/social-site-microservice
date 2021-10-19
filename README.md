# Social site with microservice architecture

## Installation guide

- set email credentials for auth service in application.properties or create a separate application.yaml file
    - auth.app.email
    - auth.app.password
    - auth.app.server
    - auth.app.port
- create these folders for the databases
    - __auth-db__ for auth service
    - __social-db__ for social service
- create a network for docker services: `docker network create web`
- run docker-compose `docker-compose up -d`