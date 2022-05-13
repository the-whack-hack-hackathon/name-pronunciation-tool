package com.hackathon.namepronunciationtool.repo;

import com.hackathon.namepronunciationtool.entity.UserDetails;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserDetails, Integer> {
    UserDetails findByUid(final String uid);
}
