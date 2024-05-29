//package pt.dt.doctortrack.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MailSenderService {
//    @Autowired
//    private JavaMailSender mailSender;
//
////    public void sendNewMail(String to, String subject, String body) {
////        SimpleMailMessage message = new SimpleMailMessage();
////        message.setTo(to);
////        message.setSubject(subject);
////        message.setText(body);
////        mailSender.send(message);
////    }
//
//    public void sendNewMail(String to, String subject, String body) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("learnmorembca@outlook.com"); // Set the sender's email address
//        message.setTo(to); // Set the recipient's email address
//        message.setSubject(subject); // Set the email subject
//        message.setText(body); // Set the email body
//
//        mailSender.send(message);
//    }
//
//}