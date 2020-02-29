package kr.taeu.handa.member.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TestMemberRepository {
	@Autowired
	MemberRepository memberRepository;

	@AfterEach
	public void cleanup() {
		/*
		 * 이후 테스트 코드에 영향이 없게 테스트 메서드 끝날때 마다 전체 삭제
		 */
		memberRepository.deleteAll();
	}

	@Test
	public void 회원가입() {
		// given
		Member member = Member.builder().email(Email.build("lteawoo@naver.com")).name("이태우")
				.password(Password.build("12345")).uniqueCode(UniqueCode.generateCode()).build();

		// when
		memberRepository.save(member);

		// then
		assertTrue(memberRepository.existsByEmail(member.getEmail()));
	}
}
