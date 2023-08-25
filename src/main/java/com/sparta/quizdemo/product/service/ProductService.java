package com.sparta.quizdemo.product.service;

import com.sparta.quizdemo.backoffice.dto.KeywordRequestDto;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.product.dto.ProductRequestDto;
import com.sparta.quizdemo.product.dto.ProductResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    /**
     * 상품 생성 API
     * @param productRequestDto 생성할 상품 정보
     */
    ResponseEntity<ProductResponseDto> createProduct(ProductRequestDto productRequestDto);

    /**
     * 상품 목록 조회 API
     */
    ResponseEntity<List<ProductResponseDto>> getProducts();

    /**
     * 키워드로 상품 검색 API
     * @param keyword 검색한 키워드
     */
    ResponseEntity<List<ProductResponseDto>> findProduct(String keyword);

    /**
     * 상품 단건 조회 API
     * @param productNo 조회 대상 상품 번호
     */
    ResponseEntity<ProductResponseDto> getProduct(Long productNo);

    /**
     * 상품 정보 수정 API
     * @param productNo 수정 대상 상품 번호
     * @param productRequestDto 수정될 상품 정보
     */
    ResponseEntity<ProductResponseDto> updateProduct(Long productNo, ProductRequestDto productRequestDto);

    /**
     * 상품 삭제 API
     * @param productNo 삭제 대상 상품 번호
     */
    ResponseEntity<ApiResponseDto> deleteProduct(Long productNo);
}
