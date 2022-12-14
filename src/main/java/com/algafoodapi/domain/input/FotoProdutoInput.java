package com.algafoodapi.domain.input;

import com.algafoodapi.constraints.validation.FileContentType;
import com.algafoodapi.constraints.validation.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoInput {

    @NotNull
    @FileSize(max = "100KB")
    @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    private MultipartFile arquivo;
    @NotBlank
    private String descricao;
}
