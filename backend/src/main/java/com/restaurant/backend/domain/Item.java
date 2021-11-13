package com.restaurant.backend.domain;

import com.restaurant.backend.domain.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "name", nullable = false)
    protected String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    protected Category category;

    @Column(name = "description")
    protected String description;

    @Column(name = "image_url")
    protected String imageURL;

    @ManyToMany(fetch = FetchType.EAGER)
    protected List<Tag> tags;

    @Column(name = "in_menu", nullable = false)
    protected Boolean inMenu;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    protected List<ItemValue> itemValues;

    @Column(name = "type", nullable = false)
    protected ItemType itemType;

    protected ItemValue getItemValueAt(LocalDateTime dateTime) {
        // TODO
        return new ItemValue();
    }
}
