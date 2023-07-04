package project.authentication.manager.app.dto.response;

import java.util.Set;
import lombok.Data;
import project.authentication.manager.app.model.Role;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
}
