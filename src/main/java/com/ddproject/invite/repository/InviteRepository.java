package com.ddproject.invite.repository;


import com.ddproject.invite.dto.InviteDto;
import com.ddproject.invite.entity.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InviteRepository extends JpaRepository<Invite, Long> {
    List<Invite> findAllByRecvUsername(String username);
}
