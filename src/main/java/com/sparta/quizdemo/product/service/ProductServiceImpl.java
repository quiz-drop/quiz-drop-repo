package com.sparta.quizdemo.product.service;

import com.sparta.quizdemo.cart.entity.CartItem;
import com.sparta.quizdemo.cart.repository.CartItemRepository;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.product.dto.OptionRequestDto;
import com.sparta.quizdemo.product.dto.OptionResponseDto;
import com.sparta.quizdemo.product.dto.ProductRequestDto;
import com.sparta.quizdemo.product.dto.ProductResponseDto;
import com.sparta.quizdemo.product.entity.Option;
import com.sparta.quizdemo.product.entity.Product;
import com.sparta.quizdemo.product.repository.OptionRepository;
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
    private final OptionRepository optionRepository;

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
    public ResponseEntity<OptionResponseDto> createOption(OptionRequestDto optionRequestDto) {
        if (optionRepository.findByOptionName(optionRequestDto.getOptionName()).isPresent()) {
            throw new IllegalArgumentException("중복된 옵션이름이 존재합니다.");
        } else {
            Option option = new Option(optionRequestDto);
            optionRepository.save(option);
            return ResponseEntity.status(HttpStatus.CREATED).body(new OptionResponseDto(option));
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
    public ResponseEntity<List<OptionResponseDto>> getOptions(String categoryName) {
        List<Option> optionList = optionRepository.findAllByCategory(categoryName);
        List<OptionResponseDto> optionResponseDtoList = new ArrayList<>();

        for (Option option : optionList) {
            optionResponseDtoList.add(new OptionResponseDto(option));
        }

        return ResponseEntity.status(HttpStatus.OK).body(optionResponseDtoList);
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
    public ResponseEntity<OptionResponseDto> updateOption(Long optionNo, OptionRequestDto optionRequestDto) {
        Option option = optionRepository.findById(optionNo).orElseThrow(() -> new NullPointerException("해당 번호의 옵션이 존재하지 않습니다."));
        option.update(optionRequestDto);
        optionRepository.save(option);
        return ResponseEntity.status(HttpStatus.OK).body(new OptionResponseDto(option));
    }

    @Override
    public ResponseEntity<ApiResponseDto> deleteProduct(Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new NullPointerException("해당 번호의 상품이 존재하지 않습니다."));
        List<CartItem> cartItemList = cartItemRepository.findAllByProductId(productNo);

        for (CartItem cartItem : cartItemList) {
            cartItemRepository.delete(cartItem);
        }
        productRepository.delete(product);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("상품이 삭제 되었습니다", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ApiResponseDto> deleteOption(Long optionNo) {
        Option option = optionRepository.findById(optionNo).orElseThrow(() -> new NullPointerException("해당 번호의 옵션이 존재하지 않습니다."));
        optionRepository.delete(option);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("옵션이 삭제 되었습니다", HttpStatus.OK.value()));
    }
}
