package kr.taeu.handa.domain.member.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.taeu.handa.domain.member.dao.MemberDetailsRepository;
import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.member.domain.model.Email;
import kr.taeu.handa.domain.member.domain.model.Password;
import kr.taeu.handa.domain.member.dto.SignUpRequest;
import kr.taeu.handa.domain.member.exception.EmailAlreadyExistsException;
import kr.taeu.handa.domain.member.exception.EmailNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
	private final MemberDetailsRepository memberDetailsRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Member createMember(SignUpRequest signUpRequest) {
		boolean isExists = this.memberDetailsRepository.existsByEmail(signUpRequest.getEmail());

		if (isExists) {
			// 이메일 중복 처리
			throw new EmailAlreadyExistsException(signUpRequest.getEmail());
		}

		// 비밀번호 암호화
		SignUpRequest cryptedReq = SignUpRequest.builder()
				.email(signUpRequest.getEmail())
				.name(signUpRequest.getName())
				.password(new Password(passwordEncoder.encode(signUpRequest.getPassword().getValue())))
				.build();

		return this.memberDetailsRepository.save(cryptedReq.toEntity());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> optMember = this.memberDetailsRepository.findByEmail(new Email(username));
		Member member = optMember.orElseThrow(() -> new EmailNotFoundException(new Email(username)));

		/*
		 * 권한 설정 > 예제이므로 멤버당 한개씩이라고 가정함
		 */
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(member.getRole().getValue()));

		return new User(member.getEmail().getValue(), member.getPassword().getValue(), grantedAuthorities);
	}
}
