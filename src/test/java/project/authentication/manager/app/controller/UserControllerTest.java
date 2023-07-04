package project.authentication.manager.app.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import project.authentication.manager.app.config.SecurityConfig;
import project.authentication.manager.app.dto.request.UserRequestDto;
import project.authentication.manager.app.model.Role;
import project.authentication.manager.app.model.RoleName;
import project.authentication.manager.app.model.User;
import project.authentication.manager.app.service.RoleService;
import project.authentication.manager.app.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class UserControllerTest {
    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private Authentication authentication;

    @MockBean
    private UserDetails userDetails;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void updateRole_ok() {
        Role customer = new Role(1L, RoleName.CUSTOMER);
        Role manager = new Role(1L, RoleName.MANAGER);
        User userBeforeUpdate = new User(1L, "user@i.ua", "Nikita", "Salohub",
                "11111111", Set.of(customer), false);

        User userAfterUpdate = new User(1L, "user@i.ua", "Nikita", "Salohub",
                "11111111", Set.of(manager), false);
        when(userService.findById(1L)).thenReturn(userBeforeUpdate);
        when(roleService.findByRoleName(RoleName.MANAGER)).thenReturn(manager);
        when(userService.update(userBeforeUpdate)).thenReturn(userAfterUpdate);

        RestAssuredMockMvc.given()
                .queryParam("role", "MANAGER")
                .when()
                .put("/users/1/role")
                .then()
                .statusCode(200);
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void shouldReturnMe_ok() {
        Role manager = new Role(1L, RoleName.MANAGER);

        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(userService.findByEmail(anyString()))
                .thenReturn(new User(1L, "nikitosik@i.ua", "Nikita", "Salohub",
                        "11111111", Set.of(manager), false));

        RestAssuredMockMvc
                .when()
                .get("/users/me")
                .then()
                .statusCode(200);
    }


    @Test
    @WithMockUser(roles = {"MANAGER", "CUSTOMER"})
    public void updateInfo_ok() {
        Role customer = new Role(1L, RoleName.CUSTOMER);
        User userBeforeUpdate = new User(1L, "manager@i.ua", "Bob", "Bobson",
                "11111111", Set.of(customer), false);

        User userAfterUpdate = new User(1L, "manager@i.ua", "Bob", "Bobsonyuk",
                "11111111", Set.of(customer), false);

        when(userService.findByEmail(anyString()))
                .thenReturn(userBeforeUpdate);
        when(userService.update(Mockito.any(User.class)))
                .thenReturn(userAfterUpdate);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new UserRequestDto(userAfterUpdate.getEmail(),
                        userAfterUpdate.getPassword(), userAfterUpdate.getFirstName(),
                        userAfterUpdate.getLastName()))
                .when()
                .put("/users/me")
                .then()
                .statusCode(200);
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void findAll_ok() {
        // Підготовка тестових даних
        List<User> users = Arrays.asList(
                new User(1L, "user1@example.com", "John", "Doe", "password", null, false),
                new User(2L, "user2@example.com", "Jane", "Smith", "password", null, false),
                new User(3L, "user3@example.com", "Bob", "Johnson", "password", null, false)
        );

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        Page<User> userPage = new PageImpl<>(users, pageable, users.size());
        when(userService.findAll(pageable)).thenReturn(userPage);

        RestAssuredMockMvc.given()
                .when()
                .get("/users")
                .then()
                .statusCode(200);
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void findAllByParams_ok() {
        // Підготовка тестових даних
        List<User> users = Arrays.asList(
                new User(1L, "user1@example.com", "John", "Doe", "password", null, false),
                new User(2L, "user2@example.com", "Jane", "Smith", "password", null, false)
        );

        Map<String, String> params = new HashMap<>();
        params.put("firstNameIn", "John");
        when(userService.findAllByParams(params)).thenReturn(users);

        RestAssuredMockMvc.given()
                .queryParams(params)
                .when()
                .get("/users/by-params")
                .then()
                .statusCode(200);
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void getById_ok() {
        Long userId = 1L;
        User user = new User(userId, "user1@example.com", "John", "Doe", "password", null, false);

        when(userService.findById(userId)).thenReturn(user);

        RestAssuredMockMvc.given()
                .when()
                .get("/users/{id}", userId)
                .then()
                .statusCode(200);
    }
}
