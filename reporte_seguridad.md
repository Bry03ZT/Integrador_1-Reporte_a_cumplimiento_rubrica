# Reporte de Pruebas de Seguridad
# Sistema de Gestión de Laptops — Solucion_Avance_Proyecto_Final_3

**Fecha de ejecución:** 2026-06-16
**Responsable:** Bryan
**Curso:** Integrador I — UTP | Ciclo Marzo 2026
**Versión del sistema:** 04 (WAR desplegado en Apache Tomcat 9)

---

## 1. Resumen Ejecutivo

Se realizaron pruebas de seguridad manuales sobre la aplicación web de gestión de laptops.
Las pruebas cubrieron las principales vulnerabilidades del **OWASP Top 10** aplicables al
contexto de una aplicación Java EE con JSP, Servlets y PostgreSQL.
Se identificaron y resolvieron **3 observaciones** antes de la entrega final del proyecto.

| Categoría de Prueba | Cantidad de Pruebas | Vulnerabilidades Encontradas | Estado Final |
|---|---|---|---|
| Autenticación y Control de Sesión | 2 | 1 | ✅ Resuelto |
| Inyección SQL (SQLi) | 2 | 1 | ✅ Resuelto |
| Validación de Entradas | 2 | 1 | ✅ Resuelto |
| **TOTAL** | **6** | **3** | **✅ Todas resueltas** |

---

## 2. Pruebas de Autenticación y Control de Sesión

### 🔐 PRUEBA-AUTH-01: Acceso Directo a Página Privada sin Sesión (Bypass de Login)

| Campo | Detalle |
|---|---|
| **Objetivo** | Verificar que un usuario no autenticado no puede acceder directamente a páginas privadas |
| **Herramienta** | Navegador web (Google Chrome / Firefox) |
| **Vulnerabilidad OWASP** | A01:2021 – Broken Access Control |
| **Pasos realizados** | 1. Abrir nueva ventana de incógnito (sin sesión activa) <br> 2. Escribir directamente en la URL: `http://localhost:8080/Solucion_Avance_Proyecto_Final_3/gestion_productos.jsp` <br> 3. Presionar Enter |
| **Resultado esperado** | Redirección automática a `index.jsp` (pantalla de login) |
| **Resultado obtenido** | ✅ El filtro `ValidarSesionFilter.java` interceptó la solicitud y redirigió a login |
| **Observación levantada** | **OBS-01:** Sin el filtro, cualquier usuario podría acceder a páginas privadas copiando la URL |
| **Solución aplicada** | Implementado `ValidarSesionFilter.java` que protege los paths: `/gestion_productos.jsp`, `/inventario.jsp`, `/registrar_venta.jsp`, `/reportes.jsp` |
| **Estado** | ✅ RESUELTO |

---

### 🔐 PRUEBA-AUTH-02: Acceso Directo a Módulo de Reportes sin Sesión

| Campo | Detalle |
|---|---|
| **Objetivo** | Verificar que el módulo de reportes (datos sensibles de ventas) también está protegido |
| **Herramienta** | Navegador web |
| **Vulnerabilidad OWASP** | A01:2021 – Broken Access Control |
| **Pasos realizados** | 1. Abrir nueva ventana de incógnito <br> 2. Escribir en la URL: `http://localhost:8080/Solucion_Avance_Proyecto_Final_3/reportes.jsp` <br> 3. Presionar Enter |
| **Resultado esperado** | Redirección automática a `index.jsp` |
| **Resultado obtenido** | ✅ El filtro `ValidarSesionFilter.java` bloqueó el acceso y redirigió a login |
| **Observación levantada** | Ninguna adicional — el filtro ya cubre este path |
| **Solución aplicada** | Misma solución que OBS-01: `ValidarSesionFilter.java` |
| **Estado** | ✅ RESUELTO |

---

## 3. Pruebas de Inyección SQL (SQL Injection)

### 💉 PRUEBA-SQLi-01: Inyección SQL Clásica en Campo Usuario del Login

| Campo | Detalle |
|---|---|
| **Objetivo** | Verificar que el campo de usuario del formulario de login no es vulnerable a SQL Injection |
| **Herramienta** | Formulario de login de la aplicación |
| **Vulnerabilidad OWASP** | A03:2021 – Injection |
| **Pasos realizados** | 1. Abrir la pantalla de login: `http://localhost:8080/Solucion_Avance_Proyecto_Final_3/` <br> 2. En el campo **Usuario** escribir: `' OR '1'='1` <br> 3. En el campo **Contraseña** escribir: `cualquier_cosa` <br> 4. Click en "Ingresar" |
| **Resultado esperado** | El login falla y no se accede al sistema |
| **Resultado obtenido** | ✅ El login falló correctamente. El `PreparedStatement` en `UsuarioDAO` trató el input como texto literal, no como código SQL |
| **Observación levantada** | **OBS-02:** Si se usara concatenación de strings (`"SELECT * FROM usuarios WHERE usuario='" + usuario + "'"`) habría acceso no autorizado |
| **Solución aplicada** | Todos los DAOs usan `PreparedStatement` con parámetros posicionales (`?`), lo que previene la inyección: `pst.setString(1, usuario)` |
| **Estado** | ✅ RESUELTO |

---

### 💉 PRUEBA-SQLi-02: Inyección SQL en Campo Contraseña del Login

| Campo | Detalle |
|---|---|
| **Objetivo** | Verificar que el campo contraseña tampoco es vulnerable a inyección SQL |
| **Herramienta** | Formulario de login de la aplicación |
| **Vulnerabilidad OWASP** | A03:2021 – Injection |
| **Pasos realizados** | 1. En el campo **Usuario** escribir: `admin@tech.com` <br> 2. En el campo **Contraseña** escribir: `' OR '1'='1' --` <br> 3. Click en "Ingresar" |
| **Resultado esperado** | El login falla — no se permite el acceso |
| **Resultado obtenido** | ✅ El login falló correctamente. El payload fue tratado como string literal por el `PreparedStatement` |
| **Observación levantada** | Ninguna adicional — la protección ya aplica a ambos campos |
| **Solución aplicada** | `PreparedStatement` con `pst.setString(2, pass)` en `UsuarioDAO` |
| **Estado** | ✅ RESUELTO |

---

## 4. Pruebas de Validación de Entradas

### 📋 PRUEBA-VAL-01: Envío del Formulario de Login con Campos Vacíos

| Campo | Detalle |
|---|---|
| **Objetivo** | Verificar que el sistema valida entradas vacías antes de procesar la solicitud |
| **Herramienta** | Formulario de login de la aplicación |
| **Vulnerabilidad OWASP** | A03:2021 – Injection / A04:2021 – Insecure Design |
| **Pasos realizados** | 1. Dejar el campo **Usuario** completamente en blanco <br> 2. Dejar el campo **Contraseña** completamente en blanco <br> 3. Click en "Ingresar" |
| **Resultado esperado** | El sistema muestra un mensaje de error sin procesar la consulta a la BD |
| **Resultado obtenido** | ✅ El `LoginServlet` detectó los campos vacíos usando `StringUtils.isBlank()` de Apache Commons Lang3 y retornó mensaje de error |
| **Observación levantada** | **OBS-03:** Sin validación, se ejecutarían queries innecesarios a la BD con strings vacíos |
| **Solución aplicada** | Validación con `StringUtils.isBlank(usuario)` en `LoginServlet.java` antes de cualquier consulta a BD |
| **Estado** | ✅ RESUELTO |

---

### 📋 PRUEBA-VAL-02: Envío del Formulario con Solo Espacios en Blanco

| Campo | Detalle |
|---|---|
| **Objetivo** | Verificar que el sistema también rechaza entradas que contienen solo espacios (bypass de validación simple) |
| **Herramienta** | Formulario de login de la aplicación |
| **Vulnerabilidad OWASP** | A03:2021 – Injection |
| **Pasos realizados** | 1. En el campo **Usuario** escribir varios espacios: `   ` <br> 2. En el campo **Contraseña** escribir varios espacios: `   ` <br> 3. Click en "Ingresar" |
| **Resultado esperado** | El sistema rechaza la entrada — no consulta la BD |
| **Resultado obtenido** | ✅ `StringUtils.isBlank("   ")` devuelve `true` (considera los espacios como vacío), el servlet rechaza el formulario |
| **Observación levantada** | Ninguna adicional — `isBlank()` ya cubre este caso (diferencia clave con `isEmpty()`) |
| **Solución aplicada** | Uso correcto de `StringUtils.isBlank()` en lugar de `isEmpty()` — `isBlank` también detecta strings de solo espacios |
| **Estado** | ✅ RESUELTO |

---

## 5. Tabla de Observaciones Levantadas y Resueltas

| ID | Tipo | Vulnerabilidad Encontrada | Archivo Afectado | Solución Implementada | Estado |
|---|---|---|---|---|---|
| OBS-01 | Autenticación | Acceso directo a páginas JSP privadas sin sesión | `gestion_productos.jsp`, `reportes.jsp`, etc. | `ValidarSesionFilter.java` protege todos los paths privados | ✅ Resuelto |
| OBS-02 | Inyección SQL | Posibilidad de SQL Injection en formulario de login | `UsuarioDAO.java` | `PreparedStatement` con parámetros `?` tipados | ✅ Resuelto |
| OBS-03 | Validación | Envío de formulario con campos vacíos sin validación | `LoginServlet.java` | `StringUtils.isBlank()` antes de consultar BD | ✅ Resuelto |

---

## 6. Conclusión

El sistema fue evaluado frente a **6 pruebas de seguridad** cubriendo las categorías más
relevantes del **OWASP Top 10** para aplicaciones Java EE. Se encontraron y resolvieron
**3 observaciones** durante el desarrollo. Al momento de la entrega, el sistema no presenta
vulnerabilidades conocidas en los aspectos evaluados.

Las medidas de seguridad implementadas son:
- ✅ `ValidarSesionFilter.java` → Control de acceso centralizado
- ✅ `PreparedStatement` en todos los DAOs → Prevención de SQL Injection
- ✅ `StringUtils.isBlank()` → Validación robusta de entradas en formularios
