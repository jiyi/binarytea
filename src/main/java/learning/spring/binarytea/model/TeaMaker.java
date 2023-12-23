package learning.spring.binarytea.model;

import lombok.*;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeaMaker {
    private Long id;
    private String name;
    private List<Order> orders;
    private Date createTime;
    private Date updateTime;
}
