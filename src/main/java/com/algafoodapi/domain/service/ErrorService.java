package com.algafoodapi.domain.service;

import com.algafoodapi.domain.repository.ErrorRepository;
import com.algafoodapi.error.ErrorEntity;
import com.algafoodapi.error.Errors;
import com.algafoodapi.error.LinkedErrors;
import com.algafoodapi.error.ValidateError;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ErrorService {

    @Autowired
    private ErrorRepository errorRepository;

    public ErrorEntity salvar(ErrorEntity errorEntity){
        Errors list = new Errors();
        List<LinkedHashMap> linkList = list.get(0).get("validateErrors");
        Gson gson = new Gson();

        String linkedGson = gson.toJson(linkList, LinkedErrors.class);
        Type type = new TypeToken<List<ValidateError>>(){}.getType();
        List<ValidateError> validateErrors = gson.fromJson(linkedGson, type);

        validateErrors.stream()
                .distinct()
                .parallel()
                .forEachOrdered(ve -> errorRepository.save(new ErrorEntity(ve.getId(), ve.getCode(), ve.getMessage(),
                        ve.getMessageFront(), ve.getCodeFront())));

        Optional<ErrorEntity> errorEntity1 = errorRepository.findById(validateErrors.get(0).getId());

        return errorRepository.save(errorEntity);
    }
}
