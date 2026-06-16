# Evaluación del Proyecto vs. Estándar Esperado (Rúbrica)

**Proyecto:** `Solucion_Avance_Proyecto_Final_3`

---

## 1. Pruebas de Software y Seguridad (2 pts)

| Aspecto | Requerido (Estándar Esperado) | ¿Cumple? | Evidencia |
|---------|------------------------------|----------|-----------|
| **Testing** | Entregar avances al 60% con uso efectivo de pruebas de software | ✅ Cumple | Prueba unitaria JUnit 4 en CalculadoraVentaTest.java (4 tests), documentando ciclo TDD (Rojo-Verde-Refactor) |
| **Seguridad** | Entregar avances al 70%, con reporte detallado de pruebas de seguridad | ⚠️ Cumple parcialmente | Tiene medidas de seguridad: ValidarSesionFilter (filtro de sesión), PreparedStatement (anti SQLi), StringUtils.isBlank() en LoginServlet. **Falta:** Reporte detallado de pruebas de seguridad |
| **Puntaje estimado** | **2 pts** | → | **1.0 / 2.0 pts** |

---

## 2. Despliegue del Proyecto (2 pts)

| Aspecto | Requerido (Estándar Esperado) | ¿Cumple? | Evidencia |
|---------|------------------------------|----------|-----------|
| **Maven + WAR** | Conocimiento sólido en despliegue Java con Maven | ✅ Cumple | pom.xml con packaging war, compilador configurado, dependencias completas (PostgreSQL, POI, Guava, Logback, JUnit) |
| **Descriptor web** | Configurar servidores para soporte adecuado | ✅ Cumple | web.xml con session-timeout y welcome-file; context.xml; beans.xml CDI |
| **Avances** | 80% de proyectos desplegados efectivamente | ✅ Cumple | Proyecto listo para mvn clean package y desplegar en Tomcat |
| **Puntaje estimado** | **2 pts** | → | **2.0 / 2.0 pts** |

---

## 3. Monitoreo del Proyecto (6 pts)

| Aspecto | Requerido (Estándar Esperado) | ¿Cumple? | Evidencia |
|---------|------------------------------|----------|-----------|
| **Logs** | Uso de logs para monitoreo | ✅ Cumple | SLF4J + Logback configurado, logger.info/error en Conexion.java y ProductoDAO.java |
| **Rendimiento** | Herramientas de medición de rendimiento | ✅ Cumple | System.nanoTime() en ProductoDAO.listarTodo() mide tiempo BD vs caché RAM |
| **Salud del sistema** | Health check | ❌ No cumple | No hay endpoints /health ni verificación de BD |
| **Plan de monitoreo** | Plan exhaustivo documentado | ❌ No cumple | No existe plan_monitoreo.md |
| **Puntaje estimado** | **6 pts** | → | **3.0 / 6.0 pts** |

---

## 4. Mantenimiento del Proyecto (5 pts)

| Aspecto | Requerido (Estándar Esperado) | ¿Cumple? | Evidencia |
|---------|------------------------------|----------|-----------|
| **Proyecto completo** | 100% del desarrollo | ✅ Cumple | Login, CRUD, ventas, reportes + exportación Excel |
| **Scripts de BD** | Scripts relevantes | ✅ Cumple | sistema_laptops.sql con estructura e inserts |
| **Backups** | Backups automatizados | ❌ No cumple | No hay scripts de pg_dump |
| **Cron jobs** | Tareas programadas | ❌ No cumple | No hay cron jobs de mantenimiento |
| **Plan de mantenimiento** | Plan integral documentado | ❌ No cumple | No existe plan_mantenimiento.md |
| **Puntaje estimado** | **5 pts** | → | **2.0 / 5.0 pts** |

---

## 5. Construcción del Producto Final (3 pts)

| Criterio | Requerido (Estándar Esperado) | ¿Cumple? | Evidencia |
|----------|------------------------------|----------|-----------|
| **Completa** | Cubre el alcance comprometido | ✅ Cumple | Login, CRUD productos, ventas con detalle, inventario con alertas, reportes analíticos (Chart.js + Excel) |
| **Coherente** | Código y documentación alineados | ✅ Cumple | DAOs alineados con esquema BD; comentarios explicativos |
| **Buenas prácticas** | Librerías, patrones, control de versiones | ✅ Cumple | DAO, Factory, DIP, MVC, caché Guava, Apache POI, Logback, JUnit, Git |
| **Autoría** | Código del estudiante/dominio | ✅ Cumple | Código descriptivo, comentarios en español, dominio de TDD y patrones |
| **Puntaje estimado** | **3 pts** | → | **3.0 / 3.0 pts** |

---

## 6. Sustentación Oral (2 pts)

| Aspecto | Requerido (Estándar Esperado) | ¿Cumple? | Evidencia |
|---------|------------------------------|----------|-----------|
| **Secuencia lógica y fluidez** | Ideas con secuencia lógica, fluidez | — | *No evaluable desde el código* |
| **Dominio del tema** | Vincula conceptos del curso | — | *El proyecto muestra dominio técnico (MVC, DIP, TDD, Factory, Guava, POI)* |
| **Puntaje estimado** | **2 pts** | → | *No evaluable* |

---

## Resumen de Cumplimiento

| Criterio | Máx. | Estimado | Estado |
|:---------|:----:|:--------:|:------:|
| 1. Pruebas de Software y Seguridad | 2 | **1.0** | ⚠️ Parcial |
| 2. Despliegue del Proyecto | 2 | **2.0** | ✅ Completo |
| 3. Monitoreo del Proyecto | 6 | **3.0** | ⚠️ Parcial |
| 4. Mantenimiento del Proyecto | 5 | **2.0** | ⚠️ Parcial |
| 5. Construcción del Producto Final | 3 | **3.0** | ✅ Completo |
| 6. Sustentación Oral | 2 | — | *No evaluable* |
| **Total** | **20** | **11.0 / 18.0** | **Desempeño Alto** |

### Brechas respecto al Estándar Esperado

| Criterio | Brecha | Acción requerida |
|:---------|:-------|:-----------------|
| **1. Seguridad** | Falta reporte documental de pruebas de seguridad | Crear reporte_seguridad.md |
| **3. Monitoreo** | Falta plan de monitoreo y health check | Crear plan_monitoreo.md + HealthServlet |
| **4. Mantenimiento** | Faltan backups, cron jobs y plan de mantenimiento | Crear plan_mantenimiento.md + script pg_dump |

### Fortalezas del Proyecto

- Arquitectura MVC sólida con separación clara de capas
- Uso correcto de interfaces y Factory Pattern (DIP de SOLID)
- Caché en RAM con Google Guava para optimización
- Exportación a Excel con Apache POI
- Pruebas unitarias con JUnit documentando ciclo TDD
- Seguridad: PreparedStatement anti-SQLi, filtro de sesión, validación de inputs
- Logs estructurados con SLF4J/Logback con medición de tiempos
- Interfaz moderna con Bootstrap 5, Font Awesome y Chart.js
