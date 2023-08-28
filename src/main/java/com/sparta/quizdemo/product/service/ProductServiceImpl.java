package com.sparta.quizdemo.product.service;

import com.sparta.quizdemo.backoffice.dto.KeywordRequestDto;
import com.sparta.quizdemo.cart.entity.CartItem;
import com.sparta.quizdemo.cart.repository.CartItemRepository;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.order.entity.OrderItem;
import com.sparta.quizdemo.order.repository.OrderItemRepository;
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
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;

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
        List<Product> productList = productRepository.findAllByOrderByCreatedAtAsc();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : productList) {
            productResponseDtoList.add(new ProductResponseDto(product));
        }

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDtoList);
    }

    @Override
    public ResponseEntity<List<ProductResponseDto>> findProduct(String keyword) {
        List<Product> productList = productRepository.findAll();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : productList) {
            if (product.getProductName().contains(keyword)) {
                productResponseDtoList.add(new ProductResponseDto(product));
            }
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
        List<CartItem> cartItemList = cartItemRepository.findAllByProductId(productNo);
        for (CartItem cartItem : cartItemList) {
            cartItemRepository.delete(cartItem);
        }

        List<OrderItem> orderItemList = orderItemRepository.findAllByProductId(productNo);
        for (OrderItem orderItem : orderItemList) {
            orderItemRepository.delete(orderItem);
        }

        productRepository.delete(product);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("상품이 삭제 되었습니다", HttpStatus.OK.value()));
    }
}
