package com.krish.bcbs.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.krish.bcbs.model.UserDao;
public interface UserRepository extends MongoRepository<UserDao, String> {
    //UserDao findByUsername(String username);
    
	UserDao findByUsername(String username);

    Boolean existsByUsername(String username);
}