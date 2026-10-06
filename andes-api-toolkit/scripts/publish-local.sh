#!/usr/bin/env bash
#
# publish-local.sh
# =================
# Publica (instala) todos los artefactos del Andes API Toolkit en el repositorio Maven
# LOCAL del usuario (~/.m2/repository), para que otros proyectos en la misma maquina
# puedan consumirlos con una dependencia normal (groupId/artifactId/version).
#
# Uso:
#   ./scripts/publish-local.sh            # instala saltando tests (rapido)
#   ./scripts/publish-local.sh --with-tests   # instala corriendo la suite completa
#
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"

cd "${ROOT_DIR}"

if [[ "${1:-}" == "--with-tests" ]]; then
  echo ">> Publicando en Maven Local (~/.m2) CON tests..."
  mvn clean install
else
  echo ">> Publicando en Maven Local (~/.m2) SIN tests (usa --with-tests para correrlos)..."
  mvn -DskipTests clean install
fi

echo
echo "Listo. Artefactos instalados en ~/.m2/repository/pe/andes/api/"
echo "Cualquier otro proyecto Maven en esta maquina ya puede depender de ellos, por ejemplo:"
echo
echo "  <dependency>"
echo "    <groupId>pe.andes.api</groupId>"
echo "    <artifactId>andes-api-client</artifactId>"
echo "    <version>1.0.0-SNAPSHOT</version>"
echo "  </dependency>"
