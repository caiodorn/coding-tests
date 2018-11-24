package br.com.bb.api.resource.category;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        categoryRepository.deleteAll();
    }

    @Test
    public void shouldReturnEmptyList_givenEmptyRepository_whenFindAll() {
        assertTrue(categoryRepository.findAll().isEmpty());
    }

    @Test
    public void shouldReturnCategoryList_givenNonEmptyRepository_whenFindAll() {
        categoryRepository.save(new Category());

        assertTrue(categoryRepository.findAll().size() == 1);
    }

    @Test
    public void shouldReturnCategoryList_givenRepositoryHasMatchingCategory_whenFindWithNameContainingPatternMostTimes() {
        Category category = new Category();
        category.setName("Cosméticos");
        categoryRepository.save(category);

        List<Category> categories = categoryRepository.findWithNameContainingPatternMostTimes("é");

        assertTrue(categories.size() == 1);
        assertEquals(category.getName(), categories.get(0).getName());
    }

}
