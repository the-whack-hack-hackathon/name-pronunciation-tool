package com.hackathon.namepronunciationtool.repo;

import com.hackathon.namepronunciationtool.entity.Voice;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoiceRepository extends PagingAndSortingRepository<Voice, Integer> {
}
