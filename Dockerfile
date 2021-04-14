FROM openliberty/open-liberty:full-java11-openj9-ubi

# Add Liberty server configuration including all necessary features
COPY --chown=1001:0 src/main/liberty/config/server.xml /config/

# Add JDBC driver
COPY --chown=1001:0 target/liberty/wlp/usr/shared/resources/jdbc/postgresql-42.2.19.jar /usr/shared/resources/jdbc/postgresql-42.2.19.jar

# Add app
COPY --chown=1001:0 target/api.war /config/apps
# Used to enable debug mode
# COPY --chown=1001:0 jvm.options /config/jvm.options

# Apparently only needed for optimized production builds
# RUN configure.sh
