# Docker image for {tomcat? springBoot?}
# VERSION {版本号}
# Author: uhyils

# docker stop prod-usher-web
# docker rm prod-usher-web
# docker rmi usher-service/prod-usher-web:latest
# docker build -t usher-service/prod-usher-web .
# docker run -it -d  -p 8051:8051 -p 20851:20851 -v /usher/logs:/usher/logs --name prod-usher-web usher-service/prod-usher-web:latest

FROM java:8

#作者
MAINTAINER uhyils <uhyils@qq.com>


#挂载
VOLUME /usher/logs:/usher/logs

#暴露端口
EXPOSE 8051 20851

#应用构建成功后的jar文件被复制到镜像内，名字也改成了app.jar
ADD target/usher-*.jar /app.jar

#启动容器时的进程
ENTRYPOINT ["java","-Xms512m","-Xmx512m","-XX:+PrintGCTimeStamps","-XX:+UseG1GC","-XX:+HeapDumpOnOutOfMemoryError","-XX:HeapDumpPath=/usher/logs/web/JVM_DOWN.dump","-XX:NewRatio=1","-Xloggc:/usher/logs/web/JVM.log","-jar","/app.jar","--spring.profiles.active=prod"]
