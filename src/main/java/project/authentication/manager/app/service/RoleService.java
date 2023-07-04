package project.authentication.manager.app.service;

import project.authentication.manager.app.model.Role;
import project.authentication.manager.app.model.RoleName;

public interface RoleService {
    Role save(Role role);

    Role findByRoleName(RoleName roleName);
}
