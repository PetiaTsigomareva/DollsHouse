package petia.com.dollhouse.service;

import com.petia.dollhouse.domain.service.UserServiceModel;
import com.petia.dollhouse.repositories.RoleRepository;
import com.petia.dollhouse.repositories.UserRepository;
import com.petia.dollhouse.service.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserServiceTests {

    private UserService userService;
    private UserServiceModel testUser;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleService roleService;
    private OfficeService officeService;
    private DollHouseService serviceService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.roleService = new RoleServiceImpl(this.roleRepository, this.modelMapper);
        this.userService = new UserServiceImpl(this.userRepository, this.roleService, this.officeService,this.serviceService,this.modelMapper, this.bCryptPasswordEncoder);
        this.testUser = createTestUser();
    }



    @Test
    public void T01_userService_registerUser_with_correct_data_return_ok() {
        this.userService.registerUser(testUser);
        long expected = this.userRepository.findAll().size();

        Assert.assertEquals(1, expected);
    }

    @Test
    public void T02_userService_findByUsername_with_correct_data_return_ok() {
        this.userService.registerUser(testUser);
        UserServiceModel expected = this.userService.findUserByUserName(testUser.getUsername());

        Assert.assertEquals(testUser.getUsername(), expected.getUsername());
    }

    @Test(expected = Exception.class)
    public void T03_userService_findByUsername_with_incorrect_data_throw_exception() {
        this.userService.registerUser(testUser);
        this.userService.findUserByUserName("invalid username");
    }

//    @Test
//    public void T04_userService_findAllUsers_with_correct_data_return_ok() {
//        this.userService.registerUser(testUser);
//        long expected = this.userService.findA().size();
//
//        Assert.assertEquals(1, expected);
//    }






    private UserServiceModel createTestUser() {

        UserServiceModel testUser = new UserServiceModel();
        testUser.setUsername("john");
        testUser.setPassword("1");
        testUser.setEmail("valid@mail.com");

        return testUser;
    }
}
