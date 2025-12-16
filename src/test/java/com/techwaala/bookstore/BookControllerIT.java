package com.techwaala.bookstore;

import com.jayway.jsonpath.JsonPath;
import com.techwaala.bookstore.repositories.BookRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @SneakyThrows
    @Test
    void createBook_thenGetById_thenDeleteById() throws Exception {
        // --- 1) Create book ---
        String createRequestJson = createJsonPayload();
        MvcResult createResult = createBook(createRequestJson);

        // Extract ID from the create response
        String createResponseBody = createResult.getResponse().getContentAsString();
        Number idNumber = JsonPath.read(createResponseBody, "$.id");
        long createdId = idNumber.longValue();

        // --- 2) Fetch by id ---
        fetchByBookId(createdId);

        // --- 3) Delete book ---
        deleteByBookId(createdId);
    }

    private MvcResult createBook(String createRequestJson) throws Exception {
        return mockMvc.perform(
                        post("/api/v1/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createRequestJson)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"))
                .andExpect(jsonPath("$.price").value(10.0))
                .andExpect(jsonPath("$.isbn").value("978-0-306-40"))
                .andReturn();
    }

    private void deleteByBookId(long createdId) throws Exception {
        mockMvc.perform(delete("/api/v1/books/{id}", createdId))
                .andExpect(status().isNoContent());
    }

    private void fetchByBookId(long createdId) throws Exception {
        mockMvc.perform(get("/api/v1/books/{id}", createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value((int) createdId))
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"))
                .andExpect(jsonPath("$.price").value(10.0))
                .andExpect(jsonPath("$.isbn").value("978-0-306-40"));
    }

    private String createJsonPayload(){
        return """
            {
              "id": 1,
              "title" : "Clean Code",
              "author": "Robert C. Martin",
              "price" : 10.0,
              "isbn"  : "978-0-306-40"
            }
            """;
    }
}

