package pt.dt.requisitiontracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pt.dt.requisitiontracker.model.Task;
import pt.dt.requisitiontracker.repository.TaskRepository;

import java.util.List;

@Service
public class NotificationService {

    private final TaskRepository taskRepository;
    private final JavaMailSender emailSender;

    @Autowired
    public NotificationService(TaskRepository taskRepository, JavaMailSender emailSender) {
        this.taskRepository = taskRepository;
        this.emailSender = emailSender;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Run once daily at midnight
    public void checkDeadlines() {
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            if (!task.isCompleted() && task.daysLeftUntilDeadline(task.getDate()) == 2) {
                sendNotification(task);
            }
        }
    }

    private void sendNotification(Task task) {
        if (task.getOwner() != null && task.getOwner().getEmail() != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("talentmandishe26@gmail.com");
            message.setTo(task.getOwner().getEmail()); // Assuming Task has a method getOwner() that returns the User
            message.setSubject("Requisition Deadline Reminder");
            message.setText("Dear " + task.getOwner().getName() + ",\n\n" +
                    "This is a reminder that your task \"" + task.getName() + "\" is due in 2 days.\n\n" +
                    "Best regards,\nCUT Requisition Tracker Team");
            emailSender.send(message);
        }
    }
}
