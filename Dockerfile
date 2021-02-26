FROM openliberty/open-liberty:full-java11-openj9-ubi

# Add Liberty server configuration including all necessary features
COPY --chown=1001:0  src/main/liberty/config/server.xml /config/

# Modify feature repository (optional)
# A sample is in the 'Getting Required Features' section below

# Add app
COPY --chown=1001:0  out/artifacts/package/package.war /config/dropins/api.war

# This script will add the requested server configurations, apply any interim fixes and populate caches to optimize runtime
RUN configure.sh
