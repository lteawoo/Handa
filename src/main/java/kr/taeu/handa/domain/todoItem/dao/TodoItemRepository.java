package kr.taeu.handa.domain.todoItem.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.member.domain.model.Email;
import kr.taeu.handa.domain.todoItem.domain.TodoItem;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
	
	List<TodoItem> findByMemberAndLastModifiedDateGreaterThanEqual(Member member, LocalDateTime lastModifiedDate);
	List<TodoItem> findByMemberOrderByPosition(Member member);
	
	Optional<TodoItem> findByIdAndMember(Long Id, Member member);
	
	Optional<TodoItem> findByPositionAndMember(Double position, Member member);
	
	boolean existsByPositionAndMember(Double position, Member member);
	
	@Query(value = "SELECT CASE WHEN A.MAX_POS IS NULL THEN 0 ELSE A.MAX_POS END FROM DUAL LEFT OUTER JOIN (SELECT MAX(POSITION) AS MAX_POS FROM TODOITEM WHERE MEMBER_ID = :#{#MEMBER.id}) A", nativeQuery=true)
	Double findMaxPosition(@Param("MEMBER") Member member);
}
