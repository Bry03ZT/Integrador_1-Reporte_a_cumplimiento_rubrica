# Reporte de Evaluación de Avance de Proyecto Final

Este documento detalla el análisis del proyecto **Solucion_Avance_Proyecto_Final_3** con respecto a los requisitos definidos en el nivel **ESTÁNDAR ESPERADO** de la rúbrica del curso.

---

## Resumen Ejecutivo de Cumplimiento

A continuación se muestra una estimación del cumplimiento del proyecto frente al **Estándar Esperado (Máximo Puntaje)**:

| Criterio | Puntaje Máx. | Estado de Cumplimiento | Puntos Estimados | Hallazgos Clave |
| :--- | :---: | :---: | :---: | :--- |
| **1. Pruebas de Software y Seguridad** | 2 pts | **Cumple Parcialmente** | 1.0 / 2.0 pts | Cuenta con pruebas unitarias TDD JUnit, pero no presenta un documento físico de Reporte de Pruebas de Seguridad u observaciones levantadas. |
| **2. Despliegue del Proyecto** | 2 pts | **Cumple** | 2.0 / 2.0 pts | Proyecto estructurado con Maven (`pom.xml` tipo `war`), configuraciones listas para Tomcat (`web.xml`, `context.xml`) y dependencias claras. |
| **3. Monitoreo del Proyecto** | 6 pts | **Cumple Parcialmente** | 3.0 / 6.0 pts | Logs integrados con SLF4J/Logback y medición de rendimiento en consultas DAO (`System.nanoTime()`), pero carece de un documento "Plan de Monitoreo Exhaustivo" y herramientas de salud de sistema. |
| **4. Mantenimiento del Proyecto** | 5 pts | **Cumple Parcialmente** | 2.0 / 5.0 pts | El proyecto está al 100% de desarrollo y cuenta con un script SQL estructurado en `BD/sistema_laptops.sql`, pero falta el plan de mantenimiento integral y la configuración de cron/backups. |
| **5. Construcción del Producto Final** | 3 pts | **Cumple** | 3.0 / 3.0 pts | Aplicación robusta con Login, CRUD, ventas y reportería (con gráficos Chart.js e integración de Apache POI para exportación a Excel). Cumple los 4 criterios de calidad. |
| **6. Sustentación Oral** | 2 pts | *No Evaluable* | — | Se evalúa en la sustentación del estudiante (el proyecto estructurado en MVC y patrones facilita una buena sustentación). |
| **Total Estimado** | **18 pts** | **Desempeño Alto** | **11.0 / 18.0 pts** *(De los puntos evaluables por código)* | **El proyecto a nivel de software y arquitectura es sobresaliente.** Se puede alcanzar el 100% agregando los planes documentales (Seguridad, Monitoreo, Mantenimiento). |

---

## Análisis Detallado por Criterio

### 1. Pruebas de Software y Seguridad
* **Requisito del Estándar Esperado (2 pts):**
  * **Testing:** Entregar avances al 60% de los proyectos demostrando uso efectivo de pruebas de software (Testing) y recibiendo retroalimentación.
  * **Seguridad:** Entregar avances al 70% de los proyectos, con observaciones levantadas y un reporte detallado de las pruebas de seguridad realizadas.
