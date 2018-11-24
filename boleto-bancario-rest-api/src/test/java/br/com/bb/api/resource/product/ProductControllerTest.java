package br.com.bb.api.resource.product;

import br.com.bb.api.resource.category.Category;
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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ProductRepository productRepositoryMock;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnHttp404_givenEmptyCollection_whenListByCategory() throws Exception {
        mockMvc.perform(get("/product/listByCategory/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnProducts_givenCategoryHasProducts_whenListByCategory() throws Exception {
        List<Product> products = new ArrayList<>();
        Category category = new Category();
        category.setId(1L);
        category.setName("Alimentos");
        Product product = new Product();
        product.setCategory(category);
        product.setName("Arroz");
        products.add(product);

        given(productRepositoryMock.findByCategoryId(category.getId())).willReturn(products);

        mockMvc.perform(get("/product/listByCategory/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(product.getId()))
                .andExpect(jsonPath("$[0].name").value(product.getName()));
    }

}
