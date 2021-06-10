package wolox.training.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wolox.training.builder.UserBuilder.listUsers;
import static wolox.training.builder.UserBuilder.user;
import static wolox.training.builder.BookBuilder.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @WithMockUser
    @Test
    void testFindAllWhenReturnSuccessResponse() throws Exception {
        when(userRepository.findAll()).thenReturn(listUsers());

        mvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("andrey")));
    }

    @WithMockUser
    @Test
    void testFindOneWhenReturnSuccessResponse() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user()));

        mvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("andrey")));
    }

    @Test
    void testCreateWhenReturnSuccessCreateResponse() throws Exception {
        when(userRepository.save(any())).thenReturn(user());

        mvc.perform(post("/api/users")
                .content(objectMapper.writeValueAsString(user()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("andrey")));
    }

    @WithMockUser
    @Test
    void testDeleteWhenReturnSuccessDeleteResponse() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user()));

        mvc.perform(delete("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @WithMockUser
    @Test
    void testUpdateWhenReturnSuccessUpdateResponse() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user()));
        when(userRepository.save(any())).thenReturn(user());

        mvc.perform(put("/api/users/1")
                .content(objectMapper.writeValueAsString(user()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("andrey")));
    }

    @WithMockUser
    @Test
    void testAddBookWhenReturnSuccessAddBookResponse() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user()));
        when(userRepository.save(any())).thenReturn(user());
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book()));

        mvc.perform(put("/api/users/1/books/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    void testAddBookWhenThrowExceptionByNotFoundBook() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user()));
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        mvc.perform(put("/api/users/1/books/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void testRemoveBookWhenThrowExceptionByNotFoundBook() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user()));
        when(userRepository.save(any())).thenReturn(user());
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book()));

        mvc.perform(delete("/api/users/1/books/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
