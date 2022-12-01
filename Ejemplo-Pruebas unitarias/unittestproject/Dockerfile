
FROM artifactory.apps.bancolombia.com/devops/openshift/vault:1.3.1 as vault

FROM artifactory.apps.bancolombia.com/devops/adoptopenjdk/openjdk11-openj9:x86_64-alpine-jre-11.0.10_9_openj9-0.24.0.v2

USER root

COPY --from=vault --chown=appuser /bin/vault /bin/vault

COPY --chown=appuser keys-configuration.sh /app/

# Set up permissions for user `appuser`
RUN mkdir /vault \
    && chown appuser /vault \
    && chmod 777 /vault \
    && chmod -R 777 /app \
    && mkdir /deployments \
    && chown appuser /deployments \
    && chmod "g+rx" /deployments \
    && chown appuser:root /deployments \
    && chown appuser /app/keys-configuration.sh \
    && chmod 777 /app/keys-configuration.sh \
    && mkdir /config \
    && chown appuser /config \
    && chmod "g+rwX" /config \
    && chown appuser:root /config \
    && apk upgrade --update-cache --available \
    && apk add openssl \
    && rm -rf /var/cache/apk/* \
    && chown appuser /opt/java/openjdk/lib/security/cacerts \
    && chmod 777 /opt/java/openjdk/lib/security/cacerts

COPY --chown=appuser target/* /deployments/

EXPOSE 8080

EXPOSE 9779

USER appuser

ENTRYPOINT [ "sh", "-c", "vault agent -config=/vault/config/agent-config.hcl && /app/keys-configuration.sh  && java -javaagent:/deployments/jmx_prometheus_javaagent-0.15.0.jar=9779:/deployments/prometheus-config.yml -jar /deployments/PlantillaProyecto-0.0.1-SNAPSHOT.jar"]