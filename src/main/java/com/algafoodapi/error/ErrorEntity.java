package com.algafoodapi.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ErrorEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private String Code;
    private String Id;
    private String Message;
    private String MessageFront;
    private String CodeFront;


}
