package com.maia.quadro.service;

import com.maia.quadro.enums.SectorStatus;
import com.maia.quadro.exception.customException.EntityExistsException;
import com.maia.quadro.exception.customException.EntityNotFoundException;
import com.maia.quadro.model.AppUser;
import com.maia.quadro.model.Sector;
import com.maia.quadro.repository.SectorRepository;
import com.maia.quadro.repository.projection.SectorProjection;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SectorService {

    private final SectorRepository sectorRepository;
    private final UserService userService;

    SectorService(SectorRepository sectorRepository, @Lazy UserService userService) {
        this.sectorRepository = sectorRepository;
        this.userService = userService;
    }

    // Função auxiliar para verificar se o setor existe com base no nome
    @Transactional(readOnly = true)
    private Boolean getByName(String name) {
        Optional<Sector> sector = sectorRepository.findByNameAndStatus(name, SectorStatus.ACTIVE);
        return sector.isPresent(); // Retorna true se o setor estiver presente
    }

    @Transactional(readOnly = false)
    public Sector create(Sector sector) {
        // Verifica se já existe algum setor criado com esse nome
        if(this.getByName(sector.getName())) throw new EntityExistsException("Setor com mesmo nome informado já existente");

        return sectorRepository.save(sector);
    }

    @Transactional(readOnly = true)
    public Page<SectorProjection> getAll(Pageable pageable) {
        return sectorRepository.findAllPageable(SectorStatus.ACTIVE, pageable);
    }

    @Transactional(readOnly = true)
    public Sector getById(Long id) {
        return sectorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Setor com id: {%s} não encontrado", id))
        );
    }

    @Transactional(readOnly = false)
    public void update(Long id, String name) {
        Sector sector = getById(id);
        if(this.getByName(name)) throw new EntityExistsException(String.format("Já existe um setor criado com nome: {%s}", name));
        sector.setName(name);
        sectorRepository.save(sector);
    }

    @Transactional(readOnly = false)
    public void delete(Long id) {
        // Verifica se é o setor default da aplicação
        if(id == 1) throw new DataIntegrityViolationException("Não é permitida a exclusão do setor default da aplicação");

        // Verifica se o setor possui algum usuário na qual pertence a este setor
        List<AppUser> users = userService.getUsersBySectorId(id);
        if(!users.isEmpty()) throw new DataIntegrityViolationException(String.format("O setor com ID: '%s' possui usuários ativos cadastrados, para excluí-lo, exclua primeiramente todos os usuários", id));

        Sector sector = getById(id);
        sector.setStatus(SectorStatus.INACTIVE);
        sectorRepository.save(sector);
    }
}
