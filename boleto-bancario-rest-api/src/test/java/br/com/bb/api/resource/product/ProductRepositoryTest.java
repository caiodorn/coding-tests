package br.com.bb.api.resource.product;

import br.com.bb.api.resource.category.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        productRepository.deleteAll();
    }

    @Test
    public void shouldReturnEmptyList_givenEmptyRepository_whenFindByCategory() {
        assertTrue(productRepository.findByCategoryId(1L).isEmpty());
    }

    @Test
    public void shouldReturnEmptyList_givenNonEmptyRepository_whenFindByCategory_nonMatchingCategory() {
        Category category = new Category();
        category.setId(1L);
        Product product = new Product();
        product.setName("Some Product");
        product.setCategory(category);
        productRepository.save(product);

        assertTrue(productRepository.findByCategoryId(2L).isEmpty());
    }

    @Test
    public void shouldReturnCategoryList_givenNonEmptyRepository_whenFindByCategory_matchingCategory() {
        Category category = new Category();
        category.setId(1L);
        Product product = new Product();
        product.setName("Some Product");
        product.setCategory(category);
        productRepository.save(product);

        List<Product> products = productRepository.findByCategoryId(1L);

        assertTrue(products.size() == 1);
        assertEquals(product.getName(), products.get(0).getName());
    }


}
