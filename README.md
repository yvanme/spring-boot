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

## OAuth2

提供两个用户用于测试：

| username | password |
|:---------|:---------|
| root     | root     |
| user     | user     |

### 授权码模式

项目启动后，在浏览器地址栏输入`http://localhost:8080/oauth/authorize?client_id=94984796&response_type=code&redirect_uri=https://www.baidu.com&scope=app`，会重定向到登陆页面，输入用户名和密码登陆。

登陆完成后，会重定向到`/oauth/confirm_access`页面，同意授权后，会重定向到`redirect_uri`的地址，也就是我们上一步指定的`https://www.baidu.com`，此时可以发现链接后面多了一个`code`的参数，该参数就是生成的授权码。

使用`curl`命令或Postman工具访问`/oauth/token`链接换取`token`:

```
POST /oauth/token HTTP/1.1
Host: localhost
Authorization: Basic OTQ5ODQ3OTY6NWxkRE0wdUFBc0NqVzVaTg==
Content-Type: application/x-www-form-urlencoded

grant_type=authorization_code&code=8xSlU&redirect_uri=https://www.baidu.com
```

如果请求成功，服务器会返回如下响应：

```json
{
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTI2NTkyMTUsInVzZXJfbmFtZSI6InVzZXIiLCJhdXRob3JpdGllcyI6WyJVU0VSIiwiQUNUVUFUT1IiXSwianRpIjoiMmRkOTg0YzItOWVlMy00NGQxLWIyZWQtN2MzY2Q0MTcyODkyIiwiY2xpZW50X2lkIjoiOTQ5ODQ3OTYiLCJzY29wZSI6WyJhcHAiXX0.IidgvE6PI4JsO-nOWQQkpYB-Bm-bpeoxzZ3-vac1e1krbTIbzDlSOLcv7vZM9kvE8k4Rm-c5er2fvyTPmRAqg1zlPORsZJnm3cbeJ4wbgyImelWNb8faXNDAnTmwBjx-Q3JnptPNQIJ6-yxd8fgIZ2cKmS_-_Dk-U5XeurCIagmoz_dxMLvpjTirv8Vmv0XAOQavipGGQHdb4mbNpQB-OUIOiZQIlKhiSqs8sBHKTsgoOAwB6d9MH5S6gUt261f9lUa8SLhKOlBcEjSxxKXY260irTNsqJbx58kADMuikayBiQYg42gs6OuNwbe7fkAJWD55DpOJnjj9yS6-CYDLFA",
    "token_type": "bearer",
    "expires_in": 43199,
    "scope": "app",
    "jti": "2dd984c2-9ee3-44d1-b2ed-7c3cd4172892"
}
```

现在请求受保护的资源，并带上`access_token`，服务器校验`token`合法后会成功返回资源内容。

`access_token`可以放在URL链接中，也可以放在请求头部：

```
// 放在url中
http://localhost:8080/enterprises?access_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVC...省略掉部分内容

// 放在请求头部
GET /enterprises HTTP/1.1
Host: localhost
Authorization: bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVC...省略掉部分内容
Content-Type: application/x-www-form-urlencoded
```

### 密码模式

```
POST /oauth/token HTTP/1.1
Host: localhost
Authorization: Basic OTQ5ODQ3OTY6NWxkRE0wdUFBc0NqVzVaTg==
Content-Type: application/x-www-form-urlencoded

grant_type=password&username=user&password=user&scope=app
```

成功后会从服务器返回`token`信息。

## JWT

JWT非对称加密使用了密钥证书实现，在`AuthorizationServerConfiguration#jwtAccessTokenConverter()`中指定。

keytool生成证书：

```bash
keytool -genkeypair -alias [alias] -keyalg RSA -keypass [password] -keystore keystore.jks -storepass [password]
```

获取PUBLIC KEY（建议在Linux环境运行该命令）：

```bash
keytool -list -rfc --keystore keystore.jks | openssl x509 -inform pem -pubkey
```

## Database Schema

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
