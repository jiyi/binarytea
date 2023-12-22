package learning.spring.binarytea.model;

import lombok.*;
import org.joda.money.Money;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {
    private Long id;
    private String name;
    private Size size;
    private Money price;
    private Date createTime;
    private Date updateTime;
}
