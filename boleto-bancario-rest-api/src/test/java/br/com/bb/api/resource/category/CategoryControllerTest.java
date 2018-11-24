package br.com.bb.api.resource.category;

import br.com.bb.api.resource.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CategoryRepository categoryRepositoryMock;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnHttp404_givenEmptyCollection_whenListAll() throws Exception {
        mockMvc.perform(get("/category/listAll"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnCategories_givenHasAny_whenListAll() throws Exception {
        List<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setId(1L);
        category.setName("Some Category");
        categories.add(category);
        given(categoryRepositoryMock.findAll()).willReturn(categories);

        mockMvc.perform(get("/category/listAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(category.getId()))
                .andExpect(jsonPath("$[0].name").value(category.getName()));
    }

    @Test
    public void shouldNotContainProducts_givenHasAny_whenListAll() throws Exception {
        List<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setId(1L);
        category.setName("Some Category");
        Product product = new Product();
        product.setName("Some Product");
        category.setProducts(Arrays.asList(product));
        categories.add(category);
        given(categoryRepositoryMock.findAll()).willReturn(categories);

        mockMvc.perform(get("/category/listAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].products").doesNotExist());
    }

    @Test
    public void shouldReturnHttp404_givenEmptyCollection_whenRequestParam_withNameContainingPatternMostTimes() throws Exception {
        mockMvc.perform(get("/category/listAll?withNameContainingPatternMostTimes=z"))
                .andExpect(status().isNotFound());    }

    @Test
    public void shouldReturnExpectedCategories_givenHasAnyMatching_whenRequestParam_withNameContainingPatternMostTimes() throws Exception {
        List<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setId(1L);
        category.setName("Matching Category");
        categories.add(category);
        given(categoryRepositoryMock.findWithNameContainingPatternMostTimes(anyString())).willReturn(categories);

        mockMvc.perform(get("/category").param("withNameContainingPatternMostTimes", "z"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(category.getId()))
                .andExpect(jsonPath("$[0].name").value(category.getName()));
    }

}
