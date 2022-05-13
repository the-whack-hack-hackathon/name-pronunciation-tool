package com.hackathon.namepronunciationtool.repo;

import com.hackathon.namepronunciationtool.entity.UserDetails;
import com.yugabyte.data.jdbc.repository.YsqlRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserYsqlRepository extends YsqlRepository<UserDetails, Integer> {
    UserDetails findByUid(final String uid);
}
