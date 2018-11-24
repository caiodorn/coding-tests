package br.com.bb.api.resource.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/listAll")
    public HttpEntity<List<Category>> listAll() {
        List<Category> categories = categoryRepository.findAll();

        return new ResponseEntity<>(categories, categories.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping
    public HttpEntity<List<Category>> listAllWithNameContainingPatternMostTimes(@RequestParam("withNameContainingPatternMostTimes") String pattern) {
        List<Category> categories = categoryRepository.findWithNameContainingPatternMostTimes(pattern.toLowerCase());

        return new ResponseEntity<>(categories, categories.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

}
