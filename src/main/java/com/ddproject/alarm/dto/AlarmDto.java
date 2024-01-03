package com.ddproject.alarm.dto;

import com.ddproject.alarm.model.AlarmArgs;
import com.ddproject.alarm.model.AlarmType;
import com.ddproject.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class AlarmDto {
    private Long id;
    private String text;
    private AlarmArgs alarmArgs;
    private AlarmType alarmType;





}
