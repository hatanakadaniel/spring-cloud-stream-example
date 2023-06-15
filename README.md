# Exemplo de aplicação com spring-cloud-stream

## Executando

### Criando a infraestrutura do Kafka

Os recursos da infraestrutura:

- Kafka Zookeeper
- Kafka Broker
- Schema Registry
- Control Center

Navegue até a pasta `./docker/ssl/` e execute o arquivo `create-certificate.sh`

```shell
cd docker/ssl/
```

```shell
./create-certificate.sh
```

A execução desse comando irá gerar os certificados necessários para configurar o Kafka com SSL.

Após a execução voltar para a pasta `./docker/` e subir a infraestrutura configrada com Docker Compose e descrita no
arquivo `docker-compose.yml`.

```shell
cd ..
```

`docker compose up --build` ou `docker compose up --build -d` (para liberar o terminal)

### Executando a aplicação

Execute pela IDE ou pelo terminal através do Gradle.

```shell
./gradlew bootRun
```