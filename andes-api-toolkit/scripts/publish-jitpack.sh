#!/usr/bin/env bash
#
# publish-jitpack.sh
# ====================
# JitPack no requiere configuracion del lado del proyecto: construye el artefacto
# bajo demanda a partir de un tag de git publicado en GitHub/GitLab (ver
# https://jitpack.io/docs/BUILDING/). Este script automatiza los 3 pasos manuales:
#   1. Leer la version actual del pom raiz (quitando el sufijo -SNAPSHOT si existiera).
#   2. Crear el tag `vX.Y.Z` (anotado) sobre el commit actual.
#   3. Empujar el tag al remoto `origin`.
#
# Uso:
#   ./scripts/publish-jitpack.sh                 # usa la version del pom (sin -SNAPSHOT)
#   ./scripts/publish-jitpack.sh 1.2.0            # fuerza una version especifica
#
# Despues de correr el script, la libreria queda disponible en JitPack apuntando a:
#   https://jitpack.io/#andes/andes-api-toolkit/vX.Y.Z
# (ajusta el `groupId`/coordenadas de JitPack al usuario/organizacion real de GitHub).
#
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"

cd "${ROOT_DIR}"

if ! git rev-parse --is-inside-work-tree > /dev/null 2>&1; then
  echo "ERROR: este directorio no es un repositorio git. JitPack requiere un tag en un repo git remoto." >&2
  exit 1
fi

if [[ -n "$(git status --porcelain)" ]]; then
  echo "ERROR: hay cambios sin commitear. Haz commit/push de todo antes de taguear una version." >&2
  exit 1
fi

VERSION="${1:-}"
if [[ -z "${VERSION}" ]]; then
  POM_VERSION="$(grep -m1 '<version>' pom.xml | sed -E 's/.*<version>(.*)<\/version>.*/\1/')"
  VERSION="${POM_VERSION%-SNAPSHOT}"
fi

TAG="v${VERSION}"

echo ">> Version a publicar en JitPack: ${VERSION} (tag ${TAG})"

if git rev-parse "${TAG}" >/dev/null 2>&1; then
  echo "ERROR: el tag ${TAG} ya existe localmente." >&2
  exit 1
fi

git tag -a "${TAG}" -m "Release ${VERSION} (JitPack)"
git push origin "${TAG}"

echo
echo "Tag ${TAG} creado y publicado. JitPack construira la libreria bajo demanda la primera"
echo "vez que alguien la solicite. Coordenadas de consumo tipicas (build.gradle):"
echo
echo "  repositories { maven { url 'https://jitpack.io' } }"
echo "  dependencies { implementation 'com.github.TU_USUARIO:andes-api-toolkit:${TAG}' }"
