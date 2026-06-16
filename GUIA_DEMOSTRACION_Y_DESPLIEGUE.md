# 🎬 Guía de Demostración por Criterio + Opciones de Despliegue

> **Proyecto:** Solucion_Avance_Proyecto_Final_3
> **Stack:** Java 8 + JSP/Servlet + Maven → WAR → Tomcat 9 + PostgreSQL

---

## 🔴 IMPORTANTE: Sobre Vercel, .jar y Docker — ¿Cuál usar?

### ❌ Vercel — NO aplica para este proyecto
Vercel es solo para aplicaciones **frontend estáticas o Node.js (Next.js, React, etc.)**.
Tu proyecto es Java/JSP con Servlets → Vercel no puede ejecutar código Java. Punto.

### ❌ .jar — NO aplica aquí
Un `.jar` es para aplicaciones Java de escritorio o microservicios Spring Boot standalone.
Tu `pom.xml` tiene `<packaging>war</packaging>` → el artefacto correcto es un **`.war`**, no un `.jar`.

### ✅ Lo correcto para tu proyecto: WAR → Tomcat
```
mvn clean package
→ genera: target/Solucion_Avance_Proyecto_Final_3-04.war
→ se despliega en: Apache Tomcat 9 (webapps/)
→ URL resultante: http://localhost:8080/Solucion_Avance_Proyecto_Final_3-04/
```

---

## 🚀 Opciones de Despliegue (de menor a mayor complejidad)

### Opción A — Local con Tomcat 9 *(lo que ya tienes — SUFICIENTE para la rúbrica)*
```
1. mvn clean package          → genera el .war
2. Copiar .war a Tomcat/webapps/
3. Iniciar Tomcat → acceder en http://localhost:8080/...
```
**✅ Esto es exactamente lo que pide la rúbrica:** "usar Maven y configurar servidores"

---

### Opción B — Docker *(para demostrar que funciona en "cualquier máquina")*

Si quieres impresionar o simplificar la demo, un `Dockerfile` hace que funcione en cualquier PC sin instalar Java ni Tomcat manualmente.

Crea este archivo en la raíz del proyecto:

📄 **`Dockerfile`**
```dockerfile
# ---- Etapa 1: Compilar con Maven ----
FROM maven:3.9.6-eclipse-temurin-8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ---- Etapa 2: Ejecutar en Tomcat 9 ----
FROM tomcat:9.0-jdk8-temurin
# Limpiar apps por defecto de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*
# Copiar nuestro .war generado
COPY --from=build /app/target/Solucion_Avance_Proyecto_Final_3-04.war \
     /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
```

📄 **`docker-compose.yml`** *(para levantar app + PostgreSQL juntos)*
```yaml
version: '3.8'
services:

  postgres:
    image: postgres:15-alpine
    environment:
      POSTGRES_DB: sistema_laptops
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: 1234
    ports:
      - "5432:5432"
    volumes:
      - ./BD/sistema_laptops.sql:/docker-entrypoint-initdb.d/init.sql

  app:
    build: ./Solucion_Avance_Proyecto_Final_3
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    environment:
      DB_HOST: postgres
      DB_PORT: 5432
      DB_NAME: sistema_laptops
      DB_USER: postgres
      DB_PASS: 1234
```

**Para levantar todo:**
```bash
docker-compose up --build
# Abrir: http://localhost:8080
```

---

### Opción C — Nube gratuita para Java WAR *(para que funcione en internet)*

| Plataforma | Soporte Java WAR | Gratis | Dificultad |
|---|---|---|---|
| **Railway.app** | ✅ Sí (con Dockerfile) | ✅ Tier gratuito | ⭐⭐ Media |
| **Render.com** | ✅ Sí (con Dockerfile) | ✅ Tier gratuito | ⭐⭐ Media |
| **Fly.io** | ✅ Sí (con Dockerfile) | ✅ Tier gratuito | ⭐⭐⭐ Alta |
| **Heroku** | ✅ Sí | ❌ De pago | — |
| **Vercel** | ❌ NO | — | — |
| **Netlify** | ❌ NO | — | — |

**Recomendación:** Si la rúbrica no exige URL pública, quédate con **Opción A (Tomcat local)** para la sustentación.
Si quieres URL real: usa **Railway.app** con el `Dockerfile` de arriba.

---

---

