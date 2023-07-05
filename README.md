# Exemplo de aplicação com spring-cloud-stream

<!-- TOC -->
* [Exemplo de aplicação com spring-cloud-stream](#exemplo-de-aplicação-com-spring-cloud-stream)
  * [Executando](#executando)
    * [Criando a infraestrutura do Kafka](#criando-a-infraestrutura-do-kafka)
    * [Acessando os recursos](#acessando-os-recursos)
      * [Schema Registry](#schema-registry)
      * [Control Center](#control-center)
    * [Executando a aplicação](#executando-a-aplicação)
      * [Localmente](#localmente)
      * [Docker](#docker)
<!-- TOC -->

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

```shell
docker compose up --build
```

ou

```shell
docker compose up --build -d
```

(para liberar o terminal)

### Acessando os recursos

#### Schema Registry

```html
http://localhost:8081
```

#### Control Center

```html
http://localhost:9021
```

### Executando a aplicação

#### Localmente
Execute pela IDE ou pelo terminal através do Gradle.

```shell
./gradlew bootRun
```

Para publicar um evento:

```html
http://localhost:8080
```

#### Docker

Recomendado utilizar como JDK Version Manager o projeto [Jabba](https://github.com/Jabba-Team/jabba):

```shell
jabba use openjdk@17
```

Verificar versão do JDK

```shell
java -version
```

Gerar artefato da aplicação

```shell
./gradlew clean build
```

Gerar imagem docker

```shell
docker build . -t spring-cloud-stream-example:latest
```

Descomentar o serviço `spring-cloud-stream-example` do arquivo [docker-compose.yml](docker/docker-compose.yml) e
executar

```shell
docker compose up --build spring-cloud-stream-example
```

Para publicar um evento:

```html
http://localhost:8082
```