package com.ssg.jdbcex2.todo.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String uuid;
    private String mid;
    private String mpw;
    private String mname;

}
