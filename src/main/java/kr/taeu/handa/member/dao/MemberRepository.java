package kr.taeu.handa.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.taeu.handa.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
