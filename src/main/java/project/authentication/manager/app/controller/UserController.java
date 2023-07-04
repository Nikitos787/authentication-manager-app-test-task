package project.authentication.manager.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.authentication.manager.app.dto.request.UserRequestDto;
import project.authentication.manager.app.dto.response.UserResponseDto;
import project.authentication.manager.app.model.Role;
import project.authentication.manager.app.model.RoleName;
import project.authentication.manager.app.model.User;
import project.authentication.manager.app.service.RoleService;
import project.authentication.manager.app.service.UserService;
import project.authentication.manager.app.service.mapper.UserMapper;
import project.authentication.manager.app.util.RequestParamParser;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @GetMapping
    @Operation(summary = "Endpoint to get all users with pagination and sorting")
    public List<UserResponseDto> findAll(
            @Parameter(description = "Number of users per page")
            @RequestParam(defaultValue = "10") Integer count,
            @Parameter(description = "Page number")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Sorting type (ASC or DESC)")
            @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = Sort.by(RequestParamParser.toSortOrders(sortBy));
        Pageable pageable = PageRequest.of(page, count, sort);
        return userService.findAll(pageable).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-params")
    @Operation(summary = "Endpoint to get users by filtering their characteristics")
    public List<UserResponseDto> findAllByParams(
            @Parameter(description = "Filtering by firstName. For example: firstNameIn=Nikita")
            @RequestParam Map<String, String> params) {
        return userService.findAllByParams(params).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Endpoint to get an user by ID")
    public UserResponseDto getById(
            @Parameter(description = "User ID")
            @PathVariable Long id) {
        return userMapper.toDto(userService.findById(id));
    }

    @GetMapping("/me")
    @Operation(summary = "Endpoint to get your own information")
    public UserResponseDto getMe(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userMapper.toDto(userService.findByEmail(userDetails.getUsername()));
    }

    @PutMapping("/{id}/role")
    @Operation(summary = "Endpoint for updating the role of a user by a manager")
    public UserResponseDto updateRole(
            @Parameter(description = "User ID")
            @PathVariable Long id,
            @Parameter(description = "Role Name")
            @RequestParam String role) {
        User userById = userService.findById(id);
        Role byRoleName = roleService.findByRoleName(RoleName.valueOf(role));
        Set<Role> roles = new HashSet<>();
        roles.add(byRoleName);
        userById.setRoles(roles);
        return userMapper.toDto(userService.update(userById));
    }

    @PutMapping("/me")
    @Operation(summary = "Endpoint to update information")
    public UserResponseDto updateInfo(Authentication authentication,
                                      @Parameter(schema = @Schema(implementation =
                                              UserRequestDto.class))
                                      @RequestBody UserRequestDto userRequestDto) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User userToUpdate = userService.findByEmail(userDetails.getUsername());
        User user = userMapper.toModel(userRequestDto);
        user.setId(userToUpdate.getId());
        user.setRoles(userToUpdate.getRoles());
        return userMapper.toDto(userService.update(user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Endpoint for safe delete user")
    public void delete(
            @Parameter(description = "User ID")
            @PathVariable Long id) {
        userService.delete(id);
    }
}
