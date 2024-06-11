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
    @Value("${default.admin.department}")
    private String defaultAdminDepartment;
    @Value("${default.admin.school}")
    private String defaultAdminSchool;


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
                defaultAdminImage,
                defaultAdminDepartment,
                defaultAdminSchool
                );
        userService.createUser(admin);
        userService.changeRoleToAdmin(admin);

        User VChancellor = new User(
                "vchancellor@gmail.com",
                "Vice-Chancellor",
                "112233",
                "images/admin.png",
                "NULL",
                "NULL");
        userService.createUser(VChancellor);
        userService.changeRoleToAdmin(VChancellor);

        User dean = new User(
                "deansciencesmath@gmail.com",
                "dean(School of Natural Sciences and Mathematics)",
                "112233",
                "images/admin.png",
                "NULL",
                "School of Natural Sciences and Mathematics");
        userService.createUser(dean);
        userService.changeRoleToAdmin(dean);

        User dean1 = new User(
                "deanartdesign@gmail.com",
                "dean(School of Art and Design)",
                "112233",
                "images/admin.png",
                "NULL",
                "School of Art and Design");
        userService.createUser(dean1);
        userService.changeRoleToAdmin(dean1);

        User dean2 = new User(
                "deanbusiness@gmail.com",
                "dean(School of Entrepreneurship and Business Sciences)",
                "112233",
                "images/admin.png",
                "NULL",
                "School of Entrepreneurship and Business Sciences");
        userService.createUser(dean2);
        userService.changeRoleToAdmin(dean2);

        User dean3 = new User(
                "deanengscience@gmail.com",
                "dean(School of Engineering Science and Technology)",
                "112233",
                "images/admin.png",
                "NULL",
                "School of Engineering Science and Technology");
        userService.createUser(dean3);
        userService.changeRoleToAdmin(dean3);

        User dean4 = new User(
                "deangraduatebs@gmail.com",
                "dean(Graduate Business School)",
                "112233",
                "images/admin.png",
                "NULL",
                "Graduate Business School");
        userService.createUser(dean4);
        userService.changeRoleToAdmin(dean4);

        User dean5 = new User(
                "deanwildlifeenv@gmail.com",
                "dean(School of Wildlife and Environment Science)",
                "112233",
                "images/admin.png",
                "NULL",
                "School of Wildlife and Environment Science");
        userService.createUser(dean5);
        userService.changeRoleToAdmin(dean5);

        User dean6 = new User(
                "deantourism@gmail.com",
                "dean(School of Hospitality and Tourism)",
                "112233",
                "images/admin.png",
                "NULL",
                "School of Hospitality and Tourism");
        userService.createUser(dean6);
        userService.changeRoleToAdmin(dean6);

        User dean7 = new User(
                "deanlifelongdev@gmail.com",
                "dean(Institute of Lifelong Learning and Development Studies)",
                "112233",
                "images/admin.png",
                "NULL",
                "Institute of Lifelong Learning and Development Studies");
        userService.createUser(dean7);
        userService.changeRoleToAdmin(dean7);

        User dean8 = new User(
                "deanagrictechn@gmail.com",
                "dean(School of Agricultural Science and Technology)",
                "112233",
                "images/admin.png",
                "NULL",
                "School of Agricultural Science and Technology");
        userService.createUser(dean8);
        userService.changeRoleToAdmin(dean8);

        User dean9 = new User(
                "deanmatprocess@gmail.com",
                "dean(Institute of Materials Science, Processing and Engineering Technology)",
                "112233",
                "images/admin.png",
                "NULL",
                "Institute of Materials Science, Processing and Engineering Technology");
        userService.createUser(dean9);
        userService.changeRoleToAdmin(dean9);

        User dean10 = new User(
                "deanhealthtechn@gmail.com",
                "dean(School of Health Sciences and Technology)",
                "112233",
                "images/admin.png",
                "NULL",
                "School of Health Sciences and Technology");
        userService.createUser(dean10);
        userService.changeRoleToAdmin(dean10);


        User hod1= new User(
                "hodbiology@gmail.com",
                "hod(Department of Biology)",
                "112233",
                "images/admin.png",
                "Department of Biology",
                "School of Natural Sciences and Mathematics");
        userService.createUser(hod1);
        userService.changeRoleToAdmin(hod1);

        User hod2= new User(
                "hodphysics@gmail.com",
                "hod(Department of Physics)",
                "112233",
                "images/admin.png",
                "Department of Physics",
                "School of Natural Sciences and Mathematics");
        userService.createUser(hod2);
        userService.changeRoleToAdmin(hod2);

        User hod3= new User(
                "hodchemistry@gmail.com",
                "hod(Department of Chemistry)",
                "112233",
                "images/admin.png",
                "Department of Chemistry",
                "School of Natural Sciences and Mathematics ");
        userService.createUser(hod3);
        userService.changeRoleToAdmin(hod3);

        User hod4= new User(
                "hodmath@gmail.com",
                "hod(Department of Mathematics)",
                "112233",
                "images/admin.png",
                "Department of Mathematics",
                "School of Natural Sciences and Mathematics");
        userService.createUser(hod4);
        userService.changeRoleToAdmin(hod4);

        User hod5= new User(
                "hodartdesign@gmail.com",
                "hod(Creative Art and Design)",
                "112233",
                "images/admin.png",
                "Creative Art and Design",
                "School of Art and Design");
        userService.createUser(hod5);
        userService.changeRoleToAdmin(hod5);

        User hod6= new User(
                "hodtextile@gmail.com",
                "hod(Clothing and Textile Technology)",
                "112233",
                "images/admin.png",
                "Clothing and Textile Technology",
                "School of Art and Design");
        userService.createUser(hod6);
        userService.changeRoleToAdmin(hod6);

        User hod7= new User(
                "hodbmanagement@gmail.com",
                "hod(Entrepreneurship and Business Management)",
                "112233",
                "images/admin.png",
                "Entrepreneurship and Business Management",
                "School of Entrepreneurship and Business Sciences");
        userService.createUser(hod7);
        userService.changeRoleToAdmin(hod7);

       /* User hod8= new User(
                "hodaccounts@gmail.com",
                "hod(Accounting and Finance)",
                "112233",
                "images/admin.png",
                "Accounting and Finance",
                "School of Entrepreneurship and Business Sciences");
        userService.createUser(hod8);
        userService.changeRoleToAdmin(hod8);
*/


        User hod10= new User(
                "hodsupplychain@gmail.com",
                "hod(Supply Chain Management)",
                "112233",
                "images/admin.png",
                "Supply Chain Management",
                "School of Entrepreneurship and Business Sciences");
        userService.createUser(hod10);
        userService.changeRoleToAdmin(hod10);

        User hod11= new User(
                "hodmarketing@gmail.com",
                "hod(Marketing)",
                "112233",
                "images/admin.png",
                "Marketing",
                "School of Entrepreneurship and Business Sciences");
        userService.createUser(hod11);
        userService.changeRoleToAdmin(hod11);

        User hod12= new User(
                "hodscienceretail@gmail.com",
                "hod(Consumer Science and Retail Management)",
                "112233",
                "images/admin.png",
                "Consumer Science and Retail Management",
                "School of Entrepreneurship and Business Sciences");
        userService.createUser(hod12);
        userService.changeRoleToAdmin(hod12);

        User hod13= new User(
                "hodaccountfinance@gmail.com",
                "hod(Accounting and Finance)",
                "112233",
                "images/admin.png",
                "Accounting and Finance",
                "School of Entrepreneurship and Business Sciences");
        userService.createUser(hod13);
        userService.changeRoleToAdmin(hod13);

        User hod14= new User(
                "hodmechatronics@gmail.com",
                "hod(Mechatronics Engineering)",
                "112233",
                "images/admin.png",
                "Mechatronics Engineering",
                "School of Engineering Science and Technology");
        userService.createUser(hod14);
        userService.changeRoleToAdmin(hod14);

        User hod15= new User(
                "hodproductioneng@gmail.com",
                "hod(Production Engineering)",
                "112233",
                "images/admin.png",
                "Production Engineering",
                "School of Engineering Science and Technology");
        userService.createUser(hod15);
        userService.changeRoleToAdmin(hod15);

        User hod16= new User(
                "hodictelectronics@gmail.com",
                "hod(ICT and Electronics)",
                "112233",
                "images/admin.png",
                "ICT and Electronics",
                "School of Engineering Science and Technology");
        userService.createUser(hod16);
        userService.changeRoleToAdmin(hod16);

        User hod17= new User(
                "hodenveng@gmail.com",
                "hod(Environmental Engineering)",
                "112233",
                "images/admin.png",
                "Environmental Engineering",
                "School of Engineering Science and Technology");
        userService.createUser(hod17);
        userService.changeRoleToAdmin(hod17);

        User hod18= new User(
                "hodfuels@gmail.com",
                "hod(Fuels and Energy Engineering)",
                "112233",
                "images/admin.png",
                "Fuels and Energy Engineering",
                "School of Engineering Science and Technology");
        userService.createUser(hod18);
        userService.changeRoleToAdmin(hod18);

        User hod19= new User(
                "hodstrategic@gmail.com",
                "hod(Master of Science in Strategic Management)",
                "112233",
                "images/admin.png",
                "Master of Science in Strategic Management",
                "Graduate Business School");
        userService.createUser(hod19);
        userService.changeRoleToAdmin(hod19);

        User hod20= new User(
                "hodmastersupplychain@gmail.com",
                "hod(Master of Science in Supply Chain Management)",
                "112233",
                "images/admin.png",
                "Master of Science in Supply Chain Management",
                "Graduate Business School");
        userService.createUser(hod20);
        userService.changeRoleToAdmin(hod20);

        User hod21= new User(
                "hodappliedentrepreneurship@gmail.com",
                "hod(Master of Science in Applied Entrepreneurship and Business Management)",
                "112233",
                "images/admin.png",
                "Master of Science in Applied Entrepreneurship and Business Management",
                "Graduate Business School");
        userService.createUser(hod21);
        userService.changeRoleToAdmin(hod21);

        User hod22= new User(
                "hodbigdata@gmail.com",
                "hod(Master of Science in Big Data Analytics)",
                "112233",
                "images/admin.png",
                "Master of Science in Big Data Analytics",
                "Graduate Business School");
        userService.createUser(hod22);
        userService.changeRoleToAdmin(hod22);

        User hod23= new User(
                "hodmasteraccountancy@gmail.com",
                "hod(Master of Science in Accountancy)",
                "112233",
                "images/admin.png",
                "Master of Science in Accountancy",
                "Graduate Business School");
        userService.createUser(hod23);
        userService.changeRoleToAdmin(hod23);

        User hod24= new User(
                "hodmastermarketing@gmail.com",
                "hod(Master of Science in International Marketing)",
                "112233",
                "images/admin.png",
                "Master of Science in International Marketing",
                "Graduate Business School");
        userService.createUser(hod24);
        userService.changeRoleToAdmin(hod24);

        User hod25= new User(
                "hodmasterstrategic@gmail.com",
                "hod(Master of Strategic Management)",
                "112233",
                "images/admin.png",
                "Master of Strategic Management",
                "Graduate Business School");
        userService.createUser(hod25);
        userService.changeRoleToAdmin(hod25);

        User hod26= new User(
                "hodecology@gmail.com",
                "hod(Department of Wildlife Ecology and Conservation)",
                "112233",
                "images/admin.png",
                "Department of Wildlife Ecology and Conservation",
                "School of Wildlife and Environment Science");
        userService.createUser(hod26);
        userService.changeRoleToAdmin(hod26);

        User hod27= new User(
                "hodfishery@gmail.com",
                "hod(Department of Freshwater and Fishery Science)",
                "112233",
                "images/admin.png",
                "Department of Freshwater and Fishery Science",
                " School of Wildlife and Environment Science");
        userService.createUser(hod27);
        userService.changeRoleToAdmin(hod27);

        User hod28= new User(
                "hodgeoinformatics@gmail.com",
                "hod(Environmental Conservation and GeoInformatics)",
                "112233",
                "images/admin.png",
                "Environmental Conservation and GeoInformatics",
                "School of Wildlife and Environment Science");
        userService.createUser(hod28);
        userService.changeRoleToAdmin(hod28);

        User hod29= new User(
                "hodenvtechno@gmail.com",
                "hod(Environmental Science and Technology)",
                "112233",
                "images/admin.png",
                "Environmental Science and Technology",
                "School of Wildlife and Environment Science");
        userService.createUser(hod29);
        userService.changeRoleToAdmin(hod29);

        User hod30= new User(
                "hodtourismhosp@gmail.com",
                "hod(Department of Hospitality and Tourism)",
                "112233",
                "images/admin.png",
                "Department of Hospitality and Tourism",
                "School of Hospitality and Tourism");
        userService.createUser(hod30);
        userService.changeRoleToAdmin(hod30);

        User hod31= new User(
                "hodtravel@gmail.com",
                "hod(Department of Travel and Recreation)",
                "112233",
                "images/admin.png",
                "Department of Travel and Recreation",
                "School of Hospitality and Tourism");
        userService.createUser(hod31);
        userService.changeRoleToAdmin(hod31);

        User hod32= new User(
                "hodlivingheritage@gmail.com",
                "hod(Centre for Indigenous Knowledge and Living Heritage)",
                "112233",
                "images/admin.png",
                "Centre for Indigenous Knowledge and Living Heritage",
                "Institute of Lifelong Learning and Development Studies");
        userService.createUser(hod32);
        userService.changeRoleToAdmin(hod32);

        User hod33= new User(
                "hodcentredev@gmil.com",
                "hod(Centre For Development Studies)",
                "112233",
                "images/admin.png",
                "Centre For Development Studies",
                " Institute of Lifelong Learning and Development Studies");
        userService.createUser(hod33);
        userService.changeRoleToAdmin(hod33);

        User hod34= new User(
                "hodlangcomm@gmail.com",
                "hod(Centre For Language and Communication Studies)",
                "112233",
                "images/admin.png",
                "Centre For Language and Communication Studies",
                " Institute of Lifelong Learning and Development Studies");
        userService.createUser(hod34);
        userService.changeRoleToAdmin(hod34);

        User hod35= new User(
                "hodprocesseng@gmail.com",
                "hod(Materials Science,Processing and Engineering Technology)",
                "112233",
                "images/admin.png",
                "Materials Science,Processing and Engineering Technology",
                "Institute of Materials Science, Processing and Engineering Technology");
        userService.createUser(hod35);
        userService.changeRoleToAdmin(hod35);

        User hod36= new User(
                "hodbiotechn@gmail.com",
                "hod(Biotechnology)",
                "112233",
                "images/admin.png",
                "Biotechnology",
                " School of Health Sciences and Technology ");
        userService.createUser(hod36);
        userService.changeRoleToAdmin(hod36);

        User hod37= new User(
                "hodagriceng@gmail.com",
                "hod(Agricultural Engineering)",
                "112233",
                "images/admin.png",
                "Agricultural Engineering",
                "School of Agricultural Science and Technology");
        userService.createUser(hod37);
        userService.changeRoleToAdmin(hod37);

        User hod38= new User(
                "hodfoodtech@gmail.com",
                "hod(Food Science and Technology)",
                "112233",
                "images/admin.png",
                "Food Science and Technology",
                " School of Agricultural Science and Technology");
        userService.createUser(hod38);
        userService.changeRoleToAdmin(hod38);

        User hod39= new User(
                "hodcropharvest@gmail.com",
                "hod(Crop Science and Post Harvest Technology)",
                "112233",
                "images/admin.png",
                "Crop Science and Post Harvest Technology",
                "School of Agricultural Science and Technology");
        userService.createUser(hod39);
        userService.changeRoleToAdmin(hod39);

        User hod40= new User(
                "hodanimalprod@gmail.com",
                "hod(School of Agricultural Science and Technology)",
                "112233",
                "images/admin.png",
                "Animal Production and Technology",
                "School of Agricultural Science and Technology");
        userService.createUser(hod40);
        userService.changeRoleToAdmin(hod40);

        User accounts= new User(
                "accounts@gmail.com",
                "accounts",
                "112233",
                "images/admin.png",
                "NULL",
                "NULL");
        userService.createUser(accounts);
        userService.changeRoleToAdmin(accounts);



        userService.findAll().stream()
                .map(u -> "saved user: " + u.getName())
                .forEach(logger::info);

        //TASKS --------------------------------------------------------------------------------------------------------
        LocalDate today = LocalDate.now();

        /* Example Tasks
        taskService.createTask(new Task(
                "Task Name",
                "Task Description",
                today.minusDays(10),
                true,
                userService.getUserByEmail("user@example.com").getName(),
                userService.getUserByEmail("user@example.com"),
                "filename"
        ));
        */
        taskService.findAll().stream().map(t -> "saved task: '" + t.getName()
                + "' for owner: " + getOwnerNameOrNoOwner(t)).forEach(logger::info);
    }

    private String getOwnerNameOrNoOwner(Task task) {
        return task.getOwner() == null ? "no owner" : task.getOwner().getName();
    }
}





/*
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

        */
/*User VC = new User(
                "vchancellor@gmail.com",
                "Vice-Chancellor",
                "112233",
                "images/admin.png");
        userService.createUser(VC);
        userService.changeRoleToAdmin(VC);*//*

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
                "hod(IT)",
                "112233",
                "images/admin.png");
        userService.createUser(hod);
        userService.changeRoleToAdmin(hod);

        User hod1= new User(
                "hodmecha@gmail.com",
                "hod(Mechatronics)",
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
        */
/*User manager = new User(
                "james@gmail.com",
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


*//*

        userService.findAll().stream()
                .map(u -> "saved user: " + u.getName())
                .forEach(logger::info);


        //TASKS --------------------------------------------------------------------------------------------------------
        //tasks from Web Design Checklist
        //https://www.beewits.com/the-ultimate-web-design-checklist-things-to-do-when-launching-a-website/

        LocalDate today = LocalDate.now();

        */
/*//*
/ Task 1: Initial Consultation and Diagnosis
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
        return task.getOwner() == null ? "no owner" : task.getOwner().getName();*//*

    }
}
*/
