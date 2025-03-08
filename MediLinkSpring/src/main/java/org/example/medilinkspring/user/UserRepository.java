package org.example.medilinkspring.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User,String> {
//    List<User> findByName(String name);

    User findByName(String name);
}
