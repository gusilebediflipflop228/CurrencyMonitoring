package api.exchangecurrency.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rates", indexes = {
        @Index(name = "idx_currency_created", columnList = "currency_id, createdAt DESC")
})
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // precision=19(до 15 знаков до запятой и 4 после)
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal value;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rate rate)) return false;
        return id != null && id.equals(rate.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}