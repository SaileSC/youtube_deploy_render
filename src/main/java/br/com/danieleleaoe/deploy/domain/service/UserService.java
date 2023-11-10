package br.com.danieleleaoe.deploy.domain.service;

import java.util.List;
import java.util.stream.Collectors;
import br.com.danieleleaoe.deploy.domain.dto.ResponseDto;
import br.com.danieleleaoe.deploy.domain.dto.UserDto;
import br.com.danieleleaoe.deploy.domain.entity.User;
import br.com.danieleleaoe.deploy.domain.enumeration.Status;
import br.com.danieleleaoe.deploy.domain.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ResponseDto responseDto;

    UserRepository userRepository;

    public ResponseDto saveUser(User user) {
        responseDto.setId(userRepository.save(user).getId());
        responseDto.setMenssage("Usuário incluído com sucesso...");
        responseDto.setStatus(Status.SUCCESS.value());
        return responseDto;
    }

    public List<UserDto> getAllUser() {
        List<UserDto> listAllUserDto = userRepository.findAll().stream()
                .map(User -> modelMapper.map(User, UserDto.class))
                .collect(Collectors.toList());
        return listAllUserDto;
    }

    public List<UserDto> getAllUserOrderByName() {
        List<UserDto> listAllUserDto = userRepository.findAll(Sort.by(Sort.Direction.ASC, "nome")).stream()
                .map(User -> modelMapper.map(User, UserDto.class))
                .collect(Collectors.toList());
        return listAllUserDto;
    }

    public UserDto getUserById(Long id) {
        return modelMapper.map(userRepository.findById(id).get(), UserDto.class);
    }

    public ResponseDto updateUser(User user) {
        responseDto.setId(userRepository.save(user).getId());
        if (user.getId() > 0) {
            userRepository.save(user);
            responseDto.setMenssage("Usuário alterado com sucesso...");
            responseDto.setStatus(Status.SUCCESS.value());
        } else {
            responseDto.setMenssage("ID do Usuário inválido...");
            responseDto.setStatus(Status.ERROR.value());
        }
        return responseDto;
    }

    public ResponseDto deleteUser(Long id) {
        responseDto.setId(id);
        if (id > 0) {
            userRepository.deleteById(id);
            responseDto.setMenssage("Usuário deletado com sucesso...");
            responseDto.setStatus(Status.SUCCESS.value());
        } else {
            responseDto.setMenssage("ID do Usuário inválido...");
            responseDto.setStatus(Status.ERROR.value());
        }
        return responseDto;
    }

    public UserDto validateLogin(String login, String password) {
        return modelMapper.map(userRepository.findUserByLoginAndPassword(login, password), UserDto.class);
    }

}