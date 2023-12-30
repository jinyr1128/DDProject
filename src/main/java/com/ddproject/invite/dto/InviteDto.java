package com.ddproject.invite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InviteDto {
    private String sendUsername;
    private String recvUsername;;
    private String boardKey;


}
