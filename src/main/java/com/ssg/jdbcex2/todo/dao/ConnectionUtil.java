package com.ssg.jdbcex2.todo.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Mysql 데이터베이스에 연결하는 유틸리티 클래스
 */
public enum ConnectionUtil {
    // enum 으로 선언 - INSTANCE는 이 클래스의 유일한 인스턴스 (싱글톤)
    // 스레드 안전, 안전한 싱글톤 구현 방법 중 하나
    INSTANCE; // ?

    // 실제 db 연결 관리할 HikariCP의 커넥션 풀 객체
    // 커넥션 풀 객체를 통해 커넥션을 빌려오거나 반납 가능
    private HikariDataSource ds;

    // enum의 Constructor, 이넘은 자동으로 Private 이기 때문에 외부에서 생성 불가능.
    ConnectionUtil() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/ssgdb?serverTimezone=Asia/Seoul");
        config.setUsername("root");
        config.setPassword("root");

        // PreparedStatement 캐싱 설정
        config.addDataSourceProperty("cachePrepStmts", "true"); // 캐싱 켜기
        config.addDataSourceProperty("prepStmtCacheSize", "250"); // 최대 250개 캐시
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048"); // 쿼리 길이 제한

        ds = new HikariDataSource(config);
    }

    // 외부에서 호출할때 커넥션 풀에서 db 연결 하나를 가져다 줌
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}

/**
 * 1. 왜 ConnectionUtil을 싱글톤으로 만들면 좋은가?
 * 디비 연결 설정은 한번만 초기화하고, 애플리케이션 전체에서 공유하여 재사용하는게 효율적임.
 *
 * 2. 왜 커넥션유틸은 이넘으로 선언했는가?
 * 이넘은 자바가 한번만 인스턴스를 생성하도록 보장함.
 * 직렬화, 리플렉션 문제를 자동으로 방지함.
 *
 * 3. 스레드 안전하다는건 무슨뜻?
 * - 여러 사용자가 동시에 객체를 사용하더라도 문제가 생기지 않음.
 * - ConnectionUtil.INSTANCE.getConnection() 을 여러 스레드에서 동시 호출해도 하나의 인스턴스 공유하여 충돌 없음.
 *
 * 4. PreparedStatement 캐싱 설정:
 * PreparedStatement는 sql 문장을 미리 컴파일해두고 재사용할 수 있는 객체임.
 *
 * 캐싱이란?
 * 동일한 sql 구문을 반복적으로 실행할때, 매번 새롭게 파싱, 컴파일하지 않고 준비해둔 PreparedStatement를 사용하는 것.
 *
 * 5. 커넥션 풀이란?
 * 자바에서 MYSQL 과 같은 디비에 연결하려면 CONNECTION 객체가 필요함.
 * 이 연결은 네트워크 통신이기 때문에 생성/해제 비용이 큼.
 *
 * 커넥션풀 = 커넥션을 미리 여러개 만들어서 풀(저장소)에 보관해둠.
 * 필요할때 꺼내 쓰고, 다쓰면 반납함. 재사용 구조이ㅣㅁ.
 *
 * 6. 미리 커넥션 객체 10개를 만들어 놓아도 다쓰지 않으면 낭비 아님?
 *
 * 7. 기본적으로 몇개 생성하는데?
 */
