package com.invoice.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fapiao.neon.client.in.BaseClient;
import com.fapiao.neon.client.in.CheckInvoiceClient;
import com.fapiao.neon.client.in.impl.BaseClientImpl;
import com.fapiao.neon.client.in.impl.CheckInvoiceClientImpl;
import com.fapiao.neon.config.NeonConfiguration;
import com.fapiao.neon.config.Profile;
 
 
@Configuration
public class NeonConfig {
 
    @Bean
    public NeonConfiguration neonConfiguration() {
        NeonConfiguration configuration = new NeonConfiguration();
        //根据实际运行环境配置Profile
        configuration.setProfile(Profile.PRODUCT);
        configuration.setClientId("152877251001");
        configuration.setClientSecret("03055f61c32011e9be5b005056b64580");
        return configuration;
    }
 
    @Bean
    public BaseClient baseClient() {
        return new BaseClientImpl(neonConfiguration());
    }
 
    @Bean
    public CheckInvoiceClient checkInvoiceClient() {
        return new CheckInvoiceClientImpl(neonConfiguration());
    }
    
}
