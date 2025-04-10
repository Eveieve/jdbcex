package com.ssg.jdbcex2.todo.util;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
/**
 * 객체 데이터를 자동 복수해주는 매핑 도구
 * TodoVO vo = modeMapper.map(dto, TodoVO.class) 가능함.
 */
public enum MapperUtil { // 싱글톤 패턴을 활용하기 위해 이넘 타입으로 선언
    INSTANCE; // ModelUtil.INSTANCE 로 접근 가능함.

    private ModelMapper modelMapper; // 내부에서 사용할 객체 선언. 외부에서 직접 접근 불가능하도록 프라이빗 선언.

    MapperUtil() { // 이넘의 생성자
    // 자바에서 이넘은 생성자 가질 수 있음.
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration() // modelMapper의 설정을 가져와서 그 위에 매핑 설정 적용하기
                .setFieldMatchingEnabled(true) // 클래스의 필드 이름을 기준으로 매핑 허용함. getter/setter 없이도 매핑 가능함.
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE) // private 필드도 접근할 수 있도록 허용하기.
                .setMatchingStrategy(MatchingStrategies.LOOSE); // 느슨함 매칭 전략 사용하기. 필드 이름이 조금 다르더라도 유사하면 매핑해주도록 하기.
    }

    // 외부에서 이 클래스 내부의 modelMapper 객체 꺼낼 수 있도록 메서드 정의.
    public ModelMapper get() {
        // 저장된 객체 반환. MapperUtil.INSTANCE.get()으로 접근 가능함. 
        return modelMapper;
    }
}
