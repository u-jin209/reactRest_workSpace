package com.react.mallapi.repository.search;

import com.react.mallapi.dto.PageRequestDTO;
import com.react.mallapi.dto.PageResponseDTO;
import com.react.mallapi.dto.ProductDTO;

public interface ProductSearch {
    PageResponseDTO<ProductDTO> searchList (PageRequestDTO pageRequestDTO);
}
