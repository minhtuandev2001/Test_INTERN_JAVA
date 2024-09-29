package com.minhtuan.internjava.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @Column(columnDefinition = "LONGTEXT")
    String images;

    String description;

    Integer view;

    Integer stock;

    Integer discount;

    BigDecimal price;

    Float rating;

    String thumbnail;

    String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false) // nullable phải có ko có là sai
    CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "style_id", nullable = false)
    StyleEntity style;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="product_color",
    joinColumns = @JoinColumn(name="product_id", nullable = false),
    inverseJoinColumns = @JoinColumn(name = "color_id", nullable = false))
    Set<ColorEntity> colors = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_size",
            joinColumns = @JoinColumn(name = "product_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "size_id", nullable = false)
    )
    Set<SizeEntity> sizes = new HashSet<>();


    public void addColor(ColorEntity color){
        if(color != null){
            if(colors == null){
                colors = new HashSet<>();
            }
            colors.add(color);
        }
    }


    public void addSize(SizeEntity size){
        if(size != null){
            if(sizes == null){
                sizes = new HashSet<>();
            }
            sizes.add(size);
        }
    }
}
