package com.caiodorn.codingtests.gamesys.user.rest;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.snippet.Attributes.key;

/**
 * No tests here - Contains all boilerplate code needed for REST controller tests and Spring REST Docs so that
 * controller test classes get less cluttered and more focused.
 */
public class DocumentedTest {

    @Autowired
    private WebApplicationContext context;

    protected MockMvc mockMvc;

    protected RestDocumentationResultHandler documentationHandler;

    protected RequestFieldsSnippet requestFieldsSnippet;

    protected ResponseFieldsSnippet customErrorSnippet;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        documentationHandler = document("{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(documentationHandler)
                .build();

        ConstrainedFields constrainedFields = new ConstrainedFields(User.class);
        requestFieldsSnippet = requestFields(
                constrainedFields.withPath("userName").description("The username"),
                constrainedFields.withPath("password").description("User's password"),
                constrainedFields.withPath("dob").description("User's date of birth"),
                constrainedFields.withPath("ssn").description("User's Social Security Number")
        );

        customErrorSnippet = responseFields(
                fieldWithPath("title").type("String").description("Status code description"),
                fieldWithPath("status").type("Integer").description("The status code"),
                fieldWithPath("detail").type("String").description("An error message describing what happened")
        );
    }

    protected static class ConstrainedFields {

        private static final String DELIMITER = ". ";
        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        protected FieldDescriptor withPath(String path) {
            return fieldWithPath(path)
                    .attributes(key("constraints")
                            .value(StringUtils.collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path), DELIMITER) + DELIMITER));
        }
    }

}
