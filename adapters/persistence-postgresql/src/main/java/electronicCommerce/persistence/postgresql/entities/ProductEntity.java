package electronicCommerce.persistence.postgresql.entities;

import electronicCommerce.domain.models.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("product")
public class ProductEntity {
    @Id
    private int id;
    @Column("brand_id")
    private int brandEntityId;
    @Column("start_date")
    private LocalDateTime startDate;
    @Column("end_date")
    private LocalDateTime endDate;
    @Column("price_list")
    private int priceList;
    @Column("product_id")
    private int productId;
    private int priority;
    private BigDecimal price;
    private String currency;

    public static Product toProduct(ProductEntity productEntity) {
        return Product.builder()
                .brandId(productEntity.getBrandEntityId())
                .startDate(productEntity.getStartDate())
                .endDate(productEntity.getEndDate())
                .priceListId(productEntity.getPriceList())
                .productId(productEntity.getProductId())
                .priority(productEntity.getPriority())
                .price(productEntity.getPrice())
                .currency(productEntity.getCurrency())
                .build();
    }
}
