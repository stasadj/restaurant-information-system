package com.restaurant.backend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tag")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FilterDef(name = "deletedItemFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedItemFilter", condition = "deleted = :isDeleted")
@SQLDelete(sql = "UPDATE item SET deleted = true WHERE id = ?")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "name", nullable = false, unique = true)
    protected String name;

    @Column(columnDefinition = "boolean default false", name = "deleted", nullable = false)
    protected Boolean deleted = false;

    //had to add this one manually because we added the deleted attribute way after using the two-field constructor everywhere
    public Tag(Long id, String name){
        this.id = id;
        this.name = name;
    }
}