package com.esprit.gestiondesconges.user;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PatchMapping("change-password")
    public ResponseEntity<?> changePassword(@PathParam("email") String email,
          @RequestBody ChangePasswordRequest request

    ) {
        service.changePassword(email, request);
        return ResponseEntity.ok().build();
    }
}
