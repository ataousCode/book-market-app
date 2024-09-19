package com.almousleck.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Role extends BaseEntity {
    @Column(unique = true)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
