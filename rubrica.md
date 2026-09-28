RÚBRICA DE PROYECTO FINAL DE CURSO
Sección : CURS-000504
Curso : Java Library Development
Instructor : Aristedes Novoa Arbildo
Horario :
 Lunes y Miércoles de 7:00p.m. a 9:00p.m.
Alumno :

I. CONSIDERACIONES GENERALES
a. El desarrollo del proyecto es personal, sin embargo, está permitido colaborar con sus
compañeros de clase o la asesoría de cualquier otro profesional con experiencia en la
creación de librerías personalizadas para estandarizar y reutilizar componentes de código.
b. Está permitido usar libros, tutoriales, scripts y presentaciones para revisar y repasar
conceptos y codificación a reutilizar.
c. Reutilizar proyectos previos desarrollados en clase o descargados de internet, incluyendo
librerías y rutinas de código, siempre que no violen la propiedad de derecho del autor.
II. ACTIVIDADES A REALIZAR
a. Crear uno o más proyectos como (pruebas de concepto – PoCs) y un proyecto integrado
que le permita utilizar en conjunto las librerías creadas y publicadas en modo local y
remoto.
b. Crear un repositorio en github, gitlab o cualquier otra plataforma de gestión de versiones
para versionar el código fuente de su solución (este repositorio será entregado al
instructor del curso como sustento de su proyecto y registrado en el sistema académico
de Galaxy Training).
c. La solución debe incluir las principales herramientas descritas a continuación (cada
categoría vale 2 puntos):
# Pattern Consideraciones
Cumple Comentarios de
revisión
Si No
01 Inventario de
Librerías
Crear un inventario de las librerías y descripción de las funcionalidades que
ofrecen cada una de ellas, usos y aplicaciones; preparar una tabla.
02 Estructura Modular
Crear librerías con estructura clara (core, api, autoconfigure, test, ejemplos)
que facilite la reutilización, el versionado y su mantenimiento. Incluir archivos
AutoConfiguration.imports cuando aplique.
03 Maven & Gradle
Configurar correctamente pom.xml o build.gradle aplicando BOM, SemVer y
publicación local/remota. Validar dependencias y herencia en proyectos
modulares. Crear BOM corporativos para estandarizar versiones
04 Librerías clásicas
Crear librerías que pueden ser utilizadas con Java puro, las clases deben ser
finales y los métodos estáticos; crear por lo menos 2 librerías de esta
categoría. Crear las PoCs de uso para demostrar su funcionalidad
05
Librerías
de Envoltura
Crear librerías de envoltura para integrar librerías con métodos estáticos o
legacy en el ecosistema de Spring. Crear las PoCs de uso para demostrar su
funcionalidad
06
Librerías
Autoconfigurables
Crear librerías auto configurables basadas en Spring para integrar librerías
con métodos estáticos, librerías de envoltura o nativas del ecosistema de
Spring. Crear las PoCs de uso para demostrar su funcionalidad
# Pattern Consideraciones
Cumple Comentarios de
revisión
Si No
07 Documentación
Técnica
Incluir README.md, JavaDocs, Spring REST Docs (librerías de APIs
REST), ejemplos de integración y uso. Mantener coherencia con la
convención que usted establezca.
08
Publicación y
versionamiento
Aplicar SemVer y publicar librerías y BOM corporativos en Nexus
(Local) y JitPack. Maven Central y otros repositorios. Crear scripts de
publicación automática en Linux/Mac(sh) y Windows(ps1/bat))
09 Presentación Preparar una presentación (ppt), README.md, diagramas o
cualquier otro recursos de documentación para sustentar su desarrollo
10
Otros Implementar temas relacionados con creación, publicación y uso de
librerías personalizadas como complemento a su investigación y
entrenamiento.
III. PRESENTACION
El alumno deberá realizar la presentación en vivo de sus casos con la implementación de las funcionalidades solicitadas de
acuerdo a la fecha coordinada y programada por Galaxy Training. En caso de que usted haya concluido con el desarrollo
de su proyecto antes de la fecha programada, puede coordinar con Galaxy Training para programar una fecha de
sustentación.