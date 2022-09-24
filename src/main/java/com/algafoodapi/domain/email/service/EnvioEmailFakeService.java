package com.algafoodapi.domain.email.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnvioEmailFakeService extends SmtpEnvioEmailService {

    public void enviarEmailFake(Mensagem mensagem) {

         String corpo = processarTemplate(mensagem);
        log.info("FAKE E-MAIL Para:{}\n{}", mensagem.getDestinatarios(), corpo);
    }
}
