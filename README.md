# JavaEE List Backend

## Purpose
This is a project for getting to know the technology stack at work. 
It uses JavaEE and a Postgres DB to build a backend for a non-existing frontend application.

## Run the project locally
1. Have Docker and Maven installed locally
1. `git clone git@github.com:Lenoxy/javaee-list-backend.git`
1. `cd javaee-list-backend`
1. `mvn package`
1. To enable the optional debug mode, uncomment line 13 (`COPY --chown=1001:0 jvm.options /config/jvm.options`) in the [`/Dockerfile`](./Dockerfile)
1. `docker compose up -d`
1. If the debug mode has been enabled before, the debugger can now be attached on port `7777`.
1. Use the [`/api.http`](./api.http) file to test the backend or any other tool on the backend running on [localhost:8080](http://localhost:8080/)

- Optionally you can find an IntelliJ run config at [`/.run/RUN.run.xml`](./.run/RUN.run.xml). It invokes the Maven build and starts the containers.
