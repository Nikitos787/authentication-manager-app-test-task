package project.authentication.manager.app.service.impl;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.authentication.manager.app.model.Role;
import project.authentication.manager.app.model.RoleName;
import project.authentication.manager.app.repository.RoleRepository;
import project.authentication.manager.app.service.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findByRoleName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(()
                -> new NoSuchElementException(String
                .format("Can't find role by roleName: %s in DB", roleName.name())));
    }
}
