package com.openpf.budgetmanager.api.graphql;

import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Tag("slow")
@ExtendWith(SpringExtension.class)
@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class GraphQLIntegrationTests {

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

    @Test
    @DisplayName("Invalid currency add mutation")
    void invalidCurrencyMutation() {
        var response = testTemplate.postMultipart(
                "mutation {\n" +
                "    addCurrency(code: \"\") {\n" +
                "        id\n" +
                "        code\n" +
                "    }\n" +
                "}", "{}"
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Get all transactions")
    @Transactional
    @Sql({"/datasets/categories-01.sql", "/datasets/accounts-01.sql", "/datasets/transactions-01.sql"})
    void getAllTransactions() {
        var response = testTemplate.postMultipart(
                "query transactions {\n" +
                "    transactions {\n" +
                "        id\n" +
                "        category {\n" +
                "            title\n" +
                "        }\n" +
                "    }\n" +
                "}", "{}"
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
