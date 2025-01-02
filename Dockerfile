FROM openjdk:17
MAINTAINER voucher.com
COPY target/VoucherVault-0.0.1-SNAPSHOT.jar VoucherVault-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/VoucherVault-0.0.1-SNAPSHOT.jar"]