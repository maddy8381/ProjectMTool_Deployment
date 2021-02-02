package com.maddy8381.PersonalProjectMngmTool.repositories;

import com.maddy8381.PersonalProjectMngmTool.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
    User getById(Long id);
}
