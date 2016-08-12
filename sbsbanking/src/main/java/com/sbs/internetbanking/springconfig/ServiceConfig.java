package com.sbs.internetbanking.springconfig;

import com.sbs.internetbanking.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public BankingFunctionsService bankingFunctionsService() {
        return new BankingFunctionsServiceImpl();
    }

    @Bean
    public TransactionService transactionService() {
        return new TransactionServiceImpl();
    }
    @Bean
    public EmailService emailService(@Value("${mail.smtp.host}") String host, @Value("${mail.smtp.auth}") String auth,
                                     @Value("${mail.debug}") String debug, @Value("${mail.smtp.port}") String port,
                                     @Value("${bank.emailId}") String bankEmailAddress, @Value("${bank.password}") String bankEmailPswd) {
        return new EmailService(host, auth, debug, port, bankEmailAddress, bankEmailPswd);
    }

    @Bean
    public OTPService otpService(@Value("${otp.subject}") String otpSubject) {
        return new OTPService(otpSubject);
    }

    @Bean
    public RequestHandlerService requestHandlerService() {
        return new RequestHandlerService();
    }
}