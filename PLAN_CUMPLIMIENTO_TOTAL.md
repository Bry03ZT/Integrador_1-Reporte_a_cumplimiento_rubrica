# ✅ PLAN DE CUMPLIMIENTO TOTAL — Rúbrica Curso Integrador I

> **Objetivo:** Pasar de 11/18 pts → **18/18 pts** *(más los 2 pts de sustentación = 20/20)*
> **Estado actual:** Actualizado con los logros completados en nuestra sesión.

---

## 🔴 CRITERIO 1 — Pruebas de Software y Seguridad (actualmente 1.0 / 2.0 pts)

### ¿Qué faltaba? → Reporte detallado de Pruebas de Seguridad + más pruebas unitarias

### ✅ Acción: Crear el archivo `reporte_seguridad.md` y expandir TDD
> **ESTADO: ✅ COMPLETADO**

**Logros alcanzados:**
1. **Testing Unitario TDD:** Se expandió el archivo `CalculadoraVentaTest.java` de 4 a **9 pruebas unitarias**, cubriendo todas las reglas de negocio y los casos borde (fases Rojo, Verde, Refactor).
2. **Pruebas de Seguridad:** Se generó y documentó el archivo 📄 `reporte_seguridad.md` con **6 pruebas de seguridad** formales:
   - 2 pruebas de autenticación (Bypass de Login).
   - 2 pruebas de inyección SQL (usando el correo correcto `admin@tech.com`).
   - 2 pruebas de validación de entradas (Inputs vacíos o en blanco).
3. **Observaciones:** Se documentó la resolución de 3 vulnerabilidades críticas gracias a `ValidarSesionFilter`, `PreparedStatement` y `StringUtils.isBlank()`.

---

## 🔴 CRITERIO 3 — Monitoreo del Proyecto (actualmente 3.0 / 6.0 pts)

### ¿Qué faltaba? → Plan de Monitoreo Exhaustivo + endpoint de salud del sistema

### ✅ Acción 1: Crear el archivo `plan_monitoreo.md`
> **ESTADO: ✅ COMPLETADO** (El archivo ya fue creado correctamente)
Se generó el documento detallando las métricas, infraestructura de logs con Logback (CACHÉ HIT vs BD QUERY) y las alertas.

### ⚠️ Acción 2: Crear el `HealthServlet.java`
> **ESTADO: ⚠️ PENDIENTE DE REVISIÓN** (Asegúrate de haberlo creado en tu código)

Si no lo has hecho, debes crear este archivo en `src/main/java/controlador/HealthServlet.java`:

```java
package controlador;

import config.Conexion;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.time.LocalDateTime;

@WebServlet("/HealthServlet")
public class HealthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String dbStatus = "OK";
        String appStatus = "UP";
        try (Connection conn = Conexion.getConexion()) {
            if (conn == null || conn.isClosed()) {
                dbStatus = "DOWN";
                appStatus = "DEGRADED";
            }
        } catch (Exception e) {
            dbStatus = "DOWN - " + e.getMessage();
            appStatus = "DEGRADED";
        }
        PrintWriter out = response.getWriter();
        out.print("{\"status\": \"" + appStatus + "\",\"database\": \"" + dbStatus + "\",\"timestamp\": \"" + LocalDateTime.now() + "\"}");
    }
}
```

---

## 🔴 CRITERIO 4 — Mantenimiento del Proyecto (actualmente 2.0 / 5.0 pts)

### ¿Qué faltaba? → Plan de mantenimiento integral + script de backup + cron job documentado

### ✅ Acción 1: Crear el archivo `plan_mantenimiento.md`
> **ESTADO: ✅ COMPLETADO** (El archivo ya fue creado correctamente)
Se generó el documento detallando la estrategia de backups, el uso de `pg_dump` y los cron jobs (Windows Task Scheduler).

### ⚠️ Acción 2: Crear la carpeta `scripts/` con los scripts de backup
> **ESTADO: ⚠️ PENDIENTE DE REVISIÓN** (Asegúrate de haber creado estos archivos)

Si no los tienes, crea una carpeta `scripts/` en la raíz de tu proyecto y agrega estos dos archivos:

