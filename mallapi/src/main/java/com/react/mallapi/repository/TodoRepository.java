package com.react.mallapi.repository;

import com.react.mallapi.domain.Todo;
import com.react.mallapi.repository.search.TodoSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Long>, TodoSearch {

}
