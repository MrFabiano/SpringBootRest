package com.algafoodapi.controller;

import com.algafoodapi.controller.openapi.RestauranteFotoProdutoControllerOPenApi;
import com.algafoodapi.domain.dto.FotoProdutoDTO;
import com.algafoodapi.domain.dto.assembler.FotoProdutoAssembler;
import com.algafoodapi.domain.exception.EntidadeNaoEncontraException;
import com.algafoodapi.domain.exception.NegocioException;
import com.algafoodapi.domain.input.FotoProdutoInput;
import com.algafoodapi.domain.model.FotoProduto;
import com.algafoodapi.domain.model.Produto;
import com.algafoodapi.domain.service.FotoProdutoService;
import com.algafoodapi.domain.service.FotoStorageService;
import com.algafoodapi.domain.service.ProdutoService;
import com.algafoodapi.infrastructure.storage.StorageExecption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController implements RestauranteFotoProdutoControllerOPenApi{

    @Autowired
    private FotoProdutoService fotoProdutoService;

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private FotoProdutoAssembler fotoProdutoAssembler;
    @Autowired
    private FotoStorageService fotoStorage;


    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId,
                                        @PathVariable Long produtoId, @Valid  FotoProdutoInput fotoProdutoInput,
                                        @RequestPart(required = true) MultipartFile arquivo) throws IOException {

        Produto produto = produtoService.buscarOuFalhar(produtoId, restauranteId);

        //MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setTamanho(arquivo.getSize());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());

      FotoProduto fotoSalva = fotoProdutoService.salvarFoto(fotoProduto, arquivo.getInputStream());

      return fotoProdutoAssembler.toModelDomainObject(fotoSalva);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoDTO buscarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        try{
        FotoProduto fotoProduto = fotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
        return fotoProdutoAssembler.toModelDomainObject(fotoProduto);
    }catch(Exception e){
            throw new StorageExecption("Não foi encontrado arquivo", e);
        }
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> buscarFotoImagem(@PathVariable Long restauranteId,
                                                                @PathVariable Long produtoId,
                                                                @RequestHeader(name = "accept")String acceptHeader){
        try{
        FotoProduto fotoProduto = fotoProdutoService.buscarOuFalhar(restauranteId, produtoId);

        MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
        List<MediaType> mediaTypeAceitas = MediaType.parseMediaTypes(acceptHeader);

        verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypeAceitas);

        FotoStorageService.FotoRecuperada fotoRecuperada = fotoStorage.recuperar(fotoProduto.getNomeArquivo());

        if(fotoRecuperada.temUrl()){
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                    .build();
        }else {
            return ResponseEntity.ok()
                    .contentType(mediaTypeFoto)
                    .body(new InputStreamResource(fotoRecuperada.getInputStream()));
        }
        }catch (EntidadeNaoEncontraException | HttpMediaTypeNotAcceptableException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        try{
         fotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
       // Files.deleteIfExists(Path.of(fotoProduto.getNomeArquivo()));
        fotoProdutoService.excluir(restauranteId, produtoId);
    }catch (EntidadeNaoEncontraException e){
            throw new NegocioException(e.getMessage());
        }
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto,
                                                   List<MediaType> mediaTypeAceitas) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypeAceitas.stream()
                .anyMatch(mediaType -> mediaType.isCompatibleWith(mediaTypeFoto));
        if(!compativel){
            throw new HttpMediaTypeNotAcceptableException(mediaTypeAceitas);
        }
    }

        /*var nomeArquivo = UUID.randomUUID().toString()
                + "_" + fotoProdutoInput.getArquivo().getOriginalFilename();

        var arquivoFoto = Path.of("/home/fabiano/Documentos/catalogo", nomeArquivo);

        try {
            fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/
}
