package com.warmup.service;

import com.warmup.dao.UserDao;
import com.warmup.dto.UserDto;
import com.warmup.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final UserDao userDao;

    public UserService(@Autowired UserDao userDao) {
        this.userDao = userDao;
    }

    public  void createUser(UserDto userDto){
        this.userDao.createUser(userDto);
    }

    public UserDto readUser(int id){
        UserEntity userEntity = this.userDao.readUser(id);
        UserDto userDto = new UserDto(
                Math.toIntExact(userEntity.getId()),
                userEntity.getUsername()
        );
        return userDto;
    }

    public List<UserDto> readUserAll(){
        Iterator<UserEntity> iterator = this.userDao.readUserAll();
        List<UserDto> userDtoList = new ArrayList<>();
        while (iterator.hasNext()){
            UserEntity userEntity = iterator.next();
            userDtoList.add(new UserDto(
                    Math.toIntExact(userEntity.getId()),
                    userEntity.getUsername()
            ));
        }
        return userDtoList;
    }

    public void updateUser(int id, UserDto userDto){
        this.userDao.updateUser(id, userDto);
    }

    public void deleteUser(int id){
        this.userDao.deleteUser(id);
    }
}