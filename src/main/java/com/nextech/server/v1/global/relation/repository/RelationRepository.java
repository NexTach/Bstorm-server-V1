package com.nextech.server.v1.global.relation.repository;

import com.nextech.server.v1.domain.members.entity.Members;
import com.nextech.server.v1.global.relation.entity.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationRepository extends JpaRepository<Relation, Long> {
    List<Relation> findByFromProtected(Members username);
    List<Relation> findByToWardContains(String phoneNumber);
    void deleteByFromProtected(Members fromProtected);
    void deleteByToWardContains(String phoneNumber);
}