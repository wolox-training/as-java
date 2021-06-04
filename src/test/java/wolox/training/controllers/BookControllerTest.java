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
import static wolox.training.builder.UserBuilder.listUsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @MockBean
    private BookRepository repository;

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @WithMockUser
    @Test
    void testFindAllWhenReturnSuccessResponse() throws Exception {
        Page<Book> listBooks = new PageImpl(listBooks());
        when(repository.findAll(any(Example.class),any(Pageable.class))).thenReturn(listBooks);

        mvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].genre", is("Action")));;
    }

    @WithMockUser
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

    @WithMockUser
    @Test
    void testDeleteWhenReturnSuccessDeleteResponse() throws Exception {
        when(repository.findById(2L)).thenReturn(Optional.of(book()));

        mvc.perform(delete("/api/books/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @WithMockUser
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
