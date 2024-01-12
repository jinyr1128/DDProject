package com.ddproject.alarm.entity;

import com.ddproject.alarm.args.AlarmArgs;
import com.ddproject.alarm.enums.AlarmType;
import com.ddproject.user.entity.User;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {@Index(name = "user_id_idx", columnList = "user_id")})
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private AlarmArgs args;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AlarmType alarmType;


}
