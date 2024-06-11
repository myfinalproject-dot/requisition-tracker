package pt.dt.requisitiontracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pt.dt.requisitiontracker.model.Task;
import pt.dt.requisitiontracker.model.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendStatusUpdateEmail(User user, Task task) {
        Context context = new Context();
        context.setVariable("name", user.getName());
        context.setVariable("taskName", task.getName());
        context.setVariable("status", task.getStatus());

        String htmlContent = templateEngine.process("email-template", context);

        sendEmail(user.getEmail(), "Task Status Updated", htmlContent);
    }

    private void sendEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("talentmandishe26@gmail.com", "CUT Requisition System");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

