package com.restaurant.backend.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    public Item(Item source){
        this.setId(source.getId());
        this.setName(source.getName());
        this.setDescription(source.getDescription());
        this.setImageURL(source.getImageURL());
        this.setInMenu(source.getInMenu());
        this.setItemType(source.getItemType());
        this.setDeleted(source.getDeleted());
        this.setCategory(new Category(source.getCategory().getId(), source.getCategory().getName()));
        this.setTags(source.getTags().stream().map(tag -> new Tag(tag.getId(), tag.getName())).collect(Collectors.toList()));
        this.setItemValues(source.getItemValues().stream().map(val -> new ItemValue(val.getId(), val.getPurchasePrice(), val.getSellingPrice(), val.getFromDate(), this)).collect(Collectors.toList()));

    }
}
