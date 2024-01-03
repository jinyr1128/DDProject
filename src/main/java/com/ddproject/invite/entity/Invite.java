package com.ddproject.invite.entity;

import com.ddproject.member.entity.BoardMemberEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long boardId;
    private String sendUsername;
    private String recvUsername;
    @Enumerated(value = EnumType.STRING)
    private BoardMemberEnum role;


}
