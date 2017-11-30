# Spring Boot Simple Project

Spring Boot演示项目

## Build

```bash
~$ gradlew clean build
```

## Run

```bash
~$ gradlew bootRun
```

## Feature List
- [x] Spring Boot Data JPA
- [x] Spring Boot Data Mongodb
- [x] Spring Boot Tests
- [x] Spring Boot RESTful API with spring-hateoas
- [x] Spring Boot Cache
- [x] Spring Security OAuth2
- [ ] Spring AMQP
- [ ] Customize auto-configuration
- [ ] Spring web-flux (Spring 5.x or Spring Boot 2.x)

上面的功能部分未完成，会在后续过程中补全，有些功能可能受Spring Boot版本影响。

## Other

keytool生成证书：

```bash
keytool -genkeypair -alias [alias] -keyalg RSA -keypass [password] -keystore keystore.jks -storepass [password]
```

获取PUBLIC KEY（建议在Linux环境运行该命令）：

```bash
keytool -list -rfc --keystore keystore.jks | openssl x509 -inform pem -pubkey
```

## OAuth2

后续会专门开贴讨论OAuth2.0的集成，敬请期待...
