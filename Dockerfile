FROM openliberty/open-liberty:full-java11-openj9-ubi

# Add Liberty server configuration including all necessary features
COPY --chown=1001:0  src/main/liberty/config/server.xml /config/

# Modify feature repository (optional)
# A sample is in the 'Getting Required Features' section below

# Add app
COPY --chown=1001:0  target/api.war /config/dropins/api.war

COPY --chown=1001:0 target/liberty/wlp/usr/shared/resources/jdbc/postgresql-42.2.19.jar /usr/shared/resources/jdbc/postgresql-42.2.19.jar

# This script will add the requested server configurations, apply any interim fixes and populate caches to optimize runtime
RUN configure.sh
