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

## DB Schema

应用启动后会自动创建数据库表结构，只需要插入测试数据即可。

```sql
INSERT INTO `sb_user` VALUES ('root',1,1,1,1,'$2a$10$yurclUXK8yPBMXq48R/JOO/gPyGSxt9nPntf9uzHDKHnmz4RbQSDu'),('user',1,1,1,1,'$2a$10$FCGoiDits7Dk.mBP8ETUseHohoD3mvH08shEXMuM8TXksPv3PQ0cO');
INSERT INTO `sb_role` VALUES ('ADMIN'),('USER');
INSERT INTO `sb_user_role_ref` VALUES ('root','ADMIN'),('root','USER'),('user','USER');
INSERT INTO `sb_client` VALUES ('94984796',NULL,'5ldDM0uAAsCjW5ZN',NULL);
INSERT INTO `sb_client_grant_types` VALUES ('94984796','authorization_code'),('94984796','password');
INSERT INTO `sb_client_scopes` VALUES ('94984796','app');
INSERT INTO `sb_enterprise` VALUES (1,'中国-上海',NULL,'矩阵工作室','025-12345678');
```
