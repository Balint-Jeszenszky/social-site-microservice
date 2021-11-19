package hu.bme.aut.thesis.microservice.auth.mapper;

import hu.bme.aut.thesis.microservice.auth.model.Role;
import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.models.PublicUserDetailsDto;
import hu.bme.aut.thesis.microservice.auth.models.UserDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDetailsDto userToUserDetailsDto(User user);

    PublicUserDetailsDto userToPublicUserDetailsDto(User user);

    default List<String> mapRoles(Set<Role> roles) {
        return roles.stream().map(role -> role.getName().name()).collect(Collectors.toList());
    }
}
