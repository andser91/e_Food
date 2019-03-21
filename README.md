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

Una volta pronta è possibile testare l'applicazione tramite Swagger-UI
  - http://localhost:8082/swagger-ui.html per il servizio customer
  - http://localhost:8083/swagger-ui.html per il servizio order
  - http://localhost:8084/swagger-ui.html per il servizio restaurant


- eseguire ./stop_application.sh per stoppare tutti i container
- eseguire ./delete_application se si vogliono cancellare i container creati

# Lanciare i test unitari
- eseguire ./run_unit_tests.sh per eseguire i test unitari

# Lanciare i test di integrazione
- eseguire ./run_integration_tests.sh per eseguire i testi di integrazione
- eseguire ./stop_integration_tests.sh per stoppare i container che sono stati avviati per eseguire i test

# Monitoraggio
  ## Distributed Tracing
  Spring Cloud Sleuth instrumenta il codice per registrare ogni richiesta in entrata/uscita da sistemi esterni (http, grpc,
  messaging...); ognuna di queste 
  richieste è uno "SPAN". Un insieme di richieste generate a partire da una richiesta esterna al sistema viene detta "TRACE".
  A ciascuno span è associato uno spanId (un id univoco per lo span) ed un traceId (un id univoco per la traccia), il nome
  dell'applicazione ed un booleano per indicare se tale informazione è esportata verso un sistema centralizzato o no.
  
  Per ogni span sono registrati i tempo di inizio e di fine e viene generata una riga nei log dell'applicazione.
  
  Attraverso la dipendenza brave.opentracing il formato adottato da Sleuth è conforme a opentracing e quindi integrabile con altre
  tecnologie.
  
  Jaeger è il server centralizzato che raccoglie,memorizza e visualizza le informazioni ricevute dalle applicazioni.
  
  ![architecture](https://user-images.githubusercontent.com/27349928/54783239-29c63c80-4c21-11e9-9ff2-6a866c845888.png)
  
  Quando uno span termina viene inviato dall'applicazione ad un Jaeger-agent (via http o udp) che lo trasmette ad un Jaeger-collector;
  questo lo memorizza in un database; il componente Jaeger-query ha lo scopo di recuperare i record dal db e visualizzarli nella
  Jaeger-UI.
