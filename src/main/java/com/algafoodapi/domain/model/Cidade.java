package com.algafoodapi.domain.model;

import com.algafoodapi.constraints.Groups;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@Data
@Entity
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank
    private String nome;

    //@JsonIgnoreProperties(value = "nome", allowGetters = true)
    @Valid
    @ManyToOne
   @ConvertGroup(from = Default.class, to = Groups.EstadoId.class)
    @JoinColumn(name = "estado_id")
    private Estado estado;
}
