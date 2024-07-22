package com.react.mallapi.repository.search;

import com.querydsl.jpa.JPQLQuery;
import com.react.mallapi.domain.Product;
import com.react.mallapi.domain.QProduct;
import com.react.mallapi.domain.QProductImage;
import com.react.mallapi.domain.Todo;
import com.react.mallapi.dto.PageRequestDTO;
import com.react.mallapi.dto.PageResponseDTO;
import com.react.mallapi.dto.ProductDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import java.util.List;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch  {

    public ProductSearchImpl() {
        super(Product.class);
    }
    @Override
    public PageResponseDTO<ProductDTO> searchList(PageRequestDTO pageRequestDTO) {
        log.info("searchList");

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage()-1,
                            pageRequestDTO.getSize(),
                            Sort.by("pno").descending());

        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;
        JPQLQuery<Product> query = from(product);

        query.leftJoin(product.imageList, productImage);
        query.where(productImage.ord.eq(0));
        getQuerydsl().applyPagination(pageable,query);
        List<Product>  productList = query.fetch();

        long count = query.fetchCount();

        log.info("=======================");
        log.info(productList);

        return null;
    }
}
