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
- Kubernetes (kubectl + minikube/minishift)

# Lanciare l'applicazione
Seguire i seguenti passi in ordine:
- eseguire ./buil_all_projects.sh per la costruzione di tutti i progetti
- eseguire ./run_application.sh per lanciare tutti i container e l'applicazione

Una volta pronta è possibile testare l'applicazione tramite Swagger-UI
  - http://localhost:8080/swagger-ui.html (si accede ai vari servizi dal menu in alto a destra)
  
- Non appena l'applicazione sarà su (un paio di minuti) è possibile lanciare, con il comando ./run_client.sh, un client Rest che        simulerà un carico di richieste da diversi utenti fittizi

- eseguire ./stop_application.sh per stoppare tutti i container
- eseguire ./delete_application se si vogliono cancellare i container creati

# Lanciare i test unitari
- eseguire ./run_unit_tests.sh per eseguire i test unitari

# Lanciare i test di integrazione
Prima di poter lanciare con successo i test di integrazione occorre esportare una variabile d'ambiente 

$DOCKER_HOST_IP = [INDIRIZZO-IP-DELLA-PROPRIA-MACCHINA]
- eseguire ./run_integration_tests.sh per eseguire i testi di integrazione
- eseguire ./stop_integration_tests.sh per stoppare i container che sono stati avviati per eseguire i test

# Lanciare l'applicazione su Kubernetes 
## Installazione minikube
Eseguire:
- curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64 && chmod +x minikube
- sudo install minikube /usr/local/bin
## Installazione helm
Helm è un package manager per kubernetes che servirà per installare istio. Per l'installazione eseguire:
- curl -LO https://git.io/get_helm.sh
- chmod 700 get_helm.sh
- ./get_helm.sh
## Installazione istio
Andare nella cartella Kubernetes del progetto e lanciare:
- curl -L https://git.io/getLatestIstio | ISTIO_VERSION=1.2.5 sh -
- cd istio-1.2.5
- export PATH=$PWD/bin:$PATH
## Lancio dell'applicazione
Per prima cosa avviare minikube:
- minikube start

Poi posizionarsi nella home del progetto ed eseguire:
- ./build-kubernetes-image.sh per fare la build dei progetti e costruire le immagini docker dei servizi
- cd Kubernetes
- ./script/setup-grafana-dashboard.sh (per aggiungere la dashboard per l'A/B testing)
- ./script/istio-setup.sh (attendere che sia tutto running)
- ./script/run-services.sh per lanciare mysql, zookeeper e kafka
- ./script/run-monitoring-service.sh per lanciare prometheus, grafana, jaeger, kiali
- ./script/run-application.sh per lanciare l'applicazione.

Per testare l'applicazione occorre recuperare l'ip del cluster ottenibile con il comando:
- minikube ip

La porta su cui gira l'applicazione è ricavabile dal comando:
kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].nodePort}'

Ora è possibile contattare i vari i servizi ad esempio tramite swagger a https://{MINIKUBE_IP}:{APP_PORT}/{NOME-SERVIZIO}/swagger-ui.html

I servizi di monitoraggio girano sullo stesso ip ma su porte differenti; per ricavare le porte eseguire il comando:
kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="{NOME_PORTA}")].nodePort}',
dove in {NOME_PORTA} va messo il nome della porta del servizio che si vuole raggiungere; i nome delle porte sono:
- https-tracing
- https-grafana
- https-prometheus
- https-kiali

Lanciare il client che simula richieste da parte dei client con:
- ./run-client.sh

Per stoppare l'applicazione lanciare:
- ./script/stop-application.sh
- ./script/stop-services.sh
- ./script/stop-monitoring-service.sh
- minikube stop (o delete)

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
  
  Una volta lanciata l'applicazione è possibile tracciare l'andamento delle richieste ricevute tramite l'interfaccia grafica di Jaeger
  raggiungibile all'indirizzo http://localhost:16686
  
  ## Buisness metrics (Docker-Compose)
  E' possibile consultare alcune metriche di buisness ritenute rilevanti attraverso Graphana (http://localhost:3000). L'username e la     password sono admin/admin. Graphana prende i dati da Prometheus che è possibile consultare all'indirizzo http://localhost:9090.         Prometheus a sua volta interroga i vari servizi che espongono diverse metriche al path /actuator/prometheus. 
   ## Buisness metrics (Kubernetes-istio)
   E' possibile consultare alcune metriche di buisness ritenute rilevanti attraverso Graphana (http://https://{MINIKUBE_IP}:{APP_PORT}).
   Consultare la dashboard "Buisness" per tenere traccia delle metriche di buisness registrate dal sistema.

