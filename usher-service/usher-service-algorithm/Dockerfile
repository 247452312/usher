# Docker image for {tomcat? springBoot?}
# VERSION {版本号}
# Author: uhyils
# docker stop prod-usher-service-algorithm
# docker rm prod-usher-service-algorithm
# docker rmi usher-service/prod-usher-service-algorithm:latest
# docker build -t usher-service/prod-usher-service-algorithm .
# docker run -it -d  -p 8058:8058 -p 20858:20858 -v /usher/logs:/usher/logs --name prod-usher-service-algorithm usher-service/prod-usher-service-algorithm:latest

FROM java:8

#作者
MAINTAINER uhyils <uhyils@qq.com>


#挂载
VOLUME /usher/logs

#暴露端口
EXPOSE 8058 20858

#应用构建成功后的jar文件被复制到镜像内，名字也改成了app.jar
ADD target/usher-*.jar /app.jar

#启动容器时的进程
ENTRYPOINT ["java","-Xms512m","-Xmx512m","-XX:+PrintGCTimeStamps","-XX:+UseG1GC","-XX:+HeapDumpOnOutOfMemoryError","-XX:HeapDumpPath=/usher/logs/algorithm/JVM_DOWN.dump","-XX:NewRatio=1","-Xloggc:/usher/logs/algorithm/JVM.log","-jar","/app.jar","--spring.profiles.active=prod"]
