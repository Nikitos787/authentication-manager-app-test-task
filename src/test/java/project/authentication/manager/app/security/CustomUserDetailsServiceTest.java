package project.authentication.manager.app.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import project.authentication.manager.app.model.Role;
import project.authentication.manager.app.model.RoleName;
import project.authentication.manager.app.model.User;
import project.authentication.manager.app.service.UserService;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    private static final String EMAIL = "bob@i.ua";
    private static final String PASSWORD = "111111111";

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    private UserService userService;
    private User user;

    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setId(1L);
        role.setRoleName(RoleName.CUSTOMER);
        user = new User();
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setRoles(Set.of(role));
    }

    @Test
    void loadUserByUsername_Ok() {
        when(userService.findByEmail(EMAIL)).thenReturn(user);

        UserDetails actual = customUserDetailsService.loadUserByUsername(EMAIL);
        assertNotNull(actual);
        assertEquals(EMAIL, actual.getUsername());
        assertEquals(PASSWORD, actual.getPassword());
    }

    @Test
    void loadUserByUsername_UserNotFound() {
        when(userService.findByEmail(EMAIL)).thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(EMAIL);
        }, "UsernameNotFoundException expected");
    }
}
