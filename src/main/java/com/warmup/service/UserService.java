package com.warmup.service;

import com.warmup.dao.UserDao;
import com.warmup.dto.UserDto;
import com.warmup.entity.UserEntity;
import com.warmup.repository.UserRepository;
import com.warmup.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final UserDao userDao;
    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secretKey = "secret";

    @Value("${jwt.expiration}")
    private Long expiration = 1000 * 60 * 60L;



    public UserService(@Autowired UserDao userDao, UserRepository userRepository) {
        this.userDao = userDao;
        this.userRepository = userRepository;
    }

    public void createUser(UserDto userDto) {
        this.userDao.createUser(userDto);
    }


    public void loginUser(UserDto dto) {
        Optional<UserEntity> userEntity = this.userRepository.findByUserId(dto.getUserId());
        if (userEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        else if (!userEntity.get().getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public String login(String userId, String password) {

        return JwtUtil.createJwt(userId, secretKey, expiration);
    }

    public UserDto readUser(int id) {
        UserEntity userEntity = this.userDao.readUser(id);
        return new UserDto(
                userEntity.getUserId(),
                userEntity.getPassword(),
                userEntity.getUsername()
        );

    }

    public List<UserDto> readUserAll() {
        Iterator<UserEntity> iterator = this.userDao.readUserAll();
        List<UserDto> userDtoList = new ArrayList<>();
        while (iterator.hasNext()) {
            UserEntity userEntity = iterator.next();
            userDtoList.add(new UserDto(
                    userEntity.getUserId(),
                    userEntity.getPassword(),
                    userEntity.getUsername()
            ));
        }
        return userDtoList;
    }

    public void updateUser(int id, UserDto userDto) {
        this.userDao.updateUser(id, userDto);
    }

    public void deleteUser(int id) {
        this.userDao.deleteUser(id);
    }
}