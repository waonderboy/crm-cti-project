package com.brogs.crm.infra.message.api;

import com.brogs.crm.domain.agentaccount.AccountCommand;
import com.brogs.crm.domain.externalmessenger.SendMethod;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Slf4j
@Component
@RequiredArgsConstructor
public class EmailMessageApiCaller implements MessageApiCaller{
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    @Override
    public boolean support(SendMethod sendMethod) {
        return SendMethod.EMAIL == sendMethod;
    }


    @Override
    public void sendConfirmMail(AccountCommand.RegisterProfile request, String confirmToken) {
        // TODO: 예외 변경 필요
        try {
            log.info("인증코드 전송이 시작됩니다 email: [ {} ], code: [ {} ];", request.getEmail(), confirmToken);
            mailSender.send(createEmailForm(request.getEmail(), confirmToken));
        } catch (MessagingException e) {
            throw new RuntimeException(e); // 예외 변경 필요
        }
    }

    private MimeMessage createEmailForm(String email, String token) throws MessagingException {

        String setFrom = "crazy7523@gmail.com"; // 외부로 뺴야함
        String title = "프로필 인증 확인";

        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject(title); //제목 설정
        message.setFrom(setFrom); //보내는 이메일
        message.setText(setContext(email, token), "utf-8", "html");

        return message;
    }


    private String setContext(String email, String token) {
        Context context = new Context();
        context.setVariable("email", email);
        context.setVariable("token", token);
        return templateEngine.process("mail", context);
    }
}
