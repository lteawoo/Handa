package kr.taeu.handa.domain.todoItem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.taeu.handa.domain.todoItem.domain.TodoItem;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

}
