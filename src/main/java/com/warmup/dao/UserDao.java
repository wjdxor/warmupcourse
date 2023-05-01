package com.warmup.dao;

import com.warmup.dto.UserDto;
import com.warmup.entity.UserEntity;
import com.warmup.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.Optional;

@Repository
public class UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
    private final UserRepository userRepository;

    public UserDao(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserDto dto) {
        UserEntity userEntity = new UserEntity(
            dto.getUserId(),
            dto.getPassword()
        );
        this.userRepository.save(userEntity);
    }


    public void loginUser(UserDto dto) {
        Optional<UserEntity> userEntity = this.userRepository.findByUserId(dto.getUserId());

        if (userEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else if (!userEntity.get().getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public UserEntity readUser(int id) {
        Optional<UserEntity> userEntity = this.userRepository.findById((long) id);
        if (userEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return userEntity.get();
    }

    public Iterator<UserEntity> readUserAll() {
        return this.userRepository.findAll().iterator();
    }

    public void updateUser(int id, UserDto dto) {
    }

    public void deleteUser(int id) {

    }
}