# 1.8.0_202 버전의 Java 이미지 사용
FROM tomcat:9-jdk8-openjdk

# 애플리케이션 JAR 파일을 컨테이너에 복사
COPY target/kumyang.war /usr/local/tomcat/webapps/kumyang.war

ENV SPRING_PROFILES_ACTIVE=dev

# Tomcat 포트 8080을 열기
EXPOSE 8080

# 애플리케이션 실행
CMD ["sh", "-c", "catalina.sh run"]