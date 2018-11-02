package com.openpf.budgetmanager;

import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Tag("slow")
@ExtendWith(SpringExtension.class)
@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class GraphQLTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private GraphQLTestTemplate testTemplate;

    @Test
    void test() {
        var response = testTemplate.postMultipart("{\n" +
                "  categories {\n" +
                "    id\n" +
                "    title\n" +
                "  }}", "{}");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
