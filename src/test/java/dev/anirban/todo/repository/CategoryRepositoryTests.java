package dev.anirban.todo.repository;

import dev.anirban.todo.entity.Category;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.repo.CategoryRepository;
import dev.anirban.todo.repo.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CategoryRepositoryTests {

    @Autowired
    private UserRepository userRepo;
    private User user1, user2;

    @Autowired
    private CategoryRepository catRepo;
    private Category category1, category2;

    @BeforeEach
    public void setupUser() {
        user1 = User
                .builder()
                .name("Test User 01")
                .username("Test Username 01")
                .email("testemai1l@gmail.com")
                .password("test password 01")
                .roles(User.UserRole.USER)
                .avatar("Test Avatar 01")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .categoriesCreated(new HashSet<>())
                .todosCreated(new HashSet<>())
                .checkpointCreated(new HashSet<>())
                .build();

        userRepo.save(user1);

        user2 = User
                .builder()
                .name("Test User 02")
                .username("Test Username 02")
                .email("testemail2@gmail.com")
                .password("test password 02")
                .roles(User.UserRole.USER)
                .avatar("Test Avatar 02")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .categoriesCreated(new HashSet<>())
                .todosCreated(new HashSet<>())
                .checkpointCreated(new HashSet<>())
                .build();

        userRepo.save(user2);
    }


    @BeforeEach
    public void setupCategory() {
        category1 = Category
                .builder()
                .name("Category 01")
                .description("Description 01")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .todoList(new HashSet<>())
                .build();

        category2 = Category
                .builder()
                .name("Category 02")
                .description("Description 02")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .todoList(new HashSet<>())
                .build();
    }


    @Test
    @DisplayName("findByCreatedBy_Uid() -> returns List of Category (positive outcome)")
    public void findByCreatedBy_Uid_returnsCategories() {
        user1.addCategory(category1);
        catRepo.save(category1);

        user2.addCategory(category2);
        catRepo.save(category2);

        List<Category> foundCategories = catRepo.findByCreatedBy_Uid(user1.getUid());

        Assertions.assertThat(foundCategories).isNotNull();
        Assertions.assertThat(foundCategories.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("findByCreatedBy_Uid() -> returns Empty (negative outcome)")
    public void findByCreatedBy_Uid_returnsEmpty() {
        List<Category> foundCategories = catRepo.findByCreatedBy_Uid(user1.getUid());
        Assertions.assertThat(foundCategories).isEmpty();
    }
}