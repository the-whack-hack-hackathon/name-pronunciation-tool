package com.hackathon.namepronunciationtool.repo;

import com.hackathon.namepronunciationtool.entity.UserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDetails, String> {
    UserDetails findByUid(final String uid);
    long deleteByUid(String uid);
}
