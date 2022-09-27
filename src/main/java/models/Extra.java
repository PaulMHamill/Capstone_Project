package models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

public class Extra {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal perNightPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    /**
     * Extra can be reused for any additional items contributing to the total reservation price.
     */
    public enum Category {
        General, Dog
    }

    public Extra(String description, BigDecimal perNightPrice, Category category) {
        this.description = description;
        this.perNightPrice = perNightPrice;
        this.category = category;
    }

    public Extra() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPerNightPrice() {
        return perNightPrice;
    }

    public BigDecimal getTotalPrice(long totalNights) {
        return perNightPrice.multiply(BigDecimal.valueOf(totalNights));
    }

    public void setPerNightPrice(BigDecimal perNightPrice) {
        this.perNightPrice = perNightPrice;


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Extra extra = (Extra) o;
        return Objects.equals(description, extra.description) &&
                category == extra.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, category);
    }

    @Override
    public String toString() {
        return "Extra{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", perNightPrice=" + perNightPrice +
                ", category=" + category +
                '}';
    }
}
