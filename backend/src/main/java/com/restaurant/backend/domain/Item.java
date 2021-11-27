package com.restaurant.backend.domain;

import com.restaurant.backend.domain.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "item")
@SQLDelete(sql = "UPDATE item SET deleted = true WHERE id = ?")
// @Where(clause = "deleted = false")
@FilterDef(name = "deletedItemFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedItemFilter", condition = "deleted = :isDeleted")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @Column(name = "deleted", nullable = false)
    protected Boolean deleted;

    public ItemValue getItemValueAt(LocalDateTime dateTime) {
        ItemValue itemValueAt = null;
        for (ItemValue itemValue : itemValues)
            if (itemValue.getFromDate().isBefore(dateTime)) {
                if (itemValueAt == null)
                    itemValueAt = itemValue;
                else if (itemValueAt.getFromDate().isBefore(itemValue.getFromDate()))
                    itemValueAt = itemValue;
            }
        return itemValueAt;
    }
}
