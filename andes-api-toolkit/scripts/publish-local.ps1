# publish-local.ps1
# ===================
# Publica (instala) todos los artefactos del Andes API Toolkit en el repositorio Maven
# LOCAL del usuario (%USERPROFILE%\.m2\repository), para que otros proyectos en la misma
# maquina puedan consumirlos con una dependencia normal (groupId/artifactId/version).
#
# Uso (PowerShell):
#   .\scripts\publish-local.ps1                 # instala saltando tests (rapido)
#   .\scripts\publish-local.ps1 -WithTests       # instala corriendo la suite completa
#
param(
    [switch]$WithTests
)

$ErrorActionPreference = "Stop"

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$RootDir = Resolve-Path (Join-Path $ScriptDir "..")

Set-Location $RootDir

if ($WithTests) {
    Write-Host ">> Publicando en Maven Local (~/.m2) CON tests..."
    mvn clean install
} else {
    Write-Host ">> Publicando en Maven Local (~/.m2) SIN tests (usa -WithTests para correrlos)..."
    mvn -DskipTests clean install
}

if ($LASTEXITCODE -ne 0) {
    Write-Error "mvn install fallo con codigo $LASTEXITCODE"
    exit $LASTEXITCODE
}

Write-Host ""
Write-Host "Listo. Artefactos instalados en `$env:USERPROFILE\.m2\repository\pe\andes\api\"
Write-Host "Cualquier otro proyecto Maven en esta maquina ya puede depender de ellos, por ejemplo:"
Write-Host ""
Write-Host "  <dependency>"
Write-Host "    <groupId>pe.andes.api</groupId>"
Write-Host "    <artifactId>andes-api-client</artifactId>"
Write-Host "    <version>1.0.0-SNAPSHOT</version>"
Write-Host "  </dependency>"