## 🎬 QUÉ DEMOSTRAR POR CADA CRITERIO DE LA RÚBRICA

---

### 🔴 Criterio 1 — Pruebas de Software y Seguridad (2 pts)

**Taller Testing → demostrar:**
1. **Abrir** `src/test/java/servicio/CalculadoraVentaTest.java` en el IDE
2. **Ejecutar** las pruebas: `mvn test` o botón "Run Tests" en NetBeans/IntelliJ
3. **Mostrar en consola:** que todas las pruebas pasan en verde ✅
4. **Explicar el ciclo TDD:** "Primero escribí el test (Rojo) → luego implementé el método (Verde) → luego refactoricé"
5. **Señalar** que el test verifica `CalculadoraVentaService.java`

**Taller Pruebas de Seguridad → demostrar (en vivo):**

| Demo | Pasos concretos |
|---|---|
| **Demo 1: Bypass de login** | Copiar en el navegador la URL directa `localhost:8080/.../gestion_productos.jsp` sin iniciar sesión → mostrar que redirige a login gracias a `ValidarSesionFilter.java` |
| **Demo 2: SQL Injection** | En el campo usuario escribir `' OR '1'='1` y en contraseña cualquier texto → mostrar que el login falla y no hay acceso. Explicar que es por los `PreparedStatement` |
| **Demo 3: Input vacío** | Dejar campos en blanco y dar click en Ingresar → mostrar el mensaje de error. Explicar `StringUtils.isBlank()` |
| **Mostrar el reporte** | Abrir `reporte_seguridad.md` y señalar la tabla de observaciones levantadas |

---

### ✅ Criterio 2 — Despliegue del Proyecto (2 pts) — YA CUMPLE

**Demostrar:**
1. **Ejecutar en consola:** `mvn clean package` → mostrar que termina con `BUILD SUCCESS`
2. **Mostrar el archivo generado:** `target/Solucion_Avance_Proyecto_Final_3-04.war`
3. **Abrir `pom.xml`** y señalar:
   - `<packaging>war</packaging>` → "empaquetamos como WAR para Tomcat"
   - `<scope>provided</scope>` en jakartaee-api → "Tomcat ya provee las APIs, no las duplicamos"
4. **Mostrar `web.xml`** → session-timeout de 30 min configurado
5. **Mostrar `context.xml`** → path del contexto en Tomcat
6. **Frase clave para sustentar:** *"El proyecto está configurado para ser compilado con `mvn clean package` y desplegado directamente en Apache Tomcat 9, que es el servidor de aplicaciones Java estándar para proyectos WAR"*

---

### 🔴 Criterio 3 — Monitoreo del Proyecto (6 pts)

**Demostrar en vivo:**

| Demo | Pasos concretos |
|---|---|
| **Logs en tiempo real** | Levantar Tomcat → hacer una acción en la app (listar inventario) → mostrar en la consola de Tomcat los logs de Logback: `[CACHÉ HIT]` o `[BD QUERY]` con milisegundos |
| **Caché vs BD** | Hacer la misma consulta 2 veces: primera vez sale `[BD QUERY]`, segunda vez sale `[CACHÉ HIT]` con tiempo más rápido → mostrar la diferencia de ms en el log |
| **Log de errores** | Mostrar el archivo `logback.xml` → explicar niveles INFO / ERROR |
| **Endpoint de salud** | Abrir `http://localhost:8080/.../HealthServlet` → mostrar el JSON: `{"status":"UP","database":"OK"}` |
| **Plan documentado** | Abrir `plan_monitoreo.md` → señalar la tabla de métricas clave |

**Frase clave:** *"Implementamos monitoreo activo con SLF4J/Logback para rastrear eventos de la aplicación, medición de rendimiento con System.nanoTime() para comparar caché vs base de datos, y un endpoint /HealthServlet para verificar la salud del sistema en tiempo real"*

---

### 🔴 Criterio 4 — Mantenimiento del Proyecto (5 pts)

**Demostrar:**

| Demo | Pasos concretos |
|---|---|
| **Script de backup** | Abrir `scripts/backup_bd.bat` → ejecutarlo en vivo (si tienes pg_dump) → mostrar que genera `backups/backup_YYYY-MM-DD.sql` |
| **Script de purga de logs** | Mostrar `scripts/purgar_logs.bat` → explicar que elimina logs con más de 30 días |
| **Cron job configurado** | Mostrar captura/explicar cómo configurarlo en Windows Task Scheduler: `taskschd.msc` → "ejecuta backup_bd.bat todos los días a las 2 AM" |
| **BD reproducible** | Mostrar `BD/sistema_laptops.sql` → "con este script cualquiera puede recrear la BD completa" |
| **Plan documentado** | Abrir `plan_mantenimiento.md` → señalar checklist mensual y estrategia de backups |

