package com.algafoodapi.domain.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@JsonRootName("cozinha")
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cozinha {

    //@JsonIgnore
    //@NotNull(groups = Groups.CozinhaId.class)
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    //@JsonIgnore
    @OneToMany(mappedBy = "cozinha")
    private List<Restaurante> restaurantes = new ArrayList<>();

}
