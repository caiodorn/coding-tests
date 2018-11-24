package br.com.bb.api.resource.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/listByCategory/{categoryId}")
    public HttpEntity<List<Product>> listByCategory(@PathVariable("categoryId") Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);

        return new ResponseEntity<>(products, products.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

}
