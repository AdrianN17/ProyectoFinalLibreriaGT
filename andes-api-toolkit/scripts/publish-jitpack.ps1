# publish-jitpack.ps1
# =====================
# JitPack no requiere configuracion del lado del proyecto: construye el artefacto
# bajo demanda a partir de un tag de git publicado en GitHub/GitLab (ver
# https://jitpack.io/docs/BUILDING/). Este script automatiza los 3 pasos manuales:
#   1. Leer la version actual del pom raiz (quitando el sufijo -SNAPSHOT si existiera).
#   2. Crear el tag `vX.Y.Z` (anotado) sobre el commit actual.
#   3. Empujar el tag al remoto `origin`.
#
# Uso (PowerShell):
#   .\scripts\publish-jitpack.ps1                # usa la version del pom (sin -SNAPSHOT)
#   .\scripts\publish-jitpack.ps1 -Version 1.2.0  # fuerza una version especifica
#
param(
    [string]$Version = ""
)

$ErrorActionPreference = "Stop"

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$RootDir = Resolve-Path (Join-Path $ScriptDir "..")

Set-Location $RootDir

try {
    git rev-parse --is-inside-work-tree | Out-Null
} catch {
    Write-Error "Este directorio no es un repositorio git. JitPack requiere un tag en un repo git remoto."
    exit 1
}

$status = git status --porcelain
if (-not [string]::IsNullOrEmpty($status)) {
    Write-Error "Hay cambios sin commitear. Haz commit/push de todo antes de taguear una version."
    exit 1
}

if ([string]::IsNullOrEmpty($Version)) {
    $pomLine = Select-String -Path "pom.xml" -Pattern "<version>" | Select-Object -First 1
    $pomVersion = $pomLine.Line -replace ".*<version>(.*)</version>.*", '$1'
    $Version = $pomVersion -replace "-SNAPSHOT$", ""
}

$Tag = "v$Version"

Write-Host ">> Version a publicar en JitPack: $Version (tag $Tag)"

$existingTag = git tag -l $Tag
if (-not [string]::IsNullOrEmpty($existingTag)) {
    Write-Error "El tag $Tag ya existe localmente."
    exit 1
}

git tag -a $Tag -m "Release $Version (JitPack)"
git push origin $Tag

Write-Host ""
Write-Host "Tag $Tag creado y publicado. JitPack construira la libreria bajo demanda la primera"
Write-Host "vez que alguien la solicite. Coordenadas de consumo tipicas (build.gradle):"
Write-Host ""
Write-Host "  repositories { maven { url 'https://jitpack.io' } }"
Write-Host "  dependencies { implementation 'com.github.TU_USUARIO:andes-api-toolkit:$Tag' }"
