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

## PASO 2 — Compilar el proyecto y generar el .WAR

### 2.1 Generar el archivo mediante el IDE (Ej. NetBeans)
1. Abrir el proyecto en el IDE (por ejemplo, Apache NetBeans).
2. Dar clic derecho sobre el nombre del proyecto y seleccionar **"Clean and Build"** (Limpiar y Construir).
3. Esperar a que termine el proceso. Esto compilará todo el código Java y empaquetará el proyecto.

### 2.2 Ubicar el archivo .war generado
1. En NetBeans, ir a la pestaña **"Files"** (Archivos) que está junto a la pestaña Projects.
2. Desplegar la carpeta del proyecto y buscar la subcarpeta llamada `target`.
3. Dentro de la carpeta `target` verás que se ha creado el archivo:
   `Solucion_Avance_Proyecto_Final_3-04.war`

Este archivo `.war` (Web Application Archive) es el paquete final de tu proyecto. Cuando copies este archivo a la carpeta de Tomcat, el servidor automáticamente lo reconocerá, lo descomprimirá y lo pondrá en ejecución.

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

> **🛠️ Si la página no carga o da error:**
> 1. **¿El servidor está apagado?** Verifica en NetBeans (pestaña "Services" -> "Servers") que Apache Tomcat esté corriendo. Si tiene un ícono de detenido, haz clic derecho y selecciona **"Start"** (Iniciar).
> 2. **¿Error de puerto ocupado (8080)?** Si Tomcat no puede iniciar porque otro proceso usa el puerto 8080, cámbialo. Abre `C:\tomcat9\conf\server.xml`, busca `<Connector port="8080"` y cámbialo a `8090`. *(Si haces esto, la URL para entrar será con 8090)*.

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

## 📦 Verificación Final del Despliegue

```
✅ 1. Compilación exitosa: "Clean and Build" en el IDE finalizado sin errores.
✅ 2. Generación del artefacto: El archivo .war se encuentra en la subcarpeta target/.
✅ 3. Servidor activo: Apache Tomcat está en ejecución con la aplicación desplegada.
✅ 4. Interfaz accesible: La URL de localhost responde correctamente en el navegador.
✅ 5. Conexión a BD verificada: El inicio de sesión en el sistema es exitoso.
✅ 6. Dependencias validadas: pom.xml contiene el empaquetado WAR correcto.
✅ 7. Configuración de contexto validada: context.xml y web.xml leídos correctamente.
```



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

