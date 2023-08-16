package com.gloddy.server.search.school;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "school")
public class School {

    @Id
    private Long id;

    private String name;

    private String address;
}
