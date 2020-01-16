package kr.taeu.handa.member.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.taeu.handa.global.model.Email;
import kr.taeu.handa.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(Email email);
	
	boolean existsByEmail(Email email);
}
