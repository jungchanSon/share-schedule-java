// 아직 사용하지 않음
//package com.schedule.share.common.config;
//
//import org.jasypt.encryption.StringEncryptor;
//import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
///*
//* Jasypt 설정파일 암호화
//*/
//@Configuration
//public class JasyptConfig {
//    @Bean(name = "encryptorBean")
//    public StringEncryptor stringEncryptor() {
//        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
//        encryptor.setPassword(System.getenv("JASYPT_ENCRYPTOR_PASSWORD")); // 환경 변수에서 암호 가져오기
//        encryptor.setAlgorithm("PBEWithMD5AndDES"); // 또는 지원되는 다른 알고리즘
//        return encryptor;
//    }
//}
