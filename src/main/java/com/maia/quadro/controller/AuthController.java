package com.maia.quadro.controller;

import com.maia.quadro.dto.auth.AuthLoginDto;
import com.maia.quadro.exception.ExceptionMessage;
import com.maia.quadro.security.JwtToken;
import com.maia.quadro.security.JwtUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtUserDetailsService detailsService, AuthenticationManager authenticationManager) {
        this.detailsService = detailsService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping()
    public ResponseEntity<?> auth(@RequestBody @Valid AuthLoginDto dto, HttpServletRequest request) {
        log.info("Processo de authenticação pelo cpf {}", dto.getCpf());

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(dto.getCpf(), dto.getPassword());

            authenticationManager.authenticate(authenticationToken);

            JwtToken token = detailsService.getTokenAuthenticated(dto.getCpf());

            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            log.error("Bad Credentials from cpf {}", dto.getCpf());
            return ResponseEntity
                    .badRequest()
                    .body(new ExceptionMessage(request, HttpStatus.BAD_REQUEST, "Credênciais inválidas, certifique-se de possuir uma conta registrada com o CPF e senha informada"));
        }
    }
}
