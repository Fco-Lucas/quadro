package com.maia.quadro.service;

import com.maia.quadro.enums.UserRole;
import com.maia.quadro.enums.UserStatus;
import com.maia.quadro.exception.customException.EntityExistsException;
import com.maia.quadro.exception.customException.EntityNotFoundException;
import com.maia.quadro.model.AppUser;
import com.maia.quadro.model.Sector;
import com.maia.quadro.repository.UserRepository;
import com.maia.quadro.repository.projection.UserProjection;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SectorService sectorService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, SectorService sectorService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.sectorService = sectorService;
        this.passwordEncoder = passwordEncoder;
    }

    // Função auxiliar para verificar se já existe um usuário com base no CPF informado
    @Transactional(readOnly = true)
    private Boolean getByCpf(String cpf, UserStatus status) {
        Optional<AppUser> user = userRepository.findByCpfAndStatus(cpf, status);
        return user.isPresent();
    }

    @Transactional(readOnly = false)
    public AppUser createAdmin(AppUser appUser) {
        // Verifica se já existe um usuário 'ADMIN' cadastrado
        Optional<AppUser> existsUserAdmin = userRepository.findByRoleAndStatus(UserRole.ADMIN, UserStatus.ACTIVE);
        if(existsUserAdmin.isPresent()) throw new DataIntegrityViolationException("Apenas 1 usuário com role: 'ADMIN' pode ser cadastrado no sistema");

        // Busca o setor com ID '1'
        Sector sector = sectorService.getById(1L);
        Long sectorId = sector.getId();

        // Verifica se já existe um usuário cadastrado com o CPF informado
        if(getByCpf(appUser.getCpf(), UserStatus.ACTIVE)) throw new EntityExistsException(String.format("Usuário com CPF: {%s} já cadastrado no sistema", appUser.getCpf()));
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setRole(UserRole.ADMIN);
        appUser.setSectorId(sectorId);
        return userRepository.save(appUser);
    }

    @Transactional(readOnly = false)
    public AppUser create(AppUser appUser) {
        // Se estiver tentando cadastrar um usuário ADMIN não permite
        if(appUser.getRole().name().equals("ADMIN")) throw new DataIntegrityViolationException("Para cadastrar um usuário com role 'ADMIN' utilize o endpoint /createAdmin");

        // Busca o setor e verifica se ele existe
        Sector sector = sectorService.getById(appUser.getSectorId());

        // Verifica se já existe um usuário cadastrado com o CPF informado
        if(getByCpf(appUser.getCpf(), UserStatus.ACTIVE)) throw new EntityExistsException(String.format("Usuário com CPF: {%s} já cadastrado no sistema", appUser.getCpf()));

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return userRepository.save(appUser);
    }

    @Transactional(readOnly = true)
    public Page<UserProjection> getAll(Pageable pageable) {
        return userRepository.findAllPageable(UserStatus.ACTIVE, pageable);
    }

    @Transactional(readOnly = true)
    public AppUser getById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com id: {%s} não encontrado", id))
        );
    }

    @Transactional(readOnly = false)
    public void updateInfo(UUID id, String name, UserRole role, Long sectorId) {
        AppUser appUser = getById(id);
        appUser.setName(name);
        appUser.setRole(role);
        appUser.setSectorId(sectorId);
        userRepository.save(appUser);
    }

    @Transactional(readOnly = false)
    public void updatePassword(UUID id, String currentPassword, String newPassword, String confirmNewPassword) {
        if(!newPassword.equals(confirmNewPassword)) throw new RuntimeException("Nova senha não é igual a confirmação da nova senha");
        AppUser appUser = getById(id);
        if(!passwordEncoder.matches(currentPassword, appUser.getPassword())) throw new RuntimeException("Senha atual inválida");
        appUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(appUser);
    }

    @Transactional(readOnly = false)
    public void restorePassword(UUID id) {
        AppUser appUser = getById(id);
        String newPassword = passwordEncoder.encode("123456");
        appUser.setPassword(newPassword);
        userRepository.save(appUser);
    }

    @Transactional(readOnly = false)
    public void delete(UUID id) {
        AppUser appUser = getById(id);
        appUser.setStatus(UserStatus.INACTIVE);
        userRepository.save(appUser);
    }

    @Transactional(readOnly = true)
    public AppUser getUserByCpf(String cpf) {
        return userRepository.findByCpfAndStatus(cpf, UserStatus.ACTIVE).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com CPF: '%s' não encontrado", cpf))
        );
    }

    @Transactional(readOnly = true)
    public UserRole getRoleByCpf(String cpf) {
        return userRepository.findRoleByCpf(cpf, UserStatus.ACTIVE);
    }

    @Transactional(readOnly = true)
    public List<AppUser> getUsersBySectorId(Long sectorId) {
        // Verifica primeiro se o setor existe
        Sector sector = sectorService.getById(sectorId);
        return userRepository.findBySectorIdAndStatus(sectorId, UserStatus.ACTIVE);
    }
}
