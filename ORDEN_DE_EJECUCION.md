# 📋 ORDEN DE EJECUCIÓN — Todo lo que debes hacer

> **Antes de la sustentación**, sigue este orden exacto de principio a fin.

---

## FASE 1 — Archivos que debes crear (documentación)
> Haz esto primero, sin tocar código aún.

- [ ] **1.1** Crear `reporte_seguridad.md` dentro de `Solucion_Avance_Proyecto_Final_3/`
  - Ya generado: copiar el contenido del archivo que se creó en esta sesión
  - Incluye 6 pruebas de seguridad (2 de autenticación + 2 de SQLi + 2 de validación)

- [ ] **1.2** Crear `plan_monitoreo.md` dentro de `Solucion_Avance_Proyecto_Final_3/`
  - Copiar el contenido del `PLAN_CUMPLIMIENTO_TOTAL.md` → sección Criterio 3

- [ ] **1.3** Crear `plan_mantenimiento.md` dentro de `Solucion_Avance_Proyecto_Final_3/`
  - Copiar el contenido del `PLAN_CUMPLIMIENTO_TOTAL.md` → sección Criterio 4

---

## FASE 2 — Código Java que debes agregar
> Solo 1 archivo nuevo de código.

- [ ] **2.1** Crear `src/main/java/controlador/HealthServlet.java`
  - Copiar el código del `PLAN_CUMPLIMIENTO_TOTAL.md` → sección Criterio 3, Acción 2
  - Este servlet responde en `/HealthServlet` con un JSON `{"status":"UP","database":"OK",...}`

---

## FASE 3 — Scripts de mantenimiento
> Crear la carpeta `scripts/` en la raíz del proyecto.

- [ ] **3.1** Crear `scripts/backup_bd.bat`
  - Copiar el script del `PLAN_CUMPLIMIENTO_TOTAL.md` → sección Criterio 4, Acción 2

- [ ] **3.2** Crear `scripts/purgar_logs.bat`
  - Copiar el script del `PLAN_CUMPLIMIENTO_TOTAL.md` → sección Criterio 4, Acción 2

---

## FASE 4 — Compilar y verificar que todo funciona
> Ahora sí, compilar el proyecto con el nuevo código agregado.

- [ ] **4.1** Abrir terminal en `Solucion_Avance_Proyecto_Final_3/`

- [ ] **4.2** Ejecutar los tests unitarios:
  ```bash
  mvn test
  ```
  **Resultado esperado:** `BUILD SUCCESS` con 9 tests en verde ✅

- [ ] **4.3** Compilar el proyecto completo:
  ```bash
  mvn clean package
  ```
  **Resultado esperado:** `BUILD SUCCESS` y archivo `target/Solucion_Avance_Proyecto_Final_3-04.war` generado ✅

---

## FASE 5 — Despliegue con base de datos
> La BD debe estar encendida ANTES de iniciar Tomcat.

- [ ] **5.1** Verificar que **PostgreSQL está corriendo**
  - Windows: `Servicios` → buscar `postgresql-x64-15` → Estado: **En ejecución**
  - Datos de conexión: host `localhost`, puerto `5432`, usuario `postgres`, contraseña `root`

- [ ] **5.2** Verificar que la BD `sistema_laptops` existe y tiene datos
  ```sql
  -- En pgAdmin → Query Tool:
  SELECT * FROM productos;
  SELECT * FROM usuarios;
  -- Deben devolver filas ✅
  ```
  - Si la BD está vacía: ejecutar `BD/sistema_laptops.sql` en pgAdmin

- [ ] **5.3** Copiar el `.war` a Tomcat:
  ```
  Origen:  target/Solucion_Avance_Proyecto_Final_3-04.war
  Destino: C:\tomcat9\webapps\
  ```

- [ ] **5.4** Iniciar Tomcat:
  ```bash
  C:\tomcat9\bin\startup.bat
  ```

- [ ] **5.5** Abrir en el navegador:
  ```
  http://localhost:8080/Solucion_Avance_Proyecto_Final_3-04/
  ```

- [ ] **5.6** Verificar login exitoso → confirma que la BD está conectada ✅

- [ ] **5.7** Verificar el endpoint de salud:
  ```
  http://localhost:8080/Solucion_Avance_Proyecto_Final_3-04/HealthServlet
  ```
  **Resultado esperado:** `{"status":"UP","database":"OK","timestamp":"..."}` ✅

