package pt.dt.requisitiontracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.dt.requisitiontracker.model.Status;
import pt.dt.requisitiontracker.model.Task;
import pt.dt.requisitiontracker.model.User;
import pt.dt.requisitiontracker.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;

//    private final MailSenderService mailService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void createTask(Task task) {
//        mailService.sendNewMail("h200555w@hit.ac.zw", "Subject right here", "Body right there!");
        taskRepository.save(task);
    }

    @Override
    public void updateTask(Long id, Task updatedTask, String fileName, String fileDownloadUri) {
        Task task = taskRepository.getOne(id);
        task.setName(updatedTask.getName());
        task.setDescription(updatedTask.getDescription());
        task.setDate(updatedTask.getDate());
        task.setFilename(fileName);
        task.setDownloadlink(fileDownloadUri);
        taskRepository.save(task);
    }

    @Override
    public List<Task> findAllCreater(User signedUser) {

        return taskRepository.findAll()
                .stream()
                .filter(task -> task.getCreatorName().equals(signedUser.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateTaskStatus(Long taskId, Status status) {
        Task task = taskRepository.getOne(taskId);
        task.setStatus(status);
        task.setStatusUpdated(true);
        taskRepository.save(task);
    }

//    public void Foo(){
//        mailService.sendNewMail("test@gmail.com", "Subject right here", "Body right there!");
//    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findByOwnerOrderByDateDesc(User user) {
        return taskRepository.findByOwnerOrderByDateDesc(user);
    }

    @Override
    public Task setTaskCompleted(Long id) {
        Task task = taskRepository.getOne(id);
        task.setCompleted(true);
        taskRepository.save(task);
        return task;
    }

    @Override
    public Task setTaskNotCompleted(Long id) {
        Task task = taskRepository.getOne(id);
        task.setCompleted(false);
        taskRepository.save(task);
        return task;
    }

    @Override
    public List<Task> findFreeTasks() {
        return taskRepository.findAll()
                .stream()
                .filter(task -> task.getOwner() == null && !task.isCompleted())
                .collect(Collectors.toList());

    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public void assignTaskToUser(Task task, User user) {
        task.setOwner(user);
        taskRepository.save(task);
    }

    @Override
    public void unassignTask(Task task) {
        task.setOwner(null);
        taskRepository.save(task);
    }

}
