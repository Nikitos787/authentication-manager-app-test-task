package project.authentication.manager.app.service;

import java.util.List;
import project.authentication.manager.app.model.RoleName;
import project.authentication.manager.app.model.User;

public interface UserService {
    User save(User user);

    User update(User user);

    User findById(Long id);

    User findByEmail(String email);

    List<User> findByRoles(RoleName roleName);

}
