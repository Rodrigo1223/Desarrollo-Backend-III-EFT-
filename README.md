Proyecto Backend III - EFT (Evaluación Final Transversal) - Rodrigo Olavarría
1. Contexto y Objetivos de la Evaluación (Semana 9)
   Este proyecto corresponde a la Evaluación Final Transversal (EFT) de la asignatura Desarrollo Backend III (PBY2203). El objetivo principal es consolidar un ecosistema de microservicios robusto y producción-ready, integrando los siguientes pilares técnicos requeridos en la pauta:

Procesamiento Batch Avanzado: Implementación del servicio de migración y procesamiento por lotes con Spring Batch, incorporando un parser multi-formato (DateParserUtil) para resolver inconsistencias de fechas legadas y políticas de skip para garantizar la tolerancia a fallos.

Arquitectura BFF (Backend For Frontend): Desarrollo de tres microservicios BFF especializados (BFF Web, BFF Móvil y BFF Cajero) que encapsulan y adaptan la lógica de negocio consumiendo de forma dinámica los backends (cero datos mock o estáticos).

Infraestructura Cloud y Descubrimiento: Orquestación centralizada mediante docker-compose.yml, integrando Eureka Server (descubrimiento), Config Server (configuración centralizada) y Kafka para la mensajería asíncrona.

2. Definición de la Arquitectura
   Patrón de Canales BFF: Se separan las interfaces de consumo según el canal del usuario final (Web, Móvil y Cajero Automático), asegurando que cada cliente reciba únicamente los datos necesarios transformados dinámicamente.

Procesamiento de Lotes (ETL/Batch): El microservicio de migración procesa archivos financieros legados (estados_financieros_anuales.csv, intereses_trimestrales.csv, movimientos_financieros_diarios.csv), transformando y persistiendo la información de manera resiliente.

3. Estructura de Microservicios de la Solución
   El repositorio se encuentra organizado en módulos independientes dentro de la misma solución:

Exp2_S6_Eureka_Server/: Servidor de descubrimiento de microservicios (Puerto 8761).

Exp2_S6_Config_Server/: Servidor de configuración centralizada (Puerto 8888).

Exp2_S6_Banco_API/: Microservicio central de gestión bancaria (Puerto 8081).

Exp2_S6_Cliente_API/: Microservicio de gestión de clientes (Puerto 8082).

Exp3_S9_Migracion_Batch/: Módulo de Spring Batch para la migración de datos financieros y manejo de fechas legadas (Puerto 8083).

Exp3_S9_BFF_Web/: Backend for Frontend orientado al canal Web (Puerto 8084).

Exp3_S9_BFF_Movil/: Backend for Frontend orientado a dispositivos móviles (Puerto 8085).

Exp3_S9_BFF_Cajero/: Backend for Frontend orientado a cajeros automáticos con operaciones dinámicas de saldo y retiro (Puerto 8086).

4. Instrucciones de Ejecución y Despliegue
   Abre una terminal y ubícate en la carpeta raíz del proyecto donde se encuentra el archivo de orquestación:

Bash
cd Exp3_S9_EFT_Rodrigo_Olavarria
Levanta la infraestructura completa y todos los microservicios utilizando Docker Compose:

Bash
docker-compose up --build -d
Verifica que los servicios se encuentren activos consultando el panel de Eureka Server en: http://localhost:8761.