# Guía de Despliegue en Entorno Cloud (AWS / Docker) - EFT Backend III
**Estudiante:** Rodrigo Olavarría

---

## 1. Arquitectura de Despliegue
La solución completa se encuentra contenerizada mediante **Docker** y orquestada con **Docker Compose**, permitiendo un despliegue homogéneo tanto en entornos de desarrollo locales como en servidores en la nube (Instancias AWS EC2 con Amazon Linux).

---

## 2. Pasos para el Despliegue en la Nube (AWS EC2)

1. **Aprovisionamiento de la Instancia:**
    - Iniciar una instancia EC2 (Amazon Linux / Ubuntu) asegurando la apertura de los puertos requeridos en el Grupo de Seguridad (`8761` para Eureka, `8081`-`8082` para APIs, `8084`-`8086` para los BFFs, y los puertos de infraestructura).

2. **Clonación y Configuración del Repositorio:**
    - Conectarse a la instancia mediante SSH.
    - Clonar el repositorio del proyecto y ubicarse en la carpeta raíz donde reside el archivo `docker-compose.yml`.

3. **Ejecución de Contenedores:**
    - Construir y levantar el ecosistema completo ejecutando:
      ```bash
      docker-compose up --build -d
      ```

4. **Verificación de Contenedores Activos:**
    - Validar el estado de los servicios ejecutando:
      ```bash
      docker ps
      ```

---

## 3. Resumen de Evidencias Gráficas del Despliegue
Las capturas oficiales de validación se encuentran almacenadas en la carpeta de evidencias del proyecto:
- **`evidencia_eureka_dashboard.png`**: Muestra el registro exitoso y estado `UP` de los microservicios en el servidor Eureka[cite: 4].
- **`evidencia_bff_web_dashboard.png`**: Validación Postman de la respuesta del BFF Web (Puerto 8084)[cite: 6].
- **`evidencia_bff_movil_resumen.png`**: Validación Postman de la respuesta del BFF Móvil (Puerto 8085)[cite: 7].
- **`evidencia_bff_cajero_saldo.png`**: Consulta de saldo en el cajero físico (Puerto 8086)[cite: 3].
- **`evidencia_bff_cajero_retiro.png`**: Transacción de retiro exitosa en el cajero físico (Puerto 8086)[cite: 2].
- **`evidencia_migracion_batch.png`**: Consola de Spring Batch mostrando el procesamiento por bloques y sentencias de inserción JPA[cite: 1].