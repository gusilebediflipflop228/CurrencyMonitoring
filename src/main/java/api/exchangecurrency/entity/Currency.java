package api.exchangecurrency.entity;

import api.exchangecurrency.entity.enums.CurrencyStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "currencies",
        indexes = @Index(name = "idx_currencies_code", columnList = "code")) // Добавлен индекс на поле code
@SQLDelete(sql = "UPDATE currencies SET status = 'DELETED' WHERE id = ?")
@FilterDef(name = "deletedCurrencyFilter", parameters = @ParamDef(name = "status", type = String.class))
@Filter(name = "deletedCurrencyFilter", condition = "status != :status")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, unique = true, nullable = false, length = 3)
    private String code;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CurrencyStatus status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency currency)) return false;
        return id != null && id.equals(currency.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
