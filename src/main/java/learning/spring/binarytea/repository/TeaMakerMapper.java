package learning.spring.binarytea.repository;

import learning.spring.binarytea.model.TeaMaker;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface TeaMakerMapper {

    int save(TeaMaker maker);
    int update(TeaMaker maker);
    TeaMaker findById(Long id);
    List<TeaMaker> findAllWithRowBounds(RowBounds rowBounds);
    List<TeaMaker> findAllWithPage(int pageSize, int pageNum);

}
