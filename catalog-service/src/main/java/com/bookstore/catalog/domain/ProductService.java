package com.bookstore.catalog.domain;

import com.bookstore.catalog.ApplicationProperties;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ApplicationProperties prop;

    ProductService(ProductRepository productRepository, ApplicationProperties prop) {
        this.productRepository = productRepository;
        this.prop = prop;
    }

    public PageResult<Product> getProducts(int pageNo) {
        Sort sort = Sort.by("name").ascending();
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable page = PageRequest.of(pageNo, prop.pageSize(), sort);
        Page<Product> result = productRepository.findAll(page).map(ProductMapper::toProduct);
        return new PageResult<>(
                result.getContent(),
                result.getTotalElements(),
                result.getNumber() + 1,
                result.getTotalPages(),
                result.isFirst(),
                result.isLast(),
                result.hasNext(),
                result.hasPrevious());
    }

    public Optional<Product> getProductByCode(String code) {
        return productRepository.findByCode(code).map(ProductMapper::toProduct);
    }
}
