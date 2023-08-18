# Docker image for {tomcat? springBoot?}
# VERSION {版本号}
# Author: uhyils
# docker stop prod-usher-service-mongo
# docker rm prod-usher-service-mongo
# docker rmi usher-service/prod-usher-service-mongo:latest
# docker build -t usher-service/prod-usher-service-mongo .
# docker run -it -d  -p 8057:8057 -p 20857:20857 -v /usher/logs:/usher/logs --name prod-usher-service-mongo usher-service/prod-usher-service-mongo:latest

FROM java:8

#作者
MAINTAINER uhyils <uhyils@qq.com>


#挂载
VOLUME /usher/logs

#暴露端口
EXPOSE 8057 20857

#应用构建成功后的jar文件被复制到镜像内，名字也改成了app.jar
ADD target/usher-*.jar /app.jar

#启动容器时的进程
ENTRYPOINT ["java","-Xms512m","-Xmx512m","-XX:+PrintGCTimeStamps","-XX:+UseG1GC","-XX:+HeapDumpOnOutOfMemoryError","-XX:HeapDumpPath=/usher/logs/mongo/JVM_DOWN.dump","-XX:NewRatio=1","-Xloggc:/usher/logs/mongo/JVM.log","-jar","/app.jar","--spring.profiles.active=prod"]
