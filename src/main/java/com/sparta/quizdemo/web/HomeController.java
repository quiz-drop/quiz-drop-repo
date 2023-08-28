package com.sparta.quizdemo.web;

import com.sparta.quizdemo.common.security.UserDetailsImpl;
import com.sparta.quizdemo.product.entity.Product;
import com.sparta.quizdemo.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductRepository productRepository;

    @GetMapping("/")
    public String mainPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("userRole", userDetails.getUser().getRole());
        }
        return "index";
    }

    @GetMapping("/product/{productId}")
    public String selectProduct(@PathVariable Long productId, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NullPointerException("해당 번호의 상품이 존재하지 않습니다."));

        model.addAttribute("category", product.getCategory());
        model.addAttribute("productId", productId);
        if(userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("userRole", userDetails.getUser().getRole());
        }
        return "productDetail";
    }

    @GetMapping("/cart")
    public String cartPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("username", userDetails.getUsername());

        return "cart";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/new-product")
    public String newProduct() {
        return "newProduct";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/edit-product/{productId}")
    public String updateProduct(@PathVariable Long productId, Model model) {
        model.addAttribute("productId", productId);
        return "editProduct";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public String backOffice() {
        return "backOffice";
    }
}
