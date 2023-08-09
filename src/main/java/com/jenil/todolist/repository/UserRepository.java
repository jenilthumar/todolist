package com.jenil.todolist.repository;

import com.jenil.todolist.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {

    /*@Query("{firstName:'?0'}")
    User findUserByFirstName(String firstName);

    @Query("{lastName:'?0'}")
    User findUserByLastName(String lastName);

    public long count();*/
}
