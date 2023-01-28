package com.artzhelt.sweater.repo;

import com.artzhelt.sweater.domain.SweaterUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<SweaterUser, Long> {

    Optional<SweaterUser> findByUsername(String username);

    Boolean existsByUsername(String username);

}