📄 **`scripts/backup_bd.bat`**:
```batch
@echo off
SET DB_NAME=sistema_laptops
SET DB_USER=postgres
SET DB_HOST=localhost
SET DB_PORT=5432
SET BACKUP_DIR=%~dp0..\backups
SET DATE_STR=%DATE:~10,4%-%DATE:~4,2%-%DATE:~7,2%
SET BACKUP_FILE=%BACKUP_DIR%\backup_%DATE_STR%.sql
IF NOT EXIST "%BACKUP_DIR%" mkdir "%BACKUP_DIR%"
pg_dump -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -F p -f "%BACKUP_FILE%" %DB_NAME%
pause
```

📄 **`scripts/purgar_logs.bat`**:
```batch
@echo off
SET LOGS_DIR=%~dp0..\logs
forfiles /p "%LOGS_DIR%" /s /m *.log /d -30 /c "cmd /c del @file"
pause
```

---

## 🟡 CRITERIO 5 — Construcción del Producto Final (ya en 3.0 / 3.0 pts)
> **ESTADO: ✅ CUMPLE AL 100%** (No requiere acción adicional)

---

## 🟡 CRITERIO 2 — Despliegue del Proyecto (ya en 2.0 / 2.0 pts)
> **ESTADO: ✅ CUMPLE AL 100%** (Despliegue local Tomcat WAR. Documentado en la guía de despliegue)

---

## 🎤 CRITERIO 6 — Sustentación Oral (2 pts — Se evalúa en vivo)
> **ESTADO: ⏳ EN PREPARACIÓN**

### ✅ Qué debes hacer para los 2 pts:
1. **Secuencia lógica:** Testing → Seguridad → Despliegue → Monitoreo → Mantenimiento → Producto Final.
2. **Fluidez:** Explica con tus palabras los errores inducidos en las pruebas (fase roja).
3. **Dominio (CLAVE):** Vincula tu código con conceptos del curso:
   - Menciona **TDD (Rojo, Verde, Refactor)** al hablar de los 9 tests.
   - Menciona **PreparedStatement y OWASP** al hablar del ataque con `admin@tech.com`.
   - Menciona **SLF4J/Logback** en el monitoreo.
   - Menciona **pg_dump y cron jobs** en mantenimiento.

---

## 📋 RESUMEN FINAL DE ENTREGABLES PARA LA RÚBRICA

| # | Entregable | Estado |
|---|---|---|
| 1 | `reporte_seguridad.md` (6 pruebas + admin@tech.com) | ✅ Completado |
| 2 | `CalculadoraVentaTest.java` (9 tests TDD) | ✅ Completado |
| 3 | `plan_monitoreo.md` | ✅ Completado |
| 4 | `controlador/HealthServlet.java` | ⚠️ Revisar si ya se creó en código |
| 5 | `plan_mantenimiento.md` | ✅ Completado |
| 6 | `scripts/backup_bd.bat` y `purgar_logs.bat` | ⚠️ Revisar si ya se crearon |
| 7 | Preparar sustentación oral | ⏳ En progreso |

---

## 🏆 PROYECCIÓN DE PUNTAJE FINAL

| Criterio | Puntaje Actual | Puntaje al Cumplir | Acción requerida |
|---|---|---|---|
| 1. Pruebas de Software y Seguridad | 1.0 / 2.0 | **2.0 / 2.0** | ✅ Archivos listos |
| 2. Despliegue del Proyecto | 2.0 / 2.0 | **2.0 / 2.0** | ✅ Ya cumple |
| 3. Monitoreo del Proyecto | 3.0 / 6.0 | **6.0 / 6.0** | ✅ Plan listo / ⚠️ Falta verificar Servlet |
| 4. Mantenimiento del Proyecto | 2.0 / 5.0 | **5.0 / 5.0** | ✅ Plan listo / ⚠️ Falta verificar scripts |
| 5. Construcción del Producto Final | 3.0 / 3.0 | **3.0 / 3.0** | ✅ Ya cumple |
| 6. Sustentación Oral | — | **2.0 / 2.0** | ⏳ Preparar sustentación |
| **TOTAL** | **11.0 / 18.0** | **🏆 20.0 / 20.0** | |

---

*Generado el 2026-06-18 | Curso Integrador I — UTP | Semana 13*
