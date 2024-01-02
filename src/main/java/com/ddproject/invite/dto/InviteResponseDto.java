package com.ddproject.invite.dto;

import com.ddproject.member.entity.BoardMemberEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteResponseDto {
    private String sendUsername;
    private String recvUsername;;
    private Long boardId;
    private BoardMemberEnum role;
}
