# 🚀 Guía de Despliegue Local — App + Base de Datos

> **Stack:** Java 8 · Maven · WAR · Apache Tomcat 9 · PostgreSQL 15
> **Para que funcione en CUALQUIER PC siguiendo estos pasos**

---

## ⚙️ Configuración actual de la conexión

El archivo `Conexion.java` tiene estos datos fijos:
```
Host:     localhost
Puerto:   5432
BD:       sistema_laptops
Usuario:  postgres
Contraseña: root
```
> ⚠️ **La PC donde se despliegue debe tener PostgreSQL con exactamente esa contraseña (`root`) y esa base de datos.**

---

## 📋 PRE-REQUISITOS (instalar una sola vez)

| Herramienta | Versión | Dónde descargar |
|---|---|---|
| **Java JDK 8** | 1.8+ | https://adoptium.net/ |
| **Maven** | 3.6+ | https://maven.apache.org/download.cgi |
| **Apache Tomcat 9** | 9.0.x | https://tomcat.apache.org/download-90.cgi |
| **PostgreSQL** | 15 | https://www.postgresql.org/download/ |

---

## PASO 1 — Instalar y configurar PostgreSQL

### 1.1 Instalar PostgreSQL
- Descargar el instalador de https://www.postgresql.org/download/windows/
- Durante la instalación:
  - **Usuario:** `postgres` *(dejarlo por defecto)*
  - **Contraseña:** `root` ← **MUY IMPORTANTE: debe ser exactamente `root`**
  - **Puerto:** `5432` *(dejarlo por defecto)*

### 1.2 Crear la base de datos

**Opción A — Usando pgAdmin (interfaz gráfica):**
```
1. Abrir pgAdmin 4
2. Click derecho en "Databases" → "Create" → "Database..."
3. Nombre: sistema_laptops → Guardar
4. Click derecho en "sistema_laptops" → "Query Tool"
5. Abrir el archivo: BD/sistema_laptops.sql
6. Ejecutar todo (F5 o botón "Run")
```

**Opción B — Usando terminal (psql):**
```bash
# Abrir PowerShell o CMD y ejecutar:
psql -U postgres -c "CREATE DATABASE sistema_laptops;"
psql -U postgres -d sistema_laptops -f "ruta\BD\sistema_laptops.sql"
```

### 1.3 Verificar que la BD está lista
```sql
-- En pgAdmin → Query Tool de sistema_laptops:
SELECT * FROM productos;
SELECT * FROM usuarios;
-- Si devuelve filas, la BD está correctamente inicializada ✅
```

---

## PASO 2 — Compilar el proyecto con Maven

### 2.1 Abrir una terminal en la carpeta del proyecto
```
Ruta: Proyecto-con-Solucion-3/Solucion_Avance_Proyecto_Final_3/
```

### 2.2 Ejecutar el comando de compilación
```bash
mvn clean package -DskipTests
```

### 2.3 Verificar que se generó el .war
```
✅ Resultado esperado:
   [INFO] BUILD SUCCESS
   
   Archivo generado:
   target/Solucion_Avance_Proyecto_Final_3-04.war
```

> **¿Error de Maven no encontrado?** Verificar que `mvn` está en el PATH del sistema,
> o abrir el proyecto directamente en NetBeans/IntelliJ y usar "Clean and Build".

---

## PASO 3 — Desplegar en Apache Tomcat 9

