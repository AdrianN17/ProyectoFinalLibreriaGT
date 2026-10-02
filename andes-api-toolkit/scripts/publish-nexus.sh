#!/usr/bin/env bash
#
# publish-nexus.sh
# =================
# Publica (deploy) todos los artefactos del Andes API Toolkit contra un repositorio Nexus
# (o cualquier repositorio Maven remoto compatible: Artifactory, etc.), usando los serverId
# `nexus-releases` / `nexus-snapshots` declarados en <distributionManagement> del pom raiz.
#
# Por defecto, sin argumentos, publica contra un repositorio LOCAL basado en archivo
# (carpeta .local-nexus-repo/ en la raiz del proyecto) para poder demostrar/probar el
# mecanismo de `mvn deploy` sin depender de un servidor Nexus real.
#
# Para publicar contra un Nexus corporativo real:
#   1. Define las credenciales del servidor en ~/.m2/settings.xml:
#        <servers>
#          <server>
#            <id>nexus-releases</id>
#            <username>TU_USUARIO</username>
#            <password>TU_PASSWORD_O_TOKEN</password>
#          </server>
#          <server>
#            <id>nexus-snapshots</id>
#            <username>TU_USUARIO</username>
#            <password>TU_PASSWORD_O_TOKEN</password>
#          </server>
#        </servers>
#   2. Ejecuta este script pasando las URLs reales:
#        ./scripts/publish-nexus.sh \
#          https://nexus.miempresa.com/repository/maven-releases/ \
#          https://nexus.miempresa.com/repository/maven-snapshots/
#
# Uso:
#   ./scripts/publish-nexus.sh                                  # repo local de prueba (.local-nexus-repo)
#   ./scripts/publish-nexus.sh <releases-url> <snapshots-url>   # Nexus real
#
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"

cd "${ROOT_DIR}"

RELEASES_URL="${1:-}"
SNAPSHOTS_URL="${2:-}"

if [[ -z "${RELEASES_URL}" && -z "${SNAPSHOTS_URL}" ]]; then
  echo ">> Sin URLs explicitas: publicando en el repositorio LOCAL de prueba (.local-nexus-repo/)"
  mvn -DskipTests -Prelease clean deploy
else
  echo ">> Publicando contra Nexus real:"
  echo "     releases:  ${RELEASES_URL}"
  echo "     snapshots: ${SNAPSHOTS_URL}"
  mvn -DskipTests -Prelease clean deploy \
    -Dnexus.releases.url="${RELEASES_URL}" \
    -Dnexus.snapshots.url="${SNAPSHOTS_URL}"
fi

echo
echo "Deploy finalizado. Revisa .local-nexus-repo/ (modo local) o la consola de tu Nexus (modo real)."
