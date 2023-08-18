package com.sparta.quizdemo.product.controller;

import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.product.dto.ProductRequestDto;
import com.sparta.quizdemo.product.dto.ProductResponseDto;
import com.sparta.quizdemo.product.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductServiceImpl productService;

    // 상품 추가
    @Secured("ROLE_ADMIN")
    @Transactional
    @PostMapping("/product")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productService.createProduct(productRequestDto);
    }

    // 전체 상품 목록 조회
    @Transactional(readOnly = true)
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        return productService.getProducts();
    }

    // 상품 단건 조회
    @Transactional(readOnly = true)
    @GetMapping("/product/{productNo}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long productNo) {
        return productService.getProduct(productNo);
    }

    // 상품 정보 업데이트
    @Secured("ROLE_ADMIN")
    @Transactional
    @PutMapping("/product/{productNo}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long productNo, @RequestBody ProductRequestDto productRequestDto) {
        return productService.updateProduct(productNo, productRequestDto);
    }

    // 상품 삭제
    @Secured("ROLE_ADMIN")
    @Transactional
    @DeleteMapping("/product/{productNo}")
    public ResponseEntity<ApiResponseDto> deleteProduct(@PathVariable Long productNo) {
        return productService.deleteProduct(productNo);
    }
}
