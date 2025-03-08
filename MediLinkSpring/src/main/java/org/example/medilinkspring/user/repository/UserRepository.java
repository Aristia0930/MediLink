package org.example.medilinkspring.user.repository;

import org.example.medilinkspring.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
//    List<User> findByName(String name);

    User findByName(String name);
}
