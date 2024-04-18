package com.monocept.ruleexecutor.repository;

import com.monocept.ruleexecutor.entity.NudgeStorage;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface NudgeStorageRepository extends CrudRepository<NudgeStorage, UUID>, JpaSpecificationExecutor<NudgeStorage> {

    @Query(value = "SELECT * from nudge_storage where nudge_id = ?1 and source = ?2", nativeQuery = true)
    List<NudgeStorage> getNudgeStorageByNudgeIdSource(String nudgeId, String source);

    @Query(value = "SELECT * from nudge_storage where nudge_id = ?1", nativeQuery = true)
    List<NudgeStorage> getNudgeStorageByNudgeId(String nudgeId);

    @Query(value = "SELECT * from nudge_storage where source = ?1", nativeQuery = true)
    List<NudgeStorage> getNudgeStorageBySource(String source);

    @Query(value = "SELECT * from nudge_storage where process_id = ?1", nativeQuery = true)
    List<NudgeStorage> getNudgeStorageByProcessId(String processId);
}
