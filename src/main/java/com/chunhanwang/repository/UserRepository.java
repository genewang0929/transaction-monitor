package com.chunhanwang.repository;


import com.chunhanwang.entity.*;
import org.springframework.data.mongodb.repository.*;

public interface UserRepository extends MongoRepository<AppUser, String> {

}
