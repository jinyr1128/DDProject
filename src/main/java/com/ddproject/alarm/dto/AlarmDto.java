package com.ddproject.alarm.dto;

import com.ddproject.alarm.args.AlarmArgs;
import com.ddproject.alarm.enums.AlarmType;
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
