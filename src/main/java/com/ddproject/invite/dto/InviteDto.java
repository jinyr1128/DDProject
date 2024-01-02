package com.ddproject.invite.dto;

import com.ddproject.member.entity.BoardMemberEnum;
import lombok.*;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InviteDto {
    private String sendUsername;
    private String recvUsername;;
    private Long boardId;
    private BoardMemberEnum role;

}
