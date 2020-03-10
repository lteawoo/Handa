package kr.taeu.handa.domain.todoItem.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.todoItem.domain.TodoItem;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
	List<TodoItem> findAllByMember(Member member);
	
	Optional<TodoItem> findByIdAndMember(Long Id, Member member);
}
