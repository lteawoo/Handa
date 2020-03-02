package kr.taeu.handa.member.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kr.taeu.handa.domain.member.dao.MemberDetailsRepository;
import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.member.domain.model.Email;
import kr.taeu.handa.domain.member.domain.model.Name;
import kr.taeu.handa.domain.member.domain.model.Password;
import kr.taeu.handa.domain.member.domain.model.Role;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TestMemberRepository {
	@Autowired
	MemberDetailsRepository memberDetailsRepository;

	@AfterEach
	public void cleanup() {
		/*
		 * 이후 테스트 코드에 영향이 없게 테스트 메서드 끝날때 마다 전체 삭제
		 */
		memberDetailsRepository.deleteAll();
	}

	@Test
	public void 회원가입() {
		// given
		Member member = Member.builder()
				.email(new Email("test@taeu.kr"))
				.name(new Name("테스트계정"))
				.password(new Password("12345"))
				.role(Role.MEMBER)
				.build();

		// when
		memberDetailsRepository.save(member);

		// then
		assertTrue(memberDetailsRepository.existsByEmail(member.getEmail()));
	}
}
