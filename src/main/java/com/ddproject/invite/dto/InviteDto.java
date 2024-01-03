package com.ddproject.invite.dto;

import com.ddproject.member.entity.BoardMemberEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InviteDto {
    @NotBlank
    private String recvUsername;;
    @NotBlank
    private Long boardId;
    @Builder.Default
    private BoardMemberEnum role = BoardMemberEnum.MEMBER;

}
