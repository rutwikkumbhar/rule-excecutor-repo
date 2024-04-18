package com.monocept.ruleexecutor.repository;

import com.monocept.ruleexecutor.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserDetailsRepository extends CrudRepository<UserDetails, String>, JpaSpecificationExecutor<UserDetails> {
}
