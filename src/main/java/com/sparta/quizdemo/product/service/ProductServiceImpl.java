package com.sparta.quizdemo.product.service;

import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.product.dto.ProductRequestDto;
import com.sparta.quizdemo.product.dto.ProductResponseDto;
import com.sparta.quizdemo.product.entity.Product;
import com.sparta.quizdemo.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<ProductResponseDto> createProduct(ProductRequestDto productRequestDto) {
        // 상품 이름 중복 확인
        if (productRepository.findByProductName(productRequestDto.getProductName()).isPresent()) {
            throw new IllegalArgumentException("중복된 상품이름이 존재합니다.");
        } else {
            Product product = new Product(productRequestDto);
            productRepository.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ProductResponseDto(product));
        }
    }

    @Override
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        List<Product> productList = productRepository.findAllByOrderByCreatedAtDesc();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : productList) {
            productResponseDtoList.add(new ProductResponseDto(product));
        }

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDtoList);
    }

    @Override
    public ResponseEntity<ProductResponseDto> getProduct(Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new NullPointerException("해당 번호의 상품이 존재하지 않습니다."));
        return ResponseEntity.status(HttpStatus.OK).body(new ProductResponseDto(product));
    }

    @Override
    public ResponseEntity<ProductResponseDto> updateProduct(Long productNo, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new NullPointerException("해당 번호의 상품이 존재하지 않습니다."));
        product.update(productRequestDto, product.getOrderCount());
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.OK).body(new ProductResponseDto(product));
    }

    @Override
    public ResponseEntity<ApiResponseDto> deleteProduct(Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new NullPointerException("해당 번호의 상품이 존재하지 않습니다."));
        productRepository.delete(product);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("컬럼이 삭제 되었습니다", HttpStatus.OK.value()));
    }
}
