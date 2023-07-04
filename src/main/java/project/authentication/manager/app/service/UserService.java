package project.authentication.manager.app.service;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.authentication.manager.app.model.RoleName;
import project.authentication.manager.app.model.User;

public interface UserService {
    User save(User user);

    User update(User user);

    List<User> findAllByParams(Map<String, String> params);

    Page<User> findAll(Pageable pageable);

    User findById(Long id);

    User findByEmail(String email);

    List<User> findByRoles(RoleName roleName);

    void delete(Long id);

}
