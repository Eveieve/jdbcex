package com.ssg.jdbcex2.todo.dao;

import com.ssg.jdbcex2.todo.domain.TodoVO;
import lombok.Cleanup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * tbl_todo 테이블과 통신하는 DAO클래스
 */
public class TodoDAO {

    // 디비에서 현재시간을 조회하는 메서드
    public String getTime() {
        // 현재 시간을 저장할 변수
        String now = null;

        try (Connection conn = ConnectionUtil.INSTANCE.getConnection(); // 커넥션 풀에서 커넥션 가져오기
             PreparedStatement ps = conn.prepareStatement("select now()"); // sql 문 준비하기
             ResultSet rs = ps.executeQuery()) { // 실행한 결과를 받기

            rs.next(); // 결과로 받은 SET 의 첫줄로 이동하기
            now = rs.getString(1); // 이동한 후, 첫 컬럼을 문자열로 꺼냄

        } catch (Exception e) { // 예외 처리 후
            e.printStackTrace(); // 예외 로깅
        }

        // 꺼낸(조회된) 시간을 반환
        return now;
    }

    public String getTime2() throws SQLException {
        String now = null;
        // Cleanup 을 사용하면 롬복이 자동으로 close 호출
        // try-catch 구분 없어도 됨.
        // try() 괄호 안에 들어가는 코드 앞에 @Cleanup붙이기.
        @Cleanup Connection conn = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement ps = conn.prepareStatement("select now()");
        @Cleanup ResultSet rs = ps.executeQuery();
        rs.next();
        now = rs.getString(1);

        return now;
     }

    /**
     * 하나의 TodoVO객체를 tbl_todo테이블에 insert하는 메서드
     * @param vo
     * @throws SQLException
     */
     public void insert(TodoVO vo) throws SQLException{
        // 준비할 sql 문 문자열 타입에 넣기.
        String sql = "insert into tbl_todo(title, dueDate, finished) values(?,?,?)";

        @Cleanup Connection conn = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement ps = conn.prepareStatement(sql);

        // 준비한 sql문의 ? 파라미터에 바인딩할
        ps.setString(1, vo.getTitle());
        ps.setDate(2, Date.valueOf(String.valueOf(vo.getDueDate())));
        ps.setBoolean(3,vo.isFinished());

        // 준비한 sql 문 실행. (업데이트)
        ps.executeUpdate();
     }

    public TodoVO select(Long tno)throws Exception {

        String sql = "select * from tbl_todo where tno = ?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setLong(1, tno);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        TodoVO vo = TodoVO.builder()
                .tno(resultSet.getLong("tno"))
                .title(resultSet.getString("title"))
                .dueDate(resultSet.getDate("dueDate").toLocalDate().atStartOfDay())
                .finished(resultSet.getBoolean("finished"))
                .build();

        return vo;
    }
    /**
     * tbl_todo 테이블의 모든 데이터를 조회하여 List<TodoVO>로 반환함.
     * @return
     * @throws Exception
     */
     public List<TodoVO> selectAll() throws Exception {
         String sql = "select * from tbl_todo";

         @Cleanup Connection conn = ConnectionUtil.INSTANCE.getConnection(); // 디비 연결
         @Cleanup PreparedStatement ps = conn.prepareStatement(sql); // sql 실행
         @Cleanup ResultSet rs = ps.executeQuery(); // 결과 셋 준비

         List<TodoVO> list = new ArrayList<>(); // 결과를 담을 리스트 선언

         while (rs.next()) { // 레코드 하나하나를 vo로 만들어 리스트에 추가
             TodoVO vo = TodoVO.builder() // vo 만들때 빌더 사용
                     .tno(rs.getLong("tno"))
                     .title(rs.getString("title"))
                     .dueDate(rs.getDate("dueDate").toLocalDate().atStartOfDay())
                     .finished(rs.getBoolean("finished")).build();
             list.add(vo);
         }
         ;
         return list;
     }

    /**
     * TODO 한개를 삭제하는 메서드
     * @param tno
     * @throws SQLException
     */
    public void delete(String tno) throws SQLException{
        // delete from...
        String sql = "DELETE FROM tbl_todo WHERE tno = ?";

        // 커넥션 생성
        // sql 문 준비
        // sql문 실행시켜 셋에 저장

        @Cleanup Connection conn = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement ps = conn.prepareStatement(sql);
        //@Cleanup ResultSet rs = ps.executeQuery();

        ps.setLong(1, Long.parseLong(tno)); // 문자열로 받은 tno를 Long으로 바꿔 sql 문의 첫번째 ?에 바인딩.
        ps.executeUpdate(); // 준비된 sql 문을 실행함. delete 인데 executeupdate인가?
        // 리턴값 굳이 사용하지 않음.
    }

    // tno를 받지 않고 전체 객체를 받는 이유는 뭘까? 그리고 왜 vo 인가?
    // 받은 객체 전체를 디비에 업데이트하기

    /**
     * 받은 객체의 값을 디비에 업데이트하기
     * @param vo
     * @throws SQLException
     */
    public void update(TodoVO vo) throws SQLException{
        String sql = "UPDATE tbl_todo set title ?, dueDate = ?, finished = ? where tno = ?";
        @Cleanup Connection conn = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, vo.getTitle());
        ps.setDate(2, Date.valueOf(String.valueOf(vo.getDueDate())));
        ps.setBoolean(3, vo.isFinished());
        ps.setLong(4, vo.getTno()); // 네번째 ?에 조건이 될 tno 값을 바인ㄴ딩하기. 어떤 레코드를 수정할지 지정하기.
        ps.executeUpdate();
    }

}
