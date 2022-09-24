package com.algafoodapi.domain.email;

import com.algafoodapi.domain.email.service.EnvioEmailFakeService;
import com.algafoodapi.domain.email.service.EnvioEmailService;
import com.algafoodapi.domain.email.service.SandBoxEnvioEmailService;
import com.algafoodapi.domain.email.service.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    public EnvioEmailService envioEmailService() {

        switch (emailProperties.getImpl()) {
            case FAKE:
                return new EnvioEmailFakeService();
            case SMTP:
                return new SmtpEnvioEmailService();
            case SANDBOX:
                return new SandBoxEnvioEmailService();
            default:
                return null;
        }
    }
}
