package com.example.demo.security;

import com.example.demo.constant.Constants;
import com.example.demo.logger.FileLogger;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SecurityBeans class  is used to make beans which we are using with only one instance through our whole life of application.
 */
@Configuration
public class SecurityBeans {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FileLogger fileLoggerUserCreation() {
        FileLogger fileLogger = new FileLogger();
        fileLogger.setFilepath(Constants.FILE_PATH_CASHIER_CREATION);
        return fileLogger;
    }

    @Bean
    public FileLogger fileLoggerDepositTransactions() {
        FileLogger fileLogger = new FileLogger();
        fileLogger.setFilepath(Constants.FILE_PATH_DEPOSIT_OPERATION);
        return fileLogger;
    }

    @Bean
    public FileLogger fileLoggerWithdrawTransactions() {
        FileLogger fileLogger = new FileLogger();
        fileLogger.setFilepath(Constants.FILE_PATH_WITHDRAW_OPERATION);
        return fileLogger;
    }

    @Bean
    public FileLogger fileLoggerCashBalance() {
        FileLogger fileLogger = new FileLogger();
        fileLogger.setFilepath(Constants.FILE_PATH_CASH_BALANCE);
        return fileLogger;
    }

}
