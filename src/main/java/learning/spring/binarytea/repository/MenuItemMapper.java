package learning.spring.binarytea.repository;

import learning.spring.binarytea.model.MenuItem;
import learning.spring.binarytea.support.handler.MoneyTypeHandler;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.EnumTypeHandler;

import java.util.List;

@Mapper
public interface MenuItemMapper {
    long count();
    int save(MenuItem menuItem);
    int update(MenuItem menuItem);
    MenuItem findById(@Param("id") Long id);
    int deleteById(@Param("id") Long id);
    List<MenuItem> findAll();
    List<MenuItem> findByOrderId(Long orderId);
}
