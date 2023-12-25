package learning.spring.binarytea.repository;

import learning.spring.binarytea.model.Amount;
import learning.spring.binarytea.model.Order;
import learning.spring.binarytea.model.OrderStatus;
import learning.spring.binarytea.model.TeaMaker;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OrderMapperTest {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private TeaMakerMapper makerRepository;

    @Autowired
    private MenuItemMapper menuItemMapper;

    @Test
    @Transactional
    @Rollback
    public void testSaveAndFind() {
        TeaMaker maker = makerRepository.findById(2L);
        Order order = Order.builder()
                .status(OrderStatus.ORDERED)
                .maker(maker)
                .amount(Amount.builder()
                        .discount(90)
                        .totalAmount(Money.ofMinor(CurrencyUnit.of("CNY"), 1200))
                        .payAmount(Money.ofMinor(CurrencyUnit.of("CNY"), 1080))
                        .build())
                .build();
        assertEquals(1, orderMapper.save(order));

        Long orderId = order.getId();
        assertNotNull(orderId);
        assertEquals(1, orderMapper.addOrderItem(orderId, menuItemMapper.findById(2L)));
    }
}
