# FROM openjdk:21-jdk-slim
# ADD /build/libs/*-1.0.0.jar app.jar
# #ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
# ENTRYPOINT ["java", "-jar", "/app.jar"]

# JDK 17 Image start
FROM openjdk:17-jdk

# 파일을 이미지 추가
COPY /build/libs/*-1.0.0.jar app.jar

# 컨테이너 시작,종료 될 때 수행되는 명령어 -> java -jar app.jar 명령어가 실행되어 복사해두었던 jar파일 실행하여 boot프로젝트 구동하겠다는 의미
ENTRYPOINT ["java", "-jar", "/app.jar"]


