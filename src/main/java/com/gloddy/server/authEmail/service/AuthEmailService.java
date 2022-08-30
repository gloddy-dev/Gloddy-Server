package com.gloddy.server.authEmail.service;

import com.gloddy.server.authEmail.dto.response.AuthEmailResponse;
import com.gloddy.server.authEmail.exception.InvalidEmailException;
import com.gloddy.server.authEmail.exception.InvalidVerificationCodeException;
import com.gloddy.server.authEmail.dto.request.AuthEmailRequest;
import com.gloddy.server.core.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthEmailService {
    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;

    @Transactional
    public void authEmail(AuthEmailRequest.AuthEmail request) {
        validateEmail(request.getEmail());
        String code = createCode();
        sendEmail(request.getEmail(), code);
    }

    private void sendEmail(String email, String code) {
        String title = "Gloddy 회원가입을 위한 인증코드입니다.";
        String text = "Gloddy 회원 가입을 위한 인증번호는 " + code + "입니다. <br/>";

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            helper.setFrom("Gloddy <devgloddy@gmail.com>");
            helper.setTo(email);
            helper.setSentDate(new Date());
            helper.setSubject(title);
            helper.setText(text, true);
            log.info("메시지 생성 완료 : {}", mimeMessage);
            mailSender.send(mimeMessage);
            log.info("메시지 전송");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        log.info("email: {}", email);
        log.info("code: {}", code);
        redisUtil.setDataExpire(email, code, 60 * 5L);
    }

    private String createCode() {
        Random random = new Random();
        String code = String.valueOf(random.nextInt(888888) + 111111);
        return code;
    }

    private void validateEmail(String email) {
        if(!email.contains("ac.kr")) {
            throw new InvalidEmailException();
        }
    }

    // TODO: 유효하지 않은 인증코드인 경우 401 에러를 던지는데 500에러로 뜸 httpStatus 잘못 설정한 듯
    @Transactional
    public AuthEmailResponse.schoolResponse verifyCode(AuthEmailRequest.AuthCode request) {
        String code = redisUtil.getData(request.getEmail());
        if(!code.equals(request.getAuthCode())) {
            throw new InvalidVerificationCodeException();
        }
        String school = request.getEmail().split("@")[1].split("\\.")[0];
        log.info("school: {}", school);
        return new AuthEmailResponse.schoolResponse(school);
    }
}
