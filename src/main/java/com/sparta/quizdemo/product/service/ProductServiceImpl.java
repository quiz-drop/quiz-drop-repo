package com.sparta.quizdemo.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.quizdemo.backoffice.dto.KeywordRequestDto;
import com.sparta.quizdemo.cart.entity.CartItem;
import com.sparta.quizdemo.cart.repository.CartItemRepository;
import com.sparta.quizdemo.common.aws.AwsS3Service;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.order.entity.OrderItem;
import com.sparta.quizdemo.order.repository.OrderItemRepository;
import com.sparta.quizdemo.product.dto.ProductRequestDto;
import com.sparta.quizdemo.product.dto.ProductResponseDto;
import com.sparta.quizdemo.product.entity.Product;
import com.sparta.quizdemo.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final AwsS3Service awsS3Service;

    @Override
    @CacheEvict(value = "Products", allEntries = true, cacheManager = "productCacheManager")
    public ProductResponseDto createProduct(MultipartFile multipartFile,String productRequestDto_temp) throws JsonProcessingException {

        ProductRequestDto productRequestDto = conversionDto(productRequestDto_temp);

        // 상품 이름 중복 확인
        if (productRepository.findByProductName(productRequestDto.getProductName()).isPresent()) {
            throw new IllegalArgumentException("중복된 상품이름이 존재합니다.");
        } else {
            String fileName = awsS3Service.uploadImage(multipartFile);
            productRequestDto.setFileName(fileName);

            Product product = new Product(productRequestDto);
            productRepository.save(product);
            return new ProductResponseDto(product);
        }
    }

    @Override
    @Transactional
    @Cacheable(value = "Products", cacheManager = "productCacheManager")
    public List<ProductResponseDto> getProducts() {
        List<Product> productList = productRepository.findAllByOrderByCreatedAtAsc();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : productList) {
            productResponseDtoList.add(new ProductResponseDto(product));
        }

        return productResponseDtoList;
    }

    @Override
    @Transactional
    @Cacheable(value = "Products", key = "#category", cacheManager = "productCacheManager")
    public List<ProductResponseDto> getProductsByCategory(String category) {
        List<Product> productList = productRepository.findAllByCategory(category);
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : productList) {
            productResponseDtoList.add(new ProductResponseDto(product));
        }

        return productResponseDtoList;
    }

    @Override
    public ResponseEntity<List<ProductResponseDto>> findProduct(String keyword) {
        List<Product> productList = productRepository.findAll();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        if (keyword.isBlank()) {
            throw new IllegalArgumentException("검색어를 입력해주세요.");
        } else {
            for (Product product : productList) {
                if (product.getProductName().contains(keyword)) {
                    productResponseDtoList.add(new ProductResponseDto(product));
                }
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDtoList);
    }

    @Override
    @Transactional
    @Cacheable(value = "Products", key = "#productNo", cacheManager = "productCacheManager")
    public ProductResponseDto getProduct(Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new NullPointerException("해당 번호의 상품이 존재하지 않습니다."));
        return new ProductResponseDto(product);
    }

    @Override
    @CacheEvict(value = "Products", allEntries = true, cacheManager = "productCacheManager")
    public ProductResponseDto updateProduct(Long productNo, MultipartFile multipartFile,String productRequestDto_temp) throws JsonProcessingException {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new NullPointerException("해당 번호의 상품이 존재하지 않습니다."));
        ProductRequestDto productRequestDto = conversionDto(productRequestDto_temp);

        String fileName = awsS3Service.uploadImage(multipartFile);
        productRequestDto.setFileName(fileName);

        product.update(productRequestDto, product.getOrderCount(), product.getProductScore());
        productRepository.save(product);
        return new ProductResponseDto(product);
    }

    @Override
    @CacheEvict(value = "Products", allEntries = true, cacheManager = "productCacheManager")
    public ApiResponseDto deleteProduct(Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new NullPointerException("해당 번호의 상품이 존재하지 않습니다."));
        List<CartItem> cartItemList = cartItemRepository.findAllByProductId(productNo);
        cartItemRepository.deleteAll(cartItemList);

        List<OrderItem> orderItemList = orderItemRepository.findAllByProductId(productNo);
        orderItemRepository.deleteAll(orderItemList);

        if (product.getProductImage() != null) {
            awsS3Service.deleteImage(product.getProductImage());
        }
        productRepository.delete(product);
        return new ApiResponseDto("상품이 삭제 되었습니다", HttpStatus.OK.value());
    }

    //json타입으로 변환
    public ProductRequestDto conversionDto(String productRequestDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(productRequestDto, ProductRequestDto.class);
    }
}
