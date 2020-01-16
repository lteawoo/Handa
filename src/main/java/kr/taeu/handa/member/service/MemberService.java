package kr.taeu.handa.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.taeu.handa.global.model.Email;
import kr.taeu.handa.member.dao.MemberRepository;
import kr.taeu.handa.member.domain.Member;
import kr.taeu.handa.member.domain.UniqueCode;
import kr.taeu.handa.member.dto.SignUpRequest;
import kr.taeu.handa.member.exception.EmailDuplicateException;
import kr.taeu.handa.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	
	@Transactional(readOnly = true)
	public Member findByEmail(Email email) {
		final Optional<Member> member = memberRepository.findByEmail(email);
		member.orElseThrow(() -> new MemberNotFoundException(email));
		return member.get();
	}
	
	public Member signUp(SignUpRequest dto) {
		if(memberRepository.existsByEmail(dto.getEmail())) {
			throw new EmailDuplicateException(dto.getEmail());
		}
		
		return memberRepository.save(dto.toEntity(UniqueCode.generateCode()));
	}
}
