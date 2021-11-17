package com.restaurant.backend.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.restaurant.backend.domain.enums.ItemType;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    protected ItemValue getItemValueAt(LocalDateTime dateTime) {
        // TODO
        return new ItemValue();
    }
}
