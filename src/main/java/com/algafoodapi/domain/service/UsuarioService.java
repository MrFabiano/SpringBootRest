package com.algafoodapi.domain.service;

import com.algafoodapi.domain.dto.assembler.UsuarioDTOAssembler;
import com.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algafoodapi.domain.exception.NegocioException;
import com.algafoodapi.domain.model.Grupo;
import com.algafoodapi.domain.model.Usuario;
import com.algafoodapi.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    private static final String USUARIO_EM_USO = "Usuario está em uso?";

    @Autowired
    private UsuarioRepository usurioRepository;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioDTOAssembler dtoAssembler;

    @Transactional
    public Usuario salvar(Usuario usuario){
       usurioRepository.detach(usuario);

       Optional<Usuario> usuarioExistente = usurioRepository.findByEmail(usuario.getEmail());

       if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)){
           throw new NegocioException
                   (String.format("Ja existe um usuario cadastrado com o e-mail %s", usuario.getEmail()));
       }
       if(usuario.isNovo()){
           usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
       }

       return usurioRepository.save(usuario);
    }

    @Transactional
    public void excluir(Long usuarioId){
        try{
            usurioRepository.findById(usuarioId);
            usurioRepository.flush();
        }catch(DataIntegrityViolationException e){
            throw new NegocioException(String.format(USUARIO_EM_USO,  usuarioId));
        }catch(EmptyResultDataAccessException e){
            throw new NegocioException(e.getMessage());

        }
    }

    public Usuario buscarOuFalhar(Long usuarioId){
        return usurioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeEmUsoException
                        (String.format("Usuario ausente", usuarioId)));
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId){
        Usuario usuario = buscarOuFalhar(usuarioId);
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);

        usuario.adicionarGrupo(grupo);
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId){
        Usuario usuario = buscarOuFalhar(usuarioId);
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);

        usuario.removerGrupo(grupo);
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha){

        Usuario usuario = buscarOuFalhar(usuarioId);

        if(!passwordEncoder.matches(senhaAtual, usuario.getSenha())){
            throw new NegocioException("Senha atual não coincide com  a senha do usuario");
        }
        usuario.setSenha(passwordEncoder.encode(novaSenha));
    }
}