**Frase clave:** *"El plan de mantenimiento cubre tres dimensiones: backups automatizados con pg_dump y scripts .bat, purga programada de logs con Windows Task Scheduler (equivalente a cron jobs), y documentación del ciclo de vida del sistema"*

---

### ✅ Criterio 5 — Construcción del Producto Final (3 pts) — YA CUMPLE

**Demostrar los 4 criterios en 4 minutos:**

| Criterio | Cómo demostrarlo |
|---|---|
| **1. Completa** | Navegar por TODAS las páginas: Login → Inventario → Gestión de Productos (CRUD) → Registrar Venta → Reportes (gráfico Chart.js) → descargar Excel |
| **2. Coherente** | Abrir `sistema_laptops.sql` y señalar la tabla `productos` → abrir `ProductoDAO.java` y señalar que la consulta SQL coincide exactamente con los mismos campos de la BD |
| **3. Buenas Prácticas** | Abrir `DAOFactory.java` → "aquí aplicamos el patrón Factory para desacoplar la creación de DAOs". Mostrar la interfaz `IProductoDAO` → "aplicamos el principio DIP de SOLID". Mostrar `.gitattributes` → "usamos Git para control de versiones" |
| **4. Autoría** | Señalar comentarios en español en el código que explican el TDD, los patrones y los conceptos del curso → "estos comentarios demuestran que entiendo lo que implementé" |

---

### 🎤 Criterio 6 — Sustentación Oral (2 pts)

**Las 3 claves para los 2 puntos completos:**

#### 1️⃣ Secuencia lógica (no improvises el orden)
Presenta SIEMPRE en este orden: Testing → Seguridad → Despliegue → Monitoreo → Mantenimiento → Producto Final

#### 2️⃣ Fluidez (no leas, explica)
Practica decir estas frases con tus propias palabras:
- *"Las pruebas TDD garantizan que la lógica de negocio funciona antes de integrarla"*
- *"Los PreparedStatement previenen la inyección SQL porque separan los datos del código SQL"*
- *"Logback nos permite rastrear el comportamiento de la aplicación en producción sin interrumpirla"*
- *"pg_dump es el estándar de PostgreSQL para exportar la BD completa y restaurarla en cualquier servidor"*

#### 3️⃣ Dominio — Vincula con conceptos del curso (CLAVE para el máximo)
| Cuando hablas de... | Menciona el concepto del curso |
|---|---|
| `CalculadoraVentaTest.java` | **TDD — Test Driven Development** |
| `ValidarSesionFilter.java` + `PreparedStatement` | **OWASP Top 10 — Seguridad en aplicaciones web** |
| `pom.xml` + `.war` + Tomcat | **Despliegue con Maven y servidores de aplicaciones** |
| `logback.xml` + `System.nanoTime()` | **Monitoreo de logs y rendimiento de aplicaciones** |
| `backup_bd.bat` + Task Scheduler | **Cron jobs y automatización de backups** |
| `DAOFactory.java` + `IProductoDAO` | **Patrones de diseño DAO + Factory + DIP (SOLID)** |

---

## 📊 Resumen de lo que debes mostrar en pantalla por criterio

```
Criterio 1 (Testing)    → mvn test en consola + 3 demos de seguridad en vivo + reporte_seguridad.md
Criterio 2 (Despliegue) → mvn clean package en consola + pom.xml + .war generado + app corriendo
Criterio 3 (Monitoreo)  → logs en consola Tomcat (CACHÉ HIT vs BD QUERY) + /HealthServlet JSON + plan_monitoreo.md
Criterio 4 (Mantenimiento) → backup_bd.bat + purgar_logs.bat + plan_mantenimiento.md
Criterio 5 (Producto)   → demo completa de la app (Login → CRUD → Ventas → Reportes → Excel)
Criterio 6 (Oral)       → hablar con fluidez vinculando cada parte con conceptos del curso
```

---

*Generado el 2026-06-16 | Curso Integrador I — UTP | Semana 13*
