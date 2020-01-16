package kr.taeu.handa.member.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.taeu.handa.member.dao.MemberRepository;

@ExtendWith(MockitoExtension.class)
public class TestMemberService {
	@InjectMocks
	MemberService memberService;
	
	@Mock
	MemberRepository memberRepository;
	
	
}