### 3.1 Instalar Tomcat 9
- Descargar "Core → zip" de https://tomcat.apache.org/download-90.cgi
- Descomprimir en una carpeta (ej. `C:\tomcat9\`)

### 3.2 Copiar el .war a Tomcat

```
Origen:  Solucion_Avance_Proyecto_Final_3/target/Solucion_Avance_Proyecto_Final_3-04.war
Destino: C:\tomcat9\webapps\Solucion_Avance_Proyecto_Final_3-04.war
```

### 3.3 Iniciar Tomcat
```bash
# Windows: ejecutar
C:\tomcat9\bin\startup.bat
```

Deberías ver en la consola:
```
INFO: Server startup in [X] milliseconds
```

### 3.4 Acceder a la aplicación
```
URL: http://localhost:8080/Solucion_Avance_Proyecto_Final_3-04/
```

> **Si Tomcat arroja error en el puerto 8080:** Otro proceso está usando ese puerto.
> Cambiar en `C:\tomcat9\conf\server.xml` → `<Connector port="8080"` → cambiar a `8090`.

---

## PASO 4 — Verificar que la app conecta con la BD

### 4.1 Login en la aplicación
Intentar iniciar sesión. Si los usuarios están cargados desde `sistema_laptops.sql`, el login debería funcionar.

### 4.2 Ver los logs de conexión en Tomcat
En la consola de Tomcat buscar:
```
✅ Éxito:  INFO  Conexion - Conexión establecida exitosamente
❌ Error:  ERROR Conexion - Error de conexión a la base de datos
```

### 4.3 Si hay error de conexión — Checklist
```
□ ¿PostgreSQL está corriendo? → Servicios de Windows → "postgresql-x64-15" → Estado: En ejecución
□ ¿La contraseña es exactamente "root"? → pgAdmin → propiedades del usuario postgres
□ ¿El puerto es 5432? → postgresql.conf o pg_hba.conf
□ ¿La BD se llama exactamente "sistema_laptops"? → sin espacios, sin mayúsculas
□ ¿Se ejecutó el script sistema_laptops.sql? → SELECT * FROM productos; debe dar resultados
```

---

## 📦 Checklist de entrega — Lo que debes presentar

```
✅ 1. Demostrar: mvn clean package → BUILD SUCCESS (en consola)
✅ 2. Mostrar el archivo: target/Solucion_Avance_Proyecto_Final_3-04.war
✅ 3. Mostrar: Tomcat corriendo con la app desplegada
✅ 4. Abrir en navegador: http://localhost:8080/Solucion_Avance_Proyecto_Final_3-04/
✅ 5. Hacer login exitoso → demuestra que la BD está conectada y funcionando
✅ 6. Mostrar pom.xml → señalar <packaging>war</packaging>
✅ 7. Mostrar context.xml y web.xml → "configuración del servidor"
```

---

## 🗣️ Frase para la sustentación del Criterio 2

> *"El proyecto está construido con Maven, utilizando empaquetamiento WAR (`<packaging>war</packaging>`)
> que es el estándar para aplicaciones Java EE desplegadas en servidores de aplicaciones.
> Se despliega en Apache Tomcat 9, configurado mediante `context.xml` para el path del contexto
> y `web.xml` para el session-timeout. El driver JDBC de PostgreSQL está declarado como
> dependencia en el `pom.xml`, lo que permite la conexión a la base de datos de forma automática
> al momento del despliegue."*

---

## 🧩 Diagrama del despliegue

```
  [ Cualquier PC ]
  ┌─────────────────────────────────────────┐
  │                                         │
  │  ┌──────────────────┐                   │
  │  │  Apache Tomcat 9 │  puerto 8080      │
  │  │                  │                   │
  │  │  📦 .war         │──────────────┐    │
  │  │  (App Java/JSP)  │              │    │
  │  └──────────────────┘              │    │
  │                                    ▼    │
  │  ┌──────────────────────────────────┐   │
  │  │  PostgreSQL 15   │  puerto 5432  │   │
  │  │  BD: sistema_laptops             │   │
  │  │  User: postgres / Pass: root     │   │
  │  └──────────────────────────────────┘   │
  │                                         │
  └─────────────────────────────────────────┘
        ▲
        │  http://localhost:8080/...
  [ Navegador ]
```

---

*Generado el 2026-06-16 | Curso Integrador I — UTP | Semana 13*
