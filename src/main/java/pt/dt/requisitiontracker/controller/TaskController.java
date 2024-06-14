package pt.dt.requisitiontracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pt.dt.requisitiontracker.model.Task;
import pt.dt.requisitiontracker.model.User;
import pt.dt.requisitiontracker.service.EmailService;
import pt.dt.requisitiontracker.service.FileStorageService;
import pt.dt.requisitiontracker.service.TaskService;
import pt.dt.requisitiontracker.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
public class TaskController {

    private TaskService taskService;
    private UserService userService;
    private EmailService emailService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/tasks")
    public String listTasks(Model model, Principal principal, SecurityContextHolderAwareRequestWrapper request) {
        prepareTasksListModel(model, principal, request);
        String email = principal.getName();
        User signedUser = userService.getUserByEmail(email);
        prepareTasksListModel(model, signedUser);
        model.addAttribute("onlyInProgress", false);
        return "views/tasks";
    }

    private void prepareTasksListModel(Model model, User signedUser) {
        model.addAttribute("tasks", taskService.findByOwnerOrderByDateDesc(signedUser));
        model.addAttribute("signedUser", signedUser);
    }

    @GetMapping("/progress")
    public String listProgress(Model model, Principal principal, SecurityContextHolderAwareRequestWrapper request) {
        prepareTasksListModelProgress(model, principal, request);
        model.addAttribute("onlyInProgress", false);
        return "views/progress";
    }

    private void prepareTasksListModelProgress(Model model, Principal principal, SecurityContextHolderAwareRequestWrapper request) {
        String email = principal.getName();
        User signedUser = userService.getUserByEmail(email);
        boolean isAdminSigned = request.isUserInRole("ROLE_ADMIN");

        model.addAttribute("tasks", taskService.findAllCreater(signedUser));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("signedUser", signedUser);
        model.addAttribute("isAdminSigned", isAdminSigned);
    }


    @GetMapping("/tasks/in-progress")
    public String listTasksInProgress(Model model, Principal principal, SecurityContextHolderAwareRequestWrapper request) {
        prepareTasksListModel(model, principal, request);
        model.addAttribute("onlyInProgress", true);
        return "views/tasks";
    }

    private void prepareTasksListModel(Model model, Principal principal, SecurityContextHolderAwareRequestWrapper request) {
        String email = principal.getName();
        User signedUser = userService.getUserByEmail(email);
        boolean isAdminSigned = request.isUserInRole("ROLE_ADMIN");

        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("signedUser", signedUser);
        model.addAttribute("isAdminSigned", isAdminSigned);

    }

    @GetMapping("/task/create")
    public String showEmptyTaskForm(Model model, Principal principal, SecurityContextHolderAwareRequestWrapper request) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);

        Task task = new Task();
        task.setCreatorName(user.getName());
//        if (request.isUserInRole("ROLE_USER")) {
//            task.setOwner(user);
//        }
        model.addAttribute("task", task);
        return "forms/task-new";
    }

    @PostMapping("/task/create")
    public String createTask(@RequestParam("file") MultipartFile file,@Valid Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "forms/task-new";
        }
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download-file/")
                .path(fileName)
                .toUriString();

        System.out.println(fileDownloadUri);
        task.setDownloadlink(fileDownloadUri);
        task.setFilename(fileName);



        taskService.createTask(task);

        return "redirect:/progress";
    }
    @GetMapping(value = "/download-file/{fileName:.+}")
//    @Operation(summary = "Download File", description = "Update file status")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.print("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/task/edit/{id}")
    public String showFilledTaskForm(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        return "forms/task-edit";
    }

    @PostMapping("/task/edit/{id}")
    public String updateTask(@RequestParam("file") MultipartFile file,@Valid Task task, BindingResult bindingResult, @PathVariable Long id, Model model) {
        if (bindingResult.hasErrors()) {
            return "forms/task-edit";
        }

        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download-file/")
                .path(fileName)
                .toUriString();

        System.out.println("+++++++++++++++++++++done++++++++++++++++++++++");

        System.out.println(fileDownloadUri);
        task.setDownloadlink(fileDownloadUri);
        task.setFilename(fileName);
        System.out.println("+++++++++++++++++++++done++++++++++++++++++++++");

        taskService.updateTask(id, task,fileName,fileDownloadUri);
        return "redirect:/tasks";
    }

    @GetMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("/task/mark-done/{id}")
    public String setTaskCompleted(@PathVariable Long id) {
        Task task= taskService.setTaskCompleted(id);
        emailService.sendStatusUpdateEmail(task.getOwner(), task);
        return "redirect:/tasks";
    }

    @GetMapping("/task/unmark-done/{id}")
    public String setTaskNotCompleted(@PathVariable Long id) {
        Task task = taskService.setTaskNotCompleted(id);
        emailService.sendStatusUpdateEmail(task.getOwner(), task);

        return "redirect:/tasks";
    }

}