---

## FASE 6 — Verificar logs en tiempo real
> Para demostrar monitoreo durante la sustentación.

- [ ] **6.1** Con Tomcat corriendo, abrir la consola donde se inició (ventana de `startup.bat`)

- [ ] **6.2** Ir a la app → listar inventario por primera vez
  - Buscar en la consola: `[BD QUERY] Inventario consultado desde PostgreSQL en X ms`

- [ ] **6.3** Listar inventario una segunda vez (dentro de los 5 minutos de caché)
  - Buscar en la consola: `[CACHÉ HIT] Inventario servido desde RAM en X ms`
  - El tiempo de la segunda vez debe ser **mucho menor** → demostrar el monitoreo de rendimiento ✅

---

## FASE 7 — Preparar la sustentación oral
> Lo último antes del día de la presentación.

- [ ] **7.1** Practicar mencionar estos conceptos en cada sección:

  | Sección | Concepto a mencionar |
  |---|---|
  | Pruebas Testing | **TDD — ciclo Rojo, Verde, Refactor** |
  | Pruebas Seguridad | **OWASP Top 10 — A01, A03** |
  | Despliegue | **Maven WAR + Apache Tomcat 9 + `<scope>provided`** |
  | Monitoreo | **SLF4J/Logback + System.nanoTime() + HealthServlet** |
  | Mantenimiento | **pg_dump + cron jobs + Windows Task Scheduler** |
  | Producto Final | **MVC + DAO + Factory + DIP (SOLID) + Git** |

- [ ] **7.2** Practicar el flujo de demo en vivo (sin leer):
  1. `mvn test` → 9 tests en verde
  2. Demostrar bypass de login fallido (seguridad)
  3. Demostrar SQL injection fallida (seguridad)
  4. `mvn clean package` → BUILD SUCCESS → mostrar el `.war`
  5. App funcionando en `localhost:8080` con login
  6. Mostrar logs `[BD QUERY]` vs `[CACHÉ HIT]` en consola
  7. Abrir `/HealthServlet` → JSON de salud
  8. Ejecutar `scripts/backup_bd.bat` en vivo
  9. Abrir `reporte_seguridad.md`, `plan_monitoreo.md`, `plan_mantenimiento.md`

---

## ✅ Checklist final — Todo listo para la sustentación

```
□ reporte_seguridad.md    → creado en raíz del proyecto
□ plan_monitoreo.md       → creado en raíz del proyecto
□ plan_mantenimiento.md   → creado en raíz del proyecto
□ HealthServlet.java      → creado en controlador/
□ scripts/backup_bd.bat   → creado
□ scripts/purgar_logs.bat → creado
□ mvn test                → 9 tests PASS ✅
□ mvn clean package       → BUILD SUCCESS + .war generado ✅
□ PostgreSQL corriendo    → sistema_laptops con datos ✅
□ App en localhost:8080   → login funcionando ✅
□ /HealthServlet          → JSON {"status":"UP"} ✅
□ Logs en consola         → CACHÉ HIT y BD QUERY visibles ✅
□ backup_bd.bat ejecutado → genera archivo .sql ✅
□ Sustentación oral       → conceptos TDD, OWASP, Maven, Logback, pg_dump listos ✅
```

---

## 📂 Estructura final esperada del proyecto

```
Solucion_Avance_Proyecto_Final_3/
├── pom.xml
├── reporte_seguridad.md          ← NUEVO (Criterio 1)
├── plan_monitoreo.md             ← NUEVO (Criterio 3)
├── plan_mantenimiento.md         ← NUEVO (Criterio 4)
├── scripts/                      ← NUEVO (Criterio 4)
│   ├── backup_bd.bat
│   └── purgar_logs.bat
└── src/
    ├── main/java/
    │   ├── config/
    │   │   ├── Conexion.java
    │   │   └── ValidarSesionFilter.java
    │   ├── controlador/
    │   │   ├── LoginServlet.java
    │   │   ├── ...
    │   │   └── HealthServlet.java    ← NUEVO (Criterio 3)
    │   ├── dao/
    │   ├── interfaces/
    │   ├── modelo/
    │   └── servicio/
    └── test/java/servicio/
        └── CalculadoraVentaTest.java ← MODIFICADO (9 tests)
```

---

*Generado el 2026-06-16 | Curso Integrador I — UTP | Semana 13*
