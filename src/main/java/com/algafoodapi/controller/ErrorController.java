package com.algafoodapi.controller;


import com.algafoodapi.domain.service.ErrorService;
import com.algafoodapi.error.ErrorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value =  "/v1/error", produces = MediaType.APPLICATION_JSON_VALUE)
public class ErrorController {

    @Autowired
    private ErrorService errorService;

    @PostMapping
    public ErrorEntity salvar(@RequestBody ErrorEntity errorEntity){
        return errorService.salvar(errorEntity);

    }
}
