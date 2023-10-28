package learning.spring.binarytea.repository;

import learning.spring.binarytea.model.MenuItem;
import learning.spring.binarytea.model.Size;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @AfterEach
    public void tearDown() {
        jdbcTemplate = null;
    }

    @Test
    @Order(1)
    void testInsertItem() {
        List<MenuItem> items = Stream.of("Go橙汁", "Python气泡水", "JavaScript苏打水")
                .map(n -> MenuItem.builder().name(n)
                            .size(Size.MEDIUM)
                            .price(Money.ofMinor(CurrencyUnit.of("CNY"), 1200))
                            .build())
                .peek(m -> menuRepository.insertItem(m))
                .collect(Collectors.toList());

        for (int i = 0; i < 3; i++) {
            assertEquals(i + 1, items.get(i).getId());
            assertItem(i + 1L, items.get(i).getName());
        }
    }

    private void assertItem(Long id, String name) {
        Map<String, Object> result = jdbcTemplate.queryForMap("select * from t_menu where id = ?", id);
        assertEquals(name, result.get("name"));
        assertEquals(Size.MEDIUM.name(), result.get("size"));
        assertEquals(1200L, result.get("price"));
    }
}
