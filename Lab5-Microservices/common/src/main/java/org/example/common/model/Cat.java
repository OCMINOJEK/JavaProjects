

package org.example.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(
        name = "cats"
)
public class Cat {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            nullable = false
    )
    private String name;
    @Column(
            name = "birth_date"
    )
    private LocalDate birthDate;
    @Column
    private String breed;
    @Column
    private String color;
    @ManyToOne
    @JoinColumn(
            name = "owner_id"
    )
    private Owner owner;
    @ManyToMany
    @JoinTable(
            name = "cat_friends",
            joinColumns = {@JoinColumn(
                    name = "cat_id"
            )},
            inverseJoinColumns = {@JoinColumn(
                    name = "friend_id"
            )}
    )
    private List<Cat> friends;
}
