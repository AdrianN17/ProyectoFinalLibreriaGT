# publish-nexus.ps1
# ===================
# Publica (deploy) todos los artefactos del Andes API Toolkit contra un repositorio Nexus
# (o cualquier repositorio Maven remoto compatible: Artifactory, etc.), usando los serverId
# `nexus-releases` / `nexus-snapshots` declarados en <distributionManagement> del pom raiz.
#
# Por defecto, sin argumentos, publica contra un repositorio LOCAL basado en archivo
# (carpeta .local-nexus-repo/ en la raiz del proyecto) para poder demostrar/probar el
# mecanismo de `mvn deploy` sin depender de un servidor Nexus real.
#
# Para publicar contra un Nexus corporativo real:
#   1. Define las credenciales del servidor en %USERPROFILE%\.m2\settings.xml:
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
#        .\scripts\publish-nexus.ps1 `
#          -ReleasesUrl "https://nexus.miempresa.com/repository/maven-releases/" `
#          -SnapshotsUrl "https://nexus.miempresa.com/repository/maven-snapshots/"
#
# Uso:
#   .\scripts\publish-nexus.ps1                                          # repo local de prueba
#   .\scripts\publish-nexus.ps1 -ReleasesUrl <url> -SnapshotsUrl <url>   # Nexus real
#
param(
    [string]$ReleasesUrl = "",
    [string]$SnapshotsUrl = ""
)

$ErrorActionPreference = "Stop"

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$RootDir = Resolve-Path (Join-Path $ScriptDir "..")

Set-Location $RootDir

if ([string]::IsNullOrEmpty($ReleasesUrl) -and [string]::IsNullOrEmpty($SnapshotsUrl)) {
    Write-Host ">> Sin URLs explicitas: publicando en el repositorio LOCAL de prueba (.local-nexus-repo/)"
    mvn -DskipTests -Prelease clean deploy
} else {
    Write-Host ">> Publicando contra Nexus real:"
    Write-Host "     releases:  $ReleasesUrl"
    Write-Host "     snapshots: $SnapshotsUrl"
    mvn -DskipTests -Prelease clean deploy "-Dnexus.releases.url=$ReleasesUrl" "-Dnexus.snapshots.url=$SnapshotsUrl"
}

if ($LASTEXITCODE -ne 0) {
    Write-Error "mvn deploy fallo con codigo $LASTEXITCODE"
    exit $LASTEXITCODE
}

Write-Host ""
Write-Host "Deploy finalizado. Revisa .local-nexus-repo/ (modo local) o la consola de tu Nexus (modo real)."
