package br.com.bb.api.resource.category;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryControllerIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnAllAvailableCategories_whenListAll() throws Exception {
        mockMvc.perform(get("/category/listAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Alimentos")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Eletrodomésticos")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("Móveis")));
    }

    @Test
    public void shouldReturnExpectedCategories_givenHasAnyMatching_whenRequestParam_withNameContainingPatternMostTimes_matching() throws Exception {
        mockMvc.perform(get("/category").param("withNameContainingPatternMostTimes", "ó"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(3)))
                .andExpect(jsonPath("$[0].name", is("Móveis")));
    }

    @Test
    public void shouldReturnExpectedCategories_givenHasAnyMatching_whenRequestParam_withNameContainingPatternMostTimes_regardlessOfCase() throws Exception {
        mockMvc.perform(get("/category").param("withNameContainingPatternMostTimes", "Ó"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(3)))
                .andExpect(jsonPath("$[0].name", is("Móveis")));
    }

    @Test
    public void shouldReturnHttp404__givenNoMatch_whenRequestParam_withNameContainingPatternMostTimes() throws Exception {
        mockMvc.perform(get("/category").param("withNameContainingPatternMostTimes", "z"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}
