package com.restaurant.backend.domain;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items")
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "name")
    protected String name;

    // TODO: Implement Category
    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "category_id")
    // protected Category category;

    @Column(name = "description")
    protected String description;

    @Column(name = "image_url")
    protected String imageURL;

    @Column(name = "in_menu")
    protected Boolean inMenu;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    protected List<ItemValue> itemValues;

    // TODO: Implement type
    // @Column(name = "type")
    // protected EItemType itemType;
}
