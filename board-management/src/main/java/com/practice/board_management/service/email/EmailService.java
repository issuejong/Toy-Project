package com.practice.board_management.service.email;

import com.practice.board_management.dto.user.request.EmailConfirmRequest;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;
    private final Random random = new SecureRandom();
    private String firstCode;
    List<String> canSignUp = new ArrayList<String>();

    public void sendVerificationCode(String email) {
        try {
            String code = String.valueOf(100000 + random.nextInt(900000));
            this.firstCode = code;

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(email);
            helper.setSubject("회원가입 인증코드");
            helper.setText("아래 인증코드를 입력해주세요: " + code, false);
            helper.setFrom("your-gmail@gmail.com"); // spring.mail.username 값과 동일해야 함

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("메일 전송 실패");
        }
    }

    public boolean verifyCode(String email, String code) {
        if(firstCode != null && firstCode.equals(code)) {
            canSignUp.add(email);
            return true;
        }
        else return false;
    }

    public boolean isVerified(String email) {
        return canSignUp.contains(email);
    }
}