* **Evaluación en el Proyecto:** **CUMPLE PARCIALMENTE** (1.0 / 2.0 pts)
  * **Puntos a favor:**
    * Posee pruebas unitarias automatizadas en la ruta [CalculadoraVentaTest.java](file:///c:/Users/LAB-USR-AREQUIPA/Integrador_1/Proyecto-con-Solucion-3/Solucion_Avance_Proyecto_Final_3/src/test/java/servicio/CalculadoraVentaTest.java) que cubren la lógica del negocio de [CalculadoraVentaService.java](file:///c:/Users/LAB-USR-AREQUIPA/Integrador_1/Proyecto-con-Solucion-3/Solucion_Avance_Proyecto_Final_3/src/main/java/servicio/CalculadoraVentaService.java) usando JUnit 4. El archivo detalla explícitamente el ciclo TDD (*Rojo, Verde, Refactor*).
    * Cuenta con medidas de seguridad a nivel de desarrollo:
      * **Seguridad de Sesiones:** El filtro [ValidarSesionFilter.java](file:///c:/Users/LAB-USR-AREQUIPA/Integrador_1/Proyecto-con-Solucion-3/Solucion_Avance_Proyecto_Final_3/src/main/java/config/ValidarSesionFilter.java) protege de forma centralizada todas las páginas JSP privadas (`/gestion_productos.jsp`, `/inventario.jsp`, `/registrar_venta.jsp`, `/reportes.jsp`), impidiendo que un usuario no autenticado acceda directamente copiando la URL.
      * **Prevención de Inyección SQL (SQLi):** Todos los DAOs (`ProductoDAO`, `UsuarioDAO`, `VentaDAO`, `ReporteDAO`) utilizan de manera estricta `PreparedStatement` con parámetros tipados para evitar la inyección de código SQL malicioso.
      * **Validación de Inputs:** Uso de la librería `Apache Commons Lang3` (`StringUtils.isBlank()`) en servlets como [LoginServlet.java](file:///c:/Users/LAB-USR-AREQUIPA/Integrador_1/Proyecto-con-Solucion-3/Solucion_Avance_Proyecto_Final_3/src/main/java/controlador/LoginServlet.java) para robustecer la entrada de datos.
  * **Faltantes para cumplir al 100%:**
    * Falta el **Reporte Detallado de Pruebas de Seguridad** y la bitácora de observaciones levantadas.
  * **Recomendación:** Agregar un archivo `reporte_seguridad.md` en la raíz del proyecto que explique estas pruebas (ej. pruebas de evasión de login, inyecciones de SQL simuladas con herramientas como OWASP ZAP) y sus observaciones levantadas/resueltas.

---

### 2. Despliegue del Proyecto
* **Requisito del Estándar Esperado (2 pts):**
  * Demostrar un conocimiento sólido en el despliegue de aplicaciones Java usando Maven y configurando servidores para garantizar el soporte adecuado. Avances al 80% de los proyectos asignados, desplegando efectivamente y aplicando observaciones levantadas.
* **Evaluación en el Proyecto:** **CUMPLE** (2.0 / 2.0 pts)
  * **Puntos a favor:**
    * El proyecto cuenta con un archivo [pom.xml](file:///c:/Users/LAB-USR-AREQUIPA/Integrador_1/Proyecto-con-Solucion-3/Solucion_Avance_Proyecto_Final_3/pom.xml) configurado correctamente con empaquetamiento `<packaging>war</packaging>` y dependencias gestionadas mediante Maven para su fácil empaquetamiento y portabilidad.
    * Posee el descriptor de despliegue web estándar [web.xml](file:///c:/Users/LAB-USR-AREQUIPA/Integrador_1/Proyecto-con-Solucion-3/Solucion_Avance_Proyecto_Final_3/src/main/webapp/WEB-INF/web.xml) configurando el `session-timeout` (30 minutos) y la página de bienvenida (`index.jsp`).
    * Contiene [context.xml](file:///c:/Users/LAB-USR-AREQUIPA/Integrador_1/Proyecto-con-Solucion-3/Solucion_Avance_Proyecto_Final_3/src/main/webapp/META-INF/context.xml) para establecer el path del contexto en el contenedor de servlets.
    * Todas las dependencias (PostgreSQL JDBC driver, Apache POI, Google Guava, Logback, JUnit) están declaradas formalmente. El ámbito `<scope>provided</scope>` está correctamente configurado para las APIs del servidor web (`jakartaee-api`).
    * El código está 100% preparado para ser compilado (`mvn clean package`) y desplegado en servidores de aplicaciones (como Apache Tomcat 9 o 10).

---

### 3. Monitoreo del Proyecto
* **Requisito del Estándar Esperado (6 pts):**
  * Demostrar un dominio de las mejores prácticas para el monitoreo de aplicaciones (logs, herramientas de rendimiento y herramientas de salud del sistema). Avances al 90% y elaborar un plan de monitoreo exhaustivo.
* **Evaluación en el Proyecto:** **CUMPLE PARCIALMENTE** (3.0 / 6.0 pts)
  * **Puntos a favor:**
    * Tiene configurada la infraestructura de logs a través de SLF4J y Logback mediante el archivo de recursos [logback.xml](file:///c:/Users/LAB-USR-AREQUIPA/Integrador_1/Proyecto-con-Solucion-3/Solucion_Avance_Proyecto_Final_3/src/main/resources/logback.xml).
    * Uso activo de logs estructurados en clases críticas como [ProductoDAO.java](file:///c:/Users/LAB-USR-AREQUIPA/Integrador_1/Proyecto-con-Solucion-3/Solucion_Avance_Proyecto_Final_3/src/main/java/dao/ProductoDAO.java) y [Conexion.java](file:///c:/Users/LAB-USR-AREQUIPA/Integrador_1/Proyecto-con-Solucion-3/Solucion_Avance_Proyecto_Final_3/src/main/java/config/Conexion.java) para registrar eventos informativos (`logger.info`) y excepciones críticas (`logger.error`).
    * **Medición de Rendimiento:** En `ProductoDAO.listarTodo()`, se implementa un cronómetro de rendimiento (`System.nanoTime()`) para medir el tiempo exacto en milisegundos que demora en servirse el inventario desde la base de datos PostgreSQL versus la caché de RAM, imprimiéndolo en el log:
      * `logger.info("✅ [CACHÉ HIT] Inventario servido desde RAM en {} ms...", tiempoMs);`
      * `logger.info("🗄️  [BD QUERY] Inventario consultado desde PostgreSQL en {} ms...", tiempoMs);`
  * **Faltantes para cumplir al 100%:**
    * No se incluye un **Plan de Monitoreo Exhaustivo** documentado.
    * No cuenta con herramientas automáticas o endpoints para evaluar la salud del sistema (ej. endpoints `/health` de estado de conexión a la BD o memoria usada).
  * **Recomendación:** 
    1. Crear un archivo `plan_monitoreo.md` detallando las métricas clave (tiempos de respuesta de base de datos, aciertos de caché, porcentaje de errores HTTP 500, uso de memoria).
    2. Opcionalmente, crear un Servlet `/HealthServlet` que verifique si la conexión a PostgreSQL está activa y exponga un JSON con el estado de salud del sistema.

---

### 4. Mantenimiento del Proyecto
* **Requisito del Estándar Esperado (5 pts):**
  * Demostrar un dominio de las mejores prácticas para el mantenimiento (implementación efectiva de cron jobs, backups y scripts relevantes). Entregar proyectos completos al 100% y elaborar un plan de mantenimiento integral.
* **Evaluación en el Proyecto:** **CUMPLE PARCIALMENTE** (2.0 / 5.0 pts)
  * **Puntos a favor:**
    * El desarrollo del proyecto a nivel de software está al **100% completo**, cumpliendo con la arquitectura MVC, persistencia en BD y exportación de archivos.
    * Cuenta con el script de base de datos estructurado en [sistema_laptops.sql](file:///c:/Users/LAB-USR-AREQUIPA/Integrador_1/Proyecto-con-Solucion-3/BD/sistema_laptops.sql) para inicializar, limpiar y poblar la base de datos de manera replicable.
  * **Faltantes para cumplir al 100%:**
    * Falta un **Plan de Mantenimiento Integral** documentado.
    * No se evidencian scripts de automatización de copias de seguridad (backups como `.bat` o `.sh` ejecutando `pg_dump` de PostgreSQL).
    * No tiene configurados cron jobs o scripts para purgar logs antiguos o realizar mantenimiento rutinario en la base de datos.
  * **Recomendación:**
    1. Crear un archivo `plan_mantenimiento.md` en el cual se describa la estrategia de backups, actualización de librerías y reinicios programados del servidor.
    2. Escribir un script simple de backup de PostgreSQL (`backup_bd.bat` o `backup_bd.sh`) dentro de una carpeta `scripts/` utilizando el comando `pg_dump` para demostrar el dominio práctico del mantenimiento de base de datos.

---

### 5. Construcción del Producto Final
* **Requisito del Estándar Esperado (3 pts):**
  * Construir una solución informática que considere 4 criterios:
    1. **Completa:** Cubre el alcance comprometido.
    2. **Coherente:** La documentación y el código están alineados.
    3. **Buenas Prácticas:** Usa librerías adecuadas, patrones de diseño, software de control de versiones.
    4. **Autoría:** El código fue hecho por el estudiante o lo domina.
* **Evaluación en el Proyecto:** **CUMPLE** (3.0 / 3.0 pts)
  * **Análisis de criterios:**
    1. **Completa:** Cubre completamente el alcance: Login con credenciales, visualización y gestión (CRUD) de inventario de laptops, registro de ventas detalladas, módulo de reportes gráficos avanzados (mediante Chart.js en la UI) y descarga del reporte en formato físico Excel (.xlsx).
    2. **Coherente:** Las operaciones y el mapeo relacional en los DAOs concuerdan exactamente con la estructura de tablas y datos definidas en [sistema_laptops.sql](file:///c:/Users/LAB-USR-AREQUIPA/Integrador_1/Proyecto-con-Solucion-3/BD/sistema_laptops.sql).
    3. **Buenas Prácticas:**
       * **Patrones de Diseño:** Implementa patrón **DAO (Data Access Object)** para aislar la persistencia; patrón **Abstract Factory / Factory** mediante [DAOFactory.java](file:///c:/Users/LAB-USR-AREQUIPA/Integrador_1/Proyecto-con-Solucion-3/Solucion_Avance_Proyecto_Final_3/src/main/java/dao/DAOFactory.java) para desacoplar la creación de DAOs; e **Inyección/Inversión de Control** (haciendo uso de interfaces como `IProductoDAO`).
       * **Caché en RAM:** Uso de la librería industrial Google Guava en [ProductoDAO.java](file:///c:/Users/LAB-USR-AREQUIPA/Integrador_1/Proyecto-con-Solucion-3/Solucion_Avance_Proyecto_Final_3/src/main/java/dao/ProductoDAO.java) para implementar caché temporal de 5 minutos, reduciendo las consultas redundantes a la BD y optimizando tiempos de respuesta.
       * **Librerías robustas:** `Apache POI` para manipulación de hojas de cálculo, `Logback` para logs y `JUnit` para testing.
       * **Control de versiones:** Archivo `.gitattributes` en la raíz indica el uso de Git.
    4. **Autoría:** El código presenta nombres de variables descriptivos, comentarios explicativos en español del flujo de negocio y de los conceptos del curso (como TDD, RNF02, DIP), demostrando un claro dominio técnico.

---

### 6. Sustentación Oral
* **Requisito del Estándar Esperado (2 pts):**
  * Secuencia lógica, fluidez, coherencia de ideas y dominio de la sección vinculando las ideas con conceptos teóricos del curso.
* **Evaluación en el Proyecto:** *No evaluable desde el código.*
  * **Observación:** El estudiante demuestra en su código conceptos muy sólidos del curso (como principios SOLID/DIP al usar fábricas e interfaces, patrones arquitectónicos MVC, TDD y control de performance en milisegundos). Esto proporciona una excelente base para realizar una sustentación oral fluida y fundamentada.
