package project.authentication.manager.app.service.mapper;

import org.mapstruct.Mapper;
import project.authentication.manager.app.dto.request.UserRequestDto;
import project.authentication.manager.app.dto.response.UserResponseDto;
import project.authentication.manager.app.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRequestDto dto);
}
