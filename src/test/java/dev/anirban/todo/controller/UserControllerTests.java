package dev.anirban.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.anirban.todo.constants.UrlConstants;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.exception.UserNotFound;
import dev.anirban.todo.service.UserService;
import jakarta.servlet.ServletException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user1, user2;

    @BeforeEach
    public void setupUser() {
        user1 = User
                .builder()
                .uid("Test ID 01")
                .name("Test User 01")
                .username("Test Username 01")
                .email("testemail@gmail.com")
                .password("test password 01")
                .avatar("Test Avatar 01")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .categoriesCreated(new HashSet<>())
                .todosCreated(new HashSet<>())
                .checkpointCreated(new HashSet<>())
                .build();

        user2 = User
                .builder()
                .uid("Test ID 02")
                .name("Test User 02")
                .username("Test Username 02")
                .email("testemail02@gmail.com")
                .password("test password 02")
                .avatar("Test Avatar 02")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .categoriesCreated(new HashSet<>())
                .todosCreated(new HashSet<>())
                .checkpointCreated(new HashSet<>())
                .build();
    }


    @Test
    @DisplayName("create() -> returns User (positive outcome)")
    public void create_returnsUser() throws Exception {
        given(userService.create(Mockito.any())).willReturn(user1);

        mockMvc.perform(post(UrlConstants.CREATE_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.uid").exists())
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, Mockito.times(1)).create(Mockito.any());
    }


    @Test
    @DisplayName("create() -> Duplicate username -> throws an Exception (negative outcome)")
    public void create_duplicateUsername_throwsException() {
        given(userService.create(Mockito.any())).willThrow(DataIntegrityViolationException.class);

        Assertions.assertThatThrownBy(() ->
                mockMvc.perform(post(UrlConstants.CREATE_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1))
                        .accept(MediaType.APPLICATION_JSON)
                )
        ).isInstanceOf(ServletException.class);

        verify(userService, times(1)).create(Mockito.any());
    }


    @Test
    @DisplayName("create() -> Duplicate email -> throws an Exception (negative outcome)")
    public void create_duplicateEmail_throwsException() {
        given(userService.create(Mockito.any())).willThrow(DataIntegrityViolationException.class);

        Assertions.assertThatThrownBy(() ->
                mockMvc.perform(post(UrlConstants.CREATE_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1))
                        .accept(MediaType.APPLICATION_JSON)
                )
        ).isInstanceOf(ServletException.class);

        verify(userService, times(1)).create(Mockito.any());
    }


    @Test
    @DisplayName("findAll() -> returns List of User (positive outcome)")
    public void findAll_returnsUsers() throws Exception {
        given(userService.findAll()).willReturn(List.of(user1, user2));

        mockMvc.perform(get(UrlConstants.FIND_ALL_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].uid").value(user1.getUid()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].uid").value(user2.getUid()));

        verify(userService, times(1)).findAll();
    }


    @Test
    @DisplayName("findAll() -> returns empty List of User (negative outcome)")
    public void findAll_returnsEmpty() throws Exception {
        given(userService.findAll()).willReturn(List.of());

        mockMvc.perform(get(UrlConstants.FIND_ALL_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));

        verify(userService, times(1)).findAll();
    }


    @Test
    @DisplayName("findById() -> returns User object (positive outcome)")
    public void findById_returnsUser() throws Exception {
        given(userService.findById(user1.getUid())).willReturn(user1);

        mockMvc.perform(get(UrlConstants.FIND_USER_BY_ID, user1.getUid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uid").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uid").value(user1.getUid()));

        verify(userService, times(1)).findById(user1.getUid());
    }


    @Test
    @DisplayName("findById() -> returns Empty Optional (negative Outcome)")
    public void findById_throwsException() {

        String invalidId = "Invalid Id";

        given(userService.findById(invalidId)).willThrow(UserNotFound.class);

        Assertions.assertThatThrownBy(() ->
                mockMvc.perform(get(UrlConstants.FIND_USER_BY_ID, invalidId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
        ).isInstanceOf(ServletException.class);

        verify(userService, times(1)).findById(invalidId);
    }


    @Test
    @DisplayName("deleteById() -> deletes user (positive outcome)")
    public void deleteById_deletesUser() throws Exception {
        willDoNothing().given(userService).deleteById(user1.getUid());

        mockMvc.perform(
                delete(UrlConstants.DELETE_USER_BY_ID, user1.getUid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).deleteById(user1.getUid());
    }


    @Test
    @DisplayName("deleteById() -> throws Exception (negative outcome)")
    public void deleteById_withInvalidId_throwsException() {


        String invalidId = "Invalid Id";
        willThrow(UserNotFound.class).given(userService).deleteById(invalidId);

        Assertions.assertThatThrownBy(() ->
                mockMvc.perform(
                        delete(UrlConstants.DELETE_USER_BY_ID, invalidId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
        ).isInstanceOf(ServletException.class);

        verify(userService, times(1)).deleteById(invalidId);
    }
}