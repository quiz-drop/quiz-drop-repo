package com.sparta.quizdemo.product.controller;

import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.product.dto.OptionRequestDto;
import com.sparta.quizdemo.product.dto.OptionResponseDto;
import com.sparta.quizdemo.product.dto.ProductRequestDto;
import com.sparta.quizdemo.product.dto.ProductResponseDto;
import com.sparta.quizdemo.product.entity.Option;
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

    // 상품 생성
    @Transactional
    @Secured("ROLE_ADMIN")
    @PostMapping("/product")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productService.createProduct(productRequestDto);
    }

    // 옵션 생성
    @Transactional
    @Secured("ROLE_ADMIN")
    @PostMapping("/product/option")
    public ResponseEntity<OptionResponseDto> createOption(@RequestBody OptionRequestDto optionRequestDto) {
        return productService.createOption(optionRequestDto);
    }

    // 전체 상품 목록 조회
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        return productService.getProducts();
    }

    // 카테고리별 옵션 목록 조회
    @GetMapping("/product/options/{categoryName}")
    public ResponseEntity<List<OptionResponseDto>> getOptions(@PathVariable String categoryName) {
        return productService.getOptions(categoryName);
    }


    // 상품 단건 조회
    @GetMapping("/product/{productNo}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long productNo) {
        return productService.getProduct(productNo);
    }

    // 상품 정보 수정
    @Transactional
    @Secured("ROLE_ADMIN")
    @PutMapping("/product/{productNo}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long productNo, @RequestBody ProductRequestDto productRequestDto) {
        return productService.updateProduct(productNo, productRequestDto);
    }

    // 옵션 수정
    @Transactional
    @Secured("ROLE_ADMIN")
    @PutMapping("/product/option/{optionNo}")
    public ResponseEntity<OptionResponseDto> updateOption(@PathVariable Long optionNo, @RequestBody OptionRequestDto optionRequestDto) {
        return productService.updateOption(optionNo, optionRequestDto);
    }

    // 상품 삭제
    @Transactional
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/product/{productNo}")
    public ResponseEntity<ApiResponseDto> deleteProduct(@PathVariable Long productNo) {
        return productService.deleteProduct(productNo);
    }

    // 옵션 삭제
    @Transactional
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/product/option/{optionNo}")
    public ResponseEntity<ApiResponseDto> deleteOption(@PathVariable Long optionNo) {
        return productService.deleteOption(optionNo);
    }
}
