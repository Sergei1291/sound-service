package com.epam.microservices.repository;

import com.epam.microservices.dto.SoundMetadata;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<SoundMetadata, Long> {

}
