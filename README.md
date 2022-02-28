
## 这是一个消息网关

### 环境依赖
1. JDK 1.8
2. Redis

### 镜像
1. open-api: riverlcj/exam-openapi (对外端口8080)
2. 短信模拟平台: riverlcj/exam-sms-server:stable (对外端口8080)
3. 本应用: mabelchen/msg-gateway:release2.0 (对外端口8081)

### 配置文件
src/main/resources/application.yml
