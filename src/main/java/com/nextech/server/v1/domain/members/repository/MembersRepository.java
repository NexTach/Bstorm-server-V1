package com.nextech.server.v1.domain.members.repository;

import com.nextech.server.v1.global.members.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRepository extends JpaRepository<Members, Long> {

}
