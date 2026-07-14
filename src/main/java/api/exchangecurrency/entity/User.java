package api.exchangecurrency.entity;
import api.exchangecurrency.entity.enums.UserRole;
import api.exchangecurrency.entity.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users",
        indexes = @Index(name = "idx_users_username", columnList = "username")) // Добавлен индекс на поле username
@SQLDelete(sql = "UPDATE users SET status = 'DELETED' WHERE id = ?")
@FilterDef(name = "deletedUserFilter", parameters = @ParamDef(name = "status", type = String.class))
@Filter(name = "deletedUserFilter", condition = "status != :status")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 60)
    @ToString.Exclude // Исключаем пароль из toString() для безопасности
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Alert> alerts = new ArrayList<>();
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id != null && id.equals(user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
