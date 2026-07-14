package api.exchangecurrency.entity;

import api.exchangecurrency.entity.enums.AlertCondition;
import api.exchangecurrency.entity.enums.AlertStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts", indexes = {
        @Index(name = "idx_alerts_user_currency", columnList = "user_id, currency_id")
})
@SQLDelete(sql = "UPDATE alerts SET status = 'DELETED' WHERE id = ?")
@FilterDef(name = "deletedAlertFilter", parameters = @ParamDef(name = "status", type = String.class))
@Filter(name = "deletedAlertFilter", condition = "status != :status")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 19, scale = 4)
    @Positive
    private BigDecimal targetValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertCondition condition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertStatus status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alert alert)) return false;
        return id != null && id.equals(alert.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
