package project.authentication.manager.app.security.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.authentication.manager.app.exception.AuthenticationException;
import project.authentication.manager.app.model.RoleName;
import project.authentication.manager.app.model.User;
import project.authentication.manager.app.security.AuthenticationService;
import project.authentication.manager.app.service.RoleService;
import project.authentication.manager.app.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public User register(String email, String password, String firstName, String lastName) {
        User userToSave = new User();
        userToSave.setEmail(email);
        userToSave.setPassword(password);
        userToSave.setFirstName(firstName);
        userToSave.setLastName(lastName);
        userToSave.setRoles(Set.of(roleService.findByRoleName(RoleName.CUSTOMER)));
        return userService.save(userToSave);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Incorrect email or password");
        }
        return user;
    }
}
