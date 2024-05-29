package pt.dt.requisitiontracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pt.dt.requisitiontracker.model.Task;
import pt.dt.requisitiontracker.model.User;
import pt.dt.requisitiontracker.repository.RoleRepository;
import pt.dt.requisitiontracker.service.TaskService;
import pt.dt.requisitiontracker.service.UserService;

import java.security.Principal;

@Controller
public class AssigmentController {
    private UserService userService;
    private TaskService taskService;
    private RoleRepository roleRepository;

    @Autowired
    public AssigmentController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/assignment")
    public String showAssigmentForm(Model model,Principal principal, SecurityContextHolderAwareRequestWrapper request) {
        prepareTasksListModelProgress(model, principal, request);
        model.addAttribute("freeTasks", taskService.findFreeTasks());
        return "forms/assignment";
    }
    private void prepareTasksListModelProgress(Model model, Principal principal, SecurityContextHolderAwareRequestWrapper request) {
        String email = principal.getName();
        User signedUser = userService.getUserByEmail(email);
        boolean isAdminSigned = request.isUserInRole("ROLE_ADMIN");
        if (isAdminSigned) {
            model.addAttribute("users", userService.findAll());

        }
        else {
            model.addAttribute("users", userService.findAllContions(signedUser));
        }
    }

    @GetMapping("/assignment/{userId}")
    public String showUserAssigmentForm(@PathVariable Long userId, Model model,Principal principal) {
        String email = principal.getName();
        User signedUser = userService.getUserByEmail(email);
        model.addAttribute("selectedUser", userService.getUserById(userId));
        model.addAttribute("users", userService.findAllContions(signedUser));
        model.addAttribute("freeTasks", taskService.findFreeTasks());
        return "forms/assignment";
    }

    @GetMapping("/assignment/assign/{userId}/{taskId}")
    public String assignTaskToUser(@PathVariable Long userId, @PathVariable Long taskId) {
        Task selectedTask = taskService.getTaskById(taskId);
        User selectedUser = userService.getUserById(userId);
        taskService.assignTaskToUser(selectedTask, selectedUser);
        return "redirect:/assignment/" + userId;
    }

    @GetMapping("assignment/unassign/{userId}/{taskId}")
    public String unassignTaskFromUser(@PathVariable Long userId, @PathVariable Long taskId) {
        Task selectedTask = taskService.getTaskById(taskId);
        taskService.unassignTask(selectedTask);
        return "redirect:/assignment/" + userId;
    }

}



