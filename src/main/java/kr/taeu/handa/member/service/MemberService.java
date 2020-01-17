package kr.taeu.handa.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.taeu.handa.global.model.Email;
import kr.taeu.handa.global.model.Password;
import kr.taeu.handa.member.dao.MemberRepository;
import kr.taeu.handa.member.domain.Member;
import kr.taeu.handa.member.domain.Role;
import kr.taeu.handa.member.domain.UniqueCode;
import kr.taeu.handa.member.dto.SignUpRequest;
import kr.taeu.handa.member.exception.EmailDuplicateException;
import kr.taeu.handa.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{
	private final MemberRepository memberRepository;
	
	@Transactional
	public Member signUp(SignUpRequest dto) {
		if(memberRepository.existsByEmail(dto.getEmail())) {
			throw new EmailDuplicateException(dto.getEmail());
		}
		
//		SignUpRequest sigendReq = new SignUpRequest(dto.getEmail(),
//				dto.getName(),
//				Password.build(passwordEncoder.encode(dto.getPassword().getValue())));
		
		return memberRepository.save(dto.toEntity(UniqueCode.generateCode()));
	}

	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		final Member member = this.findByEmail(Email.build(email));
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
		
		return new User(member.getEmail().getValue(), member.getPassword().getValue(), authorities);
	}
	
	private Member findByEmail(Email email) {
		final Optional<Member> member = memberRepository.findByEmail(email);
		member.orElseThrow(() -> new MemberNotFoundException(email));
		return member.get();
	}
}
