package kr.taeu.handa.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.taeu.handa.todoItem.domain.TodoItem;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long>{

}
