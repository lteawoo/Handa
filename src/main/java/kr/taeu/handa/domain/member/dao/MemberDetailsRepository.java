package kr.taeu.handa.domain.member.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.member.domain.model.Email;

public interface MemberDetailsRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(Email email);

	boolean existsByEmail(Email email);
}
