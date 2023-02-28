FROM openjdk:8

# 维护人
MAINTAINER bruce

# 镜像信息
LABEL name="dockerfile-demo" version="1.0" author="bruce"

# 指定需要制作镜像的jar，拷贝到用户空间根目录下（可以指定目录），并命名
COPY spring-boot-demo-0.0.1-SNAPSHOT.jar dockerfile-image.jar

# 运行jar的命令
CMD ["java","-jar","dockerfile-image.jar"]