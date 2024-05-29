package pt.dt.requisitiontracker.dataloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pt.dt.requisitiontracker.model.Role;
import pt.dt.requisitiontracker.model.User;
import pt.dt.requisitiontracker.model.Task;
import pt.dt.requisitiontracker.service.RoleService;
import pt.dt.requisitiontracker.service.TaskService;
import pt.dt.requisitiontracker.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;
    private TaskService taskService;
    private RoleService roleService;
    private final Logger logger = LoggerFactory.getLogger(InitialDataLoader.class);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Value("${default.admin.mail}")
    private String defaultAdminMail;
    @Value("${default.admin.name}")
    private String defaultAdminName;
    @Value("${default.admin.password}")
    private String defaultAdminPassword;
    @Value("${default.admin.image}")
    private String defaultAdminImage;

    @Autowired
    public InitialDataLoader(UserService userService, TaskService taskService, RoleService roleService) {
        this.userService = userService;
        this.taskService = taskService;
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        //ROLES --------------------------------------------------------------------------------------------------------
        roleService.createRole(new Role("ADMIN"));
        roleService.createRole(new Role("USER"));
        roleService.findAll().stream().map(role -> "saved role: " + role.getRole()).forEach(logger::info);

        //USERS --------------------------------------------------------------------------------------------------------
        //1
        User admin = new User(
                defaultAdminMail,
                defaultAdminName,
                defaultAdminPassword,
                defaultAdminImage);
        userService.createUser(admin);
        userService.changeRoleToAdmin(admin);

        //2
        User dean = new User(
                "dean@gmail.com",
                "dean",
                "112233",
                "images/admin.png");
        userService.createUser(dean);
        userService.changeRoleToAdmin(dean);

        User hod= new User(
                "hodit@gmail.com",
                "hod(IT Dept)",
                "112233",
                "images/admin.png");
        userService.createUser(hod);
        userService.changeRoleToAdmin(hod);

        User hod1= new User(
                "hodmecha@gmail.com",
                "hod(Mechatronics Dept)",
                "112233",
                "images/admin.png");
        userService.createUser(hod1);
        userService.changeRoleToAdmin(hod1);

        User accounts= new User(
                "accounts@gmail.com",
                "accounts",
                "112233",
                "images/admin.png");
        userService.createUser(accounts);
        userService.changeRoleToAdmin(accounts);
        //3
        /*User manager = new User(
                "doctor@gmail.com",
                "Doctor",
                "112233",
                "images/admin.png");
        userService.createUser(manager);
        userService.changeRoleToAdmin(manager);


        //3
        userService.createUser(new User(
                "mark@gmail.com",
                "Mark",
                "112233",
                "images/mark.jpg"));

        //4
        userService.createUser(new User(
                "ann@gmail.com",
                "Ann",
                "112233",
                "images/ann.jpg"));


*/
        userService.findAll().stream()
                .map(u -> "saved user: " + u.getName())
                .forEach(logger::info);


        //TASKS --------------------------------------------------------------------------------------------------------
        //tasks from Web Design Checklist
        //https://www.beewits.com/the-ultimate-web-design-checklist-things-to-do-when-launching-a-website/

        LocalDate today = LocalDate.now();

        /*// Task 1: Initial Consultation and Diagnosis
        taskService.createTask(new Task(
                "Initial Consultation and Diagnosis",
                "Conduct initial examination of the patient. Collect patient's medical history and current symptoms. Diagnose patient's condition and discuss potential treatment options.",
                today.minusDays(40),
                true,
                userService.getUserByEmail("ann@gmail.com").getName(),
                userService.getUserByEmail("ann@gmail.com"),
                "filename"
        ));
        new Task("Initial Consultation and Diagnosis", "Conduct initial examination of the patient. Collect patient's medical history and current symptoms. Diagnose patient's condition and discuss potential treatment options.", today.minusDays(40), true, "Ann", userService.getUserByEmail("ann@gmail.com"), "filename");

// Task 2: Prescription and Treatment Plan
        taskService.createTask(new Task(
                "Prescription and Treatment Plan",
                "Define and prescribe medication based on the diagnosis. Outline treatment plan including dosage, frequency, and duration of medication. Provide lifestyle and dietary recommendations. Schedule follow-up appointment to monitor progress.",
                today.minusDays(30),
                true,
                userService.getUserByEmail("mark@gmail.com").getName(),
                userService.getUserByEmail("mark@gmail.com"),
                "filename"
        ));



        taskService.findAll().stream().map(t -> "saved task: '" + t.getName()
                + "' for owner: " + getOwnerNameOrNoOwner(t)).forEach(logger::info);
    }

    private String getOwnerNameOrNoOwner(Task task) {
        return task.getOwner() == null ? "no owner" : task.getOwner().getName();*/
    }
}
