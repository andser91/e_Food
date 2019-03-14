# e_Food
Implementazione di un'applicazione a microservizi.
Contribuiscono a questo repository:
- Elena Bernardi https://github.com/ELEnaBernardi
- Debora Benedetto https://github.com/b95debora

# Requisiti
- Java
- Gradle
- Docker
- Docker-compose

# Lanciare l'applicazione
Seguire i seguenti passi in ordine:
- eseguire ./buil_all_projects.sh per la costruzione di tutti i progetti
- eseguire ./run_application.sh per lanciare tutti i container e l'applicazione

Una volta pronta è possibile testa l'applicazione tramite Swagger-UI
  - http://localhost:8082/swagger-ui.html per il servizio customer
  - http://localhost:8083/swagger-ui.html per il servizio order
  - http://localhost:8084/swagger-ui.html per il servizio restaurant

- eseguire ./stop_application.sh per stoppare tutti i container
- eseguire ./delete_application se si vogliono cancellare i container creati

# Lanciare i test unitari
- eseguire ./run_unit_tests.sh per eseguire i test unitari

# Lanciare i test di integrazione
- eseguire ./run_integration_test.sh per eseguire i testi di integrazione
- eseguire ./stop_integration_test.sh per stoppare i container che sono stati avviati per eseguire i test
