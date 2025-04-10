package com.ssg.jdbcex2.todo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder // 세터 없이 빌더로 초기값 지정 가능
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {
    private Long tno;
    private String title;
    private LocalDateTime dueDate;
    private boolean finished;

    @Override
    public String toString() {
        return "TodoDTO{" +
                "tno=" + tno +
                ", title='" + title + '\'' +
                ", dueDate=" + dueDate +
                ", finished=" + finished +
                '}';
    }
}

/*
 * 언제 DTO 를 사용하는지?
 * -> 컨트롤러나 서비스에서 응답 객체 만들때
 *  테스트 코드에서 임시 객체 만들때
 *  엔티티 -> dto 변환할때
 *
 * 필드명 바꾸거나 일부 필드만 담을 수 있음
 * 사용자에게 보여줄 데이터 구조: todoDTO
 * 디비에서 읽어온 구조 TodoVO
 * 서비스에서 둘을 변환해 사용함
 * 프론트에서 원하는 형태로 데이터 필드 가공하여 보낼 수 있음. 
 */