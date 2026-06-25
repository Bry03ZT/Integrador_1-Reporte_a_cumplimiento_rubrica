# Plan de Monitoreo Exhaustivo — Sistema de Gestión de Laptops

## 1. Objetivos del Monitoreo
Garantizar la disponibilidad, rendimiento y estabilidad de la aplicación en producción.

## 2. Métricas Clave a Monitorear

| Métrica | Herramienta | Umbral Crítico | Frecuencia |
|---|---|---|---|
| Tiempo de respuesta de BD (PostgreSQL) | Logback + System.nanoTime() | > 500 ms | Por cada request |
| Tasa de aciertos en caché (Guava Cache) | Logback logger.info | < 70% hit rate | Por cada consulta |
| Errores HTTP 500 (excepciones) | Logback logger.error | Cualquier error | Tiempo real |
| Uso de memoria JVM | JMX / VisualVM | > 80% heap | Cada 5 minutos |
| Disponibilidad de la BD | Endpoint /HealthServlet | Timeout > 3s | Cada 1 minuto |

## 3. Infraestructura de Logs (Implementada)
- **Framework:** SLF4J + Logback
- **Configuración:** `src/main/resources/logback.xml`
- **Niveles usados:**
  - `INFO`: Operaciones normales (hits de caché, tiempos de consulta)
  - `ERROR`: Excepciones de base de datos y fallos críticos
- **Clases monitoreadas:** `ProductoDAO.java`, `Conexion.java`, todos los DAOs

## 4. Medición de Rendimiento (Implementada)
- En `ProductoDAO.listarTodo()` se mide con `System.nanoTime()`:
  - `[CACHÉ HIT]` Inventario servido desde RAM en X ms
  - `[BD QUERY]` Inventario consultado desde PostgreSQL en X ms

## 5. Monitoreo de Salud del Sistema

### 5.1 Endpoint de Salud: `/HealthServlet`
- Verifica conexión activa a PostgreSQL
- Devuelve JSON con estado: `{"status": "UP", "db": "OK", "timestamp": "..."}`
- Implementado en: `src/main/java/controlador/HealthServlet.java`

## 6. Alertas y Acciones de Respuesta

| Evento | Acción |
|---|---|
| BD no disponible | Registrar en log, mostrar página de error amigable |
| Tiempo de respuesta > 500ms | Log de advertencia, revisar índices de BD |
| Error HTTP 500 repetido | Notificación al equipo, revisar stack trace en logs |

## 7. Revisión Periódica
- Revisión de logs: Diaria
- Revisión de métricas de rendimiento: Semanal
- Actualización del plan: Cada sprint/versión del proyecto