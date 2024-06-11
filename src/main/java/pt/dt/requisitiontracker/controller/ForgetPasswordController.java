package pt.dt.requisitiontracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.dt.requisitiontracker.model.ForgetPasswordToken;
import pt.dt.requisitiontracker.model.User;
import pt.dt.requisitiontracker.repository.ForgetPasswordRepository;
import pt.dt.requisitiontracker.service.ForgetPasswordService;
import pt.dt.requisitiontracker.service.UserService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgetPasswordController {
    @Autowired
    UserService userService;
    @Autowired
    ForgetPasswordService forgetPasswordService;
    @Autowired
    ForgetPasswordRepository forgetPasswordRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/password-request")
    public String passwordRequest(){
        return "forms/password-request";
    }
    @PostMapping("/password-request")
    public String savePasswordRequest(@RequestParam("email") String email, Model model){
        User user = userService.findByEmail(email);
        if(user == null){
            model.addAttribute("error", "This email is not registered");
            return "forms/password-request";
        }
        ForgetPasswordToken forgetPasswordToken = new ForgetPasswordToken();
        forgetPasswordToken.setExpireTime(forgetPasswordService.expireTimeRange());
        forgetPasswordToken.setToken(forgetPasswordService.generateToken());
        forgetPasswordToken.setUser(user);
        forgetPasswordToken.setUsed(false);
        forgetPasswordRepository.save(forgetPasswordToken);

        String emailLink = "http://localhost:8080/reset-password?token=" + forgetPasswordToken.getToken();
        try {
            forgetPasswordService.sendEmail(user.getEmail(), "Password Reset Link", emailLink);
        } catch (MessagingException | UnsupportedEncodingException e) {
            model.addAttribute("error", "Error while sending email");
            return "forms/password-request";
        }
        model.addAttribute("success", true);
        return "forms/password-request";
      /*  return "redirect:forms/password-request?success";*/
    }
    @GetMapping("/reset-password")
    public String resetPassword(@Param(value = "token") String token, Model model, HttpSession session){
        session.setAttribute("token", token);
        ForgetPasswordToken forgetPasswordToken = forgetPasswordRepository.findByToken(token);
        return forgetPasswordService.checkValidity(forgetPasswordToken,model);
    }
    @PostMapping("/reset-password")
    public String saveResetPassword(HttpServletRequest request, HttpSession session, Model model){
        String password =request.getParameter("password");
        String token = (String)session.getAttribute("token");

        ForgetPasswordToken forgetPasswordToken = forgetPasswordRepository.findByToken(token);
        User user = forgetPasswordToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        forgetPasswordToken.setUsed(true);
        userService.save(user);
        forgetPasswordRepository.save(forgetPasswordToken);

        model.addAttribute("message", "You have successfully reset your password");

        return "forms/reset-password";
    }
}
