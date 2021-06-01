package wolox.training.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wolox.training.builder.BookBuilder.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.repositories.BookRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockBean
    private BookRepository repository;

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testFindAllWhenReturnSuccessResponse() throws Exception {
        when(repository.findAll()).thenReturn(listBooks());

        mvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].genre", is("Action")));
    }


    @Test
    void testFindOneWhenReturnSuccessResponse() throws Exception {
        when(repository.findById(2L)).thenReturn(Optional.of(book()));

        mvc.perform(get("/api/books/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genre", is("Action")));
    }

    @Test
    void testCreateWhenReturnSuccessCreateResponse() throws Exception {
        when(repository.save(any())).thenReturn(book());

        mvc.perform(post("/api/books")
                .content(objectMapper.writeValueAsString(book()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.genre", is("Action")));
    }

    @Test
    void testDeleteWhenReturnSuccessDeleteResponse() throws Exception {
        when(repository.findById(2L)).thenReturn(Optional.of(book()));

        mvc.perform(delete("/api/books/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateWhenReturnSuccessUpdateResponse() throws Exception {
        when(repository.findById(2L)).thenReturn(Optional.of(book()));
        when(repository.save(any())).thenReturn(book());

        mvc.perform(put("/api/books/2")
                .content(objectMapper.writeValueAsString(book()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genre", is("Action")));
    }

}
