package com.ssg.jdbcex2.todo.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor // 모든 필드 값을 매개변수로 받는 생성자 생성
// 파라미터가 없는 기본 생성자 만들어줌.
// 스프링, JPA등 객체 생성시 기본 생성자 반드시 필요함
// DTO, VO는 프레임 워크에서 자동 생성될 일이 많아 필수적으로 기본 생성자 만들기.
@NoArgsConstructor
public class TodoVO {
    private Long tno;
    private String title;
    private LocalDateTime dueDate;
    private boolean finished;

}
