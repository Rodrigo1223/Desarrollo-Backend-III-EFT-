# Guía de Ejecución y Pruebas - Evaluación Final Transversal (EFT)
**Asignatura:** Desarrollo Backend III (PBY2203)  
**Estudiante:** Rodrigo Olavarría

---

## 📋 Prerrequisitos del Entorno
Antes de ejecutar la solución, asegúrate de tener instalado en tu equipo:
- [Docker](https://www.docker.com/) y **Docker Compose** en ejecución.
- [Postman](https://www.postman.com/) (o herramienta similar) para realizar las pruebas de los canales BFF.

---

## 🚀 1. Despliegue de la Infraestructura y Microservicios
El proyecto utiliza un entorno orquestado con Docker Compose para levantar todos los contenedores de forma automatizada (Eureka, Config Server, APIs de Backend, Módulo Batch y los tres BFFs).

1. Abre una terminal de comandos en la carpeta raíz del proyecto (donde se encuentra el archivo `docker-compose.yml`).
2. Ejecuta el siguiente comando para construir las imágenes y levantar los servicios en segundo plano:
   ```bash
   docker-compose up --build -d