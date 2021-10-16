package hu.bme.aut.thesis.microservice.auth.mapper;

import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.models.PublicUserDetailsDto;
import hu.bme.aut.thesis.microservice.auth.models.UserDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDetailsDto userToUserDetailsDto(User user);

    PublicUserDetailsDto userToPublicUserDetailsDto(User user);
}
