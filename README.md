# springboot-otel-demo

```sh
podman compose up --build 
````

Swagger for the API: http://localhost:8080/swagger-ui.html
Swagger for the kafka listener: http://localhost:8081/swagger-ui.html
Jaeger: http://localhost:16686
Grafana: http://localhost:3000

![sketch](./sketch.png)

- a payment endpoint
- kafka topic
- b kafka listener
- b jdbc
- postgres
- otel jar + otel config 
- docker compose
- k8s?
