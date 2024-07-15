package com.react.mallapi.service;

import com.react.mallapi.domain.Todo;
import com.react.mallapi.dto.PageRequestDTO;
import com.react.mallapi.dto.PageResponseDTO;
import com.react.mallapi.dto.TodoDTO;

public interface TodoService {

    TodoDTO get(Long tno);

    Long register(TodoDTO dto);

    void modify(TodoDTO dto);

    void remove(Long tno);

    PageResponseDTO<TodoDTO> getList(PageRequestDTO requestDTO);

    default TodoDTO entityToDTO(Todo todo){
        return TodoDTO.builder()
                .tno(todo.getTno())
                .title(todo.getTitle())
                .content(todo.getContent())
                .complete(todo.isComplete())
                .dueDate(todo.getDueDate())
                .build();
    }

    default Todo dtoToEntity(TodoDTO todoDTO){
        return Todo.builder()
                .tno(todoDTO.getTno())
                .title(todoDTO.getTitle())
                .content(todoDTO.getContent())
                .dueDate(todoDTO.getDueDate())
                .build();
    }
}
