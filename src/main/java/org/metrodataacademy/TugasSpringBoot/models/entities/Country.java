package org.metrodataacademy.TugasSpringBoot.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 2, unique = true)
    private String code;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(
            name = "region_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_country"))
    private Region region;
}