package com.maia.quadro.service;

import com.maia.quadro.enums.UserRole;
import com.maia.quadro.enums.UserStatus;
import com.maia.quadro.exception.customException.EntityExistsException;
import com.maia.quadro.exception.customException.EntityNotFoundException;
import com.maia.quadro.model.User;
import com.maia.quadro.repository.UserRepository;
import com.maia.quadro.repository.projection.UserProjection;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Função auxiliar para verificar se já existe um usuário com base no CPF informado
    @Transactional(readOnly = true)
    public Boolean getByCpf(String cpf, UserStatus status) {
        Optional<User> user = userRepository.findByCpfAndStatus(cpf, status);
        return user.isPresent();
    }

    @Transactional(readOnly = false)
    public User create(User user) {
        if(getByCpf(user.getCpf(), UserStatus.ACTIVE)) throw new EntityExistsException(String.format("Usuário com CPF: {%s} já cadastrado no sistema", user.getCpf()));

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Page<UserProjection> getAll(Pageable pageable) {
        return userRepository.findAllPageable(UserStatus.ACTIVE, pageable);
    }

    @Transactional(readOnly = true)
    public User getById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com id: {%s} não encontrado", id))
        );
    }

    @Transactional(readOnly = false)
    public void updateInfo(UUID id, String name, UserRole role, Long sectorId) {
        User user = getById(id);
        user.setName(name);
        user.setRole(role);
        user.setSectorId(sectorId);
        userRepository.save(user);
    }

    @Transactional(readOnly = false)
    public void updatePassword(UUID id, String currentPassword, String newPassword, String confirmNewPassword) {
        if(!newPassword.equals(confirmNewPassword)) throw new RuntimeException("Nova senha não é igual a confirmação da nova senha");
        User user = getById(id);
        if(!currentPassword.equals(user.getPassword())) throw new RuntimeException("Senha atual inválida");
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Transactional(readOnly = false)
    public void delete(UUID id) {
        User user = getById(id);
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
    }
}
