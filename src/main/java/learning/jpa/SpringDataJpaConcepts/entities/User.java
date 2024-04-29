package learning.jpa.SpringDataJpaConcepts.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.*;

@Setter
@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    private String role;

    private BigDecimal charges;

    @Version
    private Integer version;
}
