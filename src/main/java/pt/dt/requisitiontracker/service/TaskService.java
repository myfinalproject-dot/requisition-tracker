package pt.dt.requisitiontracker.service;

import pt.dt.requisitiontracker.model.Status;
import pt.dt.requisitiontracker.model.Task;
import pt.dt.requisitiontracker.model.User;

import java.util.List;

public interface TaskService {

    void createTask(Task task);

//    void updateTask(Long id, Task task);

    void deleteTask(Long id);

    List<Task> findAll();

    List<Task> findByOwnerOrderByDateDesc(User user);

    Task setTaskCompleted(Long id);

    Task setTaskNotCompleted(Long id);

    List<Task> findFreeTasks();

    Task getTaskById(Long taskId);

    void assignTaskToUser(Task task, User user);

    void unassignTask(Task task);

    void updateTask(Long id, Task task, String fileName, String fileDownloadUri);

    List<Task> findAllCreater(User signedUser);

    void updateTaskStatus(Long taskId, Status status);

}
