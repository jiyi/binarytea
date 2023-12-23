package learning.spring.binarytea.repository;

import learning.spring.binarytea.model.MenuItem;
import learning.spring.binarytea.model.Order;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import java.util.List;

@Mapper
public interface OrderMapper {

    int save(Order order);
    int addOrderItem(Long orderId, MenuItem item);
    Order findById(Long id);
    List<Order> findByMakerId(Long makerId);
}
