package kr.taeu.handa.todoItem.repository;

import org.springframework.data.repository.CrudRepository;

import kr.taeu.handa.todoItem.domain.TodoItem;

public interface TodoItemRepository extends CrudRepository<TodoItem, Long>{

}
