package com.maia.quadro.controller;

import com.maia.quadro.dto.PageableDto;
import com.maia.quadro.dto.user.*;
import com.maia.quadro.mapper.PagebleMapper;
import com.maia.quadro.mapper.UserMapper;
import com.maia.quadro.model.AppUser;
import com.maia.quadro.repository.projection.UserProjection;
import com.maia.quadro.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Users", description = "Contém todas as operações relacionadas aos recursos dos usuários do quadro de chamados")
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createAdmin")
    @PreAuthorize("true")
    public ResponseEntity<UserResponseDto> createAdmin(@RequestBody @Valid UserAdminCreateDto dto) {
        AppUser appUser = userService.createAdmin(UserMapper.toUserAdmin(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(appUser));
    }

    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserCreateDto dto) {
        AppUser appUser = userService.create(UserMapper.toUser(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(appUser));
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    public ResponseEntity<PageableDto> getAll(Pageable pageable) {
        Page<UserProjection> users = userService.getAll(pageable);

        return ResponseEntity.ok(PagebleMapper.toDto(users));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDto> getById(@PathVariable UUID id) {
        AppUser appUser = userService.getById(id);
        return ResponseEntity.ok(UserMapper.toDto(appUser));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> updateInfo(@PathVariable UUID id, @RequestBody @Valid UserUpdateInfoDto dto) {
        userService.updateInfo(id, dto.getName(), dto.getRole(), dto.getSectorId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/updatePassword")
    @PreAuthorize("#dto.id == authentication.principal.id")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UserUpdatePasswordDto dto) {
        userService.updatePassword(dto.getId(), dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmNewPassword());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/restorePassword/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN') and #id ne authentication.principal.id")
    public ResponseEntity<Void> restorePassword(@PathVariable UUID id) {
        userService.restorePassword(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
