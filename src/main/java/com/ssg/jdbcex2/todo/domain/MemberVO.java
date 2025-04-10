package com.ssg.jdbcex2.todo.domain;

import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
    private String uuid;
private String mid;
private String mname;
private String mpw;

}
