package com.ssg.jdbcex2.todo.service;

import com.ssg.jdbcex2.todo.dao.MemberDAO;
import com.ssg.jdbcex2.todo.domain.MemberVO;
import com.ssg.jdbcex2.todo.dto.MemberDTO;
import com.ssg.jdbcex2.todo.util.MapperUtil;
import org.modelmapper.ModelMapper;

public enum MemberService {

    // 객체수를 관리하기 위해서 이넘으로 만들기
    INSTANCE;

    private ModelMapper modelMapper = new ModelMapper();
    private MemberDAO dao;

    MemberService() {
        dao = new MemberDAO();
        modelMapper = MapperUtil.INSTANCE.get(); // 싱글톤 객체를

    }
    public MemberDTO login(String mid, String mpw) throws Exception{
        MemberVO vo = dao.getWithPassword(mid, mpw); // 디비에 저장된 객체를 가져와서
        MemberDTO dto = modelMapper.map(vo, MemberDTO.class); // dto로 변환하기
        return dto;
    }

    /**
     * mid, uuid 받으면 업데이트 하는 메서드
     * @param mid
     * @param uuid
     */
    public void updateUuid(String mid, String uuid) throws Exception {
        dao.updateUuid(mid, uuid);
    }

    public MemberDTO getByUuid(String uuid) throws Exception {
        MemberVO  vo= dao.selectUUID(uuid);
        MemberDTO memberDTO = modelMapper.map(vo, MemberDTO.class);
        return memberDTO;
    }
}
