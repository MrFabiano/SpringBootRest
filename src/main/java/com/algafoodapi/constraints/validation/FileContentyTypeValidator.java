package com.algafoodapi.constraints.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class FileContentyTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> allowedContentTypes;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.allowedContentTypes = Arrays.asList(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        return multipartFile == null || this.allowedContentTypes.contains(multipartFile.getContentType());
    }

}
    /*private DataSize allowed;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.allowed = DataSize.parse(Arrays.stream(constraintAnnotation.allowed()).findFirst().get());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value == null || value.getSize() <= this.allowed.toBytes();
    }

     */

