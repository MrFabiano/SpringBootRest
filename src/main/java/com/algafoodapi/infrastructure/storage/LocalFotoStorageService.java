package com.algafoodapi.infrastructure.storage;

import com.algafoodapi.config.storage.StorageProperties;
import com.algafoodapi.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class LocalFotoStorageService implements FotoStorageService {

//    @Value("${algafood.storage.local.diretorio-fotos}")
//    private Path diretorioFotos;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try{
        Path arquivoPath = getArquivoPath(nomeArquivo);

        FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
                .inputStream(Files.newInputStream(arquivoPath))
                .build();
        return fotoRecuperada;
        //arquivoPath.getFileName().toFile().getCanonicalFile();
    }catch(Exception e){
            throw new StorageExecption("Não foi possivel recuperar a foto?", e);
        }

    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
        Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());

            FileCopyUtils.copy(novaFoto.getInputStream(),
                    Files.newOutputStream(arquivoPath));
        } catch (IOException e) {
            throw new StorageExecption("Não foi possivel armazar arquivo", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        Path arquivoPath = getArquivoPath(nomeArquivo);
        try {
            Files.deleteIfExists(arquivoPath);
        } catch (IOException e) {
            throw new StorageExecption("Não foi possivel excluir arquivo", e);
        }
    }

    private Path getArquivoPath(String nomeArquivo){
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
    }

}

