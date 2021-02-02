package com.maddy8381.PersonalProjectMngmTool.repositories;

import com.maddy8381.PersonalProjectMngmTool.domain.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {

    Backlog findByProjectIdentifier(String Identifier);
}
