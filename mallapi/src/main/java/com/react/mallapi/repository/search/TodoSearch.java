package com.react.mallapi.repository.search;

import com.react.mallapi.domain.Todo;
import com.react.mallapi.dto.PageRequestDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

@Transactional
public interface TodoSearch {
    Page<Todo> search1(PageRequestDTO pageRequestDTO);
}
