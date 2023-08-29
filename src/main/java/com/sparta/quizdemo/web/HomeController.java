package com.sparta.quizdemo.web;

import com.sparta.quizdemo.cart.entity.Cart;
import com.sparta.quizdemo.cart.entity.CartItem;
import com.sparta.quizdemo.cart.repository.CartItemRepository;
import com.sparta.quizdemo.cart.repository.CartRepository;
import com.sparta.quizdemo.common.security.UserDetailsImpl;
import com.sparta.quizdemo.product.dto.ProductResponseDto;
import com.sparta.quizdemo.product.entity.Product;
import com.sparta.quizdemo.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @GetMapping("/")
    public String mainPage(Model model, @RequestParam("category") String category, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Product> productList = productRepository.findAllByCategoryOrderByOrderCountDesc(category);
        model.addAttribute("productList", productList);
        if(userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }
        return "index";
    }

    @GetMapping("/cart")
    public String cartPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Cart cart = cartRepository.findByUserId(userDetails.getUser().getId()).orElseThrow(() -> new NullPointerException("해당 번호의 유저가 존재하지 않습니다."));
        List<CartItem> cartItemList = cartItemRepository.findAllByCartId(cart.getId());

        model.addAttribute("cartItemList", cartItemList);

        return "cart";
    }
}
