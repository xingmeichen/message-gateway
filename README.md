
## 这是一个消息网关

### 环境依赖
1. JDK 1.8
2. Redis

### hosts 文件添加配置
127.0.0.1 redis
127.0.0.1 mock-sms-server 

### 镜像
1. open-api: riverlcj/exam-openapi
2. 短信模拟平台: riverlcj/exam-sms-server:stable
3. 本应用: mabelchen/msg-gateway:release1.0

### 配置文件
src/main/resources/application.yml
