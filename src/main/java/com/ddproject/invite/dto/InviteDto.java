package com.ddproject.invite.dto;

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

}
