package com.nextech.server.v1.domain.mission.repository;

import com.nextech.server.v1.domain.mission.dto.enums.Status;
import com.nextech.server.v1.domain.mission.entity.Mission;
import com.nextech.server.v1.global.members.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByFromId(Long from_id);
    @Query("SELECT m FROM Mission m JOIN m.status s WHERE m.toWard.id = :toWardId AND s IN :statuses")
    List<Mission> findByToWardIdAndStatusIn(@Param("toWardId") Long toWardId, @Param("statuses") List<Status> statuses);
    List<Mission> findByFromAndStatusContains(Members member, Status status);
    List<Mission> findByFromAndToWard(Members from, Members toWard);
    List<Mission> findByFrom(Members from);
}
