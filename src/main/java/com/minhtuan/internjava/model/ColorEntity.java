package com.minhtuan.internjava.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="color")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ColorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String code;

    @ManyToMany(mappedBy = "colors", fetch = FetchType.LAZY)
    Set<ProductEntity> products = new HashSet<>();

}
