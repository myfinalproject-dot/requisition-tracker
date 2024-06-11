package pt.dt.requisitiontracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pt.dt.requisitiontracker.model.ForgetPasswordToken;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ForgetPasswordService {
    @Autowired
    JavaMailSender javaMailSender;
    private final int MINUTES = 10;
    public String generateToken(){
        return UUID.randomUUID().toString();
    }
    public LocalDateTime expireTimeRange(){
        return LocalDateTime.now().plusMinutes(MINUTES);
    }
    public void sendEmail(String to, String subject, String emailLink) throws  MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String emailContent = "<p>Hello</p>"
                +"Click the link below to reset password"
                + "<p><a href=\"" + emailLink + "\">Change My Password</a></p>"
                + "<br>"
                +"Ignore this Email if you did not made the request";

        helper.setText(emailContent, true);
        helper.setFrom("talentmandishe26@gmail.com","CUT Requisition System");
        helper.setSubject(subject);
        helper.setTo(to);
        javaMailSender.send(message);

    }
    public boolean isExpired(ForgetPasswordToken forgetPasswordToken){
        return LocalDateTime.now().isAfter(forgetPasswordToken.getExpireTime());
    }
    public String checkValidity(ForgetPasswordToken forgetPasswordToken, Model model){
        if(forgetPasswordToken == null){
            model.addAttribute("error", "Invalid Token");
            return "forms/error-page";
        } else if (forgetPasswordToken.isUsed()) {
            model.addAttribute("error","this token is already used");
            return "forms/error-page";
        } else if (isExpired(forgetPasswordToken)) {
            model.addAttribute("error","this token is expired");
            return "forms/error-page";
        }
        else {
            return "forms/reset-password";
        }
    }
}
