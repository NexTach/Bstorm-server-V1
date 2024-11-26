package com.nextech.server.v1.domain.mission.repository;

import com.nextech.server.v1.domain.mission.dto.enums.Status;
import com.nextech.server.v1.domain.mission.entity.Mission;
import com.nextech.server.v1.global.members.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByFromId(Members member);
    List<Mission> findByToWardAndStatus(Members toWard, Status status);
    List<Mission> findByFromAndStatusContains(Members member, Status status);
    List<Mission> findByFromAndToWard(String from, Members toWard);
}
