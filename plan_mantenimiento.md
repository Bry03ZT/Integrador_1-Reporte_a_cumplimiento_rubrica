# Plan de Mantenimiento Integral — Sistema de Gestión de Laptops

## 1. Objetivo
Garantizar la continuidad operativa y la integridad de los datos del sistema.

## 2. Estrategia de Backups

### 2.1 Base de Datos PostgreSQL
- **Herramienta:** `pg_dump` (PostgreSQL nativo)
- **Script:** `scripts/backup_bd.bat` (Windows) / `scripts/backup_bd.sh` (Linux)
- **Frecuencia:** Diaria a las 02:00 AM
- **Retención:** Últimos 7 backups diarios + 4 semanales
- **Destino:** Carpeta `backups/` con nombre formato `backup_YYYY-MM-DD.sql`

### 2.2 Archivos de Configuración
- Backup de `logback.xml`, `context.xml`, `web.xml` y `pom.xml`
- Frecuencia: Cada vez que se modifiquen

## 3. Scripts de Automatización

### 3.1 Script de Backup de BD (Windows)
- Archivo: `scripts/backup_bd.bat`
- Usa `pg_dump` para exportar la BD `sistema_laptops`

### 3.2 Script de Purga de Logs Antiguos (Windows)
- Archivo: `scripts/purgar_logs.bat`
- Elimina archivos de log con más de 30 días de antigüedad

## 4. Programación de Tareas (Cron Jobs)

| Tarea | Frecuencia | Script | Herramienta |
|---|---|---|---|
| Backup de base de datos | Diario 02:00 AM | `backup_bd.bat` | Windows Task Scheduler / crontab |
| Purga de logs antiguos | Semanal (domingo 03:00 AM) | `purgar_logs.bat` | Windows Task Scheduler / crontab |
| Reinicio preventivo de Tomcat | Mensual (1er domingo) | Manual | Administrador |

### 4.1 Configuración cron (Linux/Mac)
```text
0 2 * * * /ruta/scripts/backup_bd.sh
0 3 * * 0 /ruta/scripts/purgar_logs.sh
```

### 4.2 Configuración Windows Task Scheduler
- Abrir: `taskschd.msc`
- Nueva tarea: Ejecutar `backup_bd.bat` diariamente a las 02:00 AM

## 5. Actualización de Dependencias (Maven)
- Revisión trimestral de versiones en `pom.xml`
- Herramienta: `mvn versions:display-dependency-updates`
- Verificar compatibilidad antes de actualizar en producción

## 6. Control de Versiones (Git)
- Repositorio Git activo (`.gitattributes` presente)
- Política de ramas: `main` (producción) + `develop` (desarrollo)
- Commits descriptivos con prefijos: `[FIX]`, `[FEAT]`, `[MAINT]`

## 7. Checklist de Mantenimiento Mensual
- [ ] Verificar backup reciente de BD
- [ ] Revisar logs de errores del último mes
- [ ] Actualizar dependencias si hay versiones de seguridad
- [ ] Verificar disponibilidad del endpoint `/HealthServlet`
- [ ] Revisar espacio en disco del servidor