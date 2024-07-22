package com.react.mallapi.service;

import com.react.mallapi.dto.PageRequestDTO;
import com.react.mallapi.dto.PageResponseDTO;
import com.react.mallapi.dto.ProductDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductService  {
    PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO);

    Long  register(ProductDTO productDTO);

    ProductDTO get(Long pno);

    void modify(ProductDTO productDTO);

    void remove(Long pno);
}
