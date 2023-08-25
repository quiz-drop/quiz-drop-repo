package com.sparta.quizdemo.product.controller;

import com.sparta.quizdemo.backoffice.dto.KeywordRequestDto;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.product.dto.ProductRequestDto;
import com.sparta.quizdemo.product.dto.ProductResponseDto;
import com.sparta.quizdemo.product.service.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "상품 생성", description = "관리자 제한")
    @Transactional
    @Secured("ROLE_ADMIN")
    @PostMapping("/product")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productService.createProduct(productRequestDto);
    }

    @Operation(summary = "전체 상품 목록 조회", description = "로그인 없이도 이용할 수 있습니다.")
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        return productService.getProducts();
    }

    @Operation(summary = "키워드로 상품 검색", description = "로그인 없이도 이용할 수 있습니다.")
    @PutMapping("/product")
    public ResponseEntity<List<ProductResponseDto>> findProduct(@RequestBody KeywordRequestDto keywordRequestDto) {
        return productService.findProduct(keywordRequestDto.getKeyword());
    }

    @Operation(summary = "상품 단건 조회")
    @GetMapping("/product/{productNo}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long productNo) {
        return productService.getProduct(productNo);
    }

    @Operation(summary = "상품 정보 수정", description = "관리자 제한")
    @Transactional
    @Secured("ROLE_ADMIN")
    @PutMapping("/product/{productNo}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long productNo, @RequestBody ProductRequestDto productRequestDto) {
        return productService.updateProduct(productNo, productRequestDto);
    }

    @Operation(summary = "상품 삭제", description = "관리자 제한")
    @Transactional
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/product/{productNo}")
    public ResponseEntity<ApiResponseDto> deleteProduct(@PathVariable Long productNo) {
        return productService.deleteProduct(productNo);
    }
}
