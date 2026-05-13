package secureAPI.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import secureAPI.dto.ApiResponse;
import secureAPI.dto.LoginRequest;
import secureAPI.dto.RegisterRequest;
import secureAPI.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(
            @RequestBody RegisterRequest request) {

        String result = authService.register(request);

        if (result.equals("ALREADY_EXISTS")) {
            return ResponseEntity.status(409).body(
                new ApiResponse<>(false, 409, "Email already registered", null)
            );
        }

        return ResponseEntity.status(201).body(
            new ApiResponse<>(true, 201, "User registered successfully", null)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(
            @RequestBody LoginRequest request) {
        try {
            String token = authService.login(request);
            return ResponseEntity.ok(
                new ApiResponse<>(true, 200, "Login successful", token)
            );
        } catch (Exception e) {
            return ResponseEntity.status(401).body(
                new ApiResponse<>(false, 401, e.getMessage(), null)
            );
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Void>> updateUser(
            @PathVariable Long id,
            @RequestBody RegisterRequest request) {
        try {
            authService.updateUser(id, request);
            return ResponseEntity.ok(
                new ApiResponse<>(true, 200, "User updated successfully", null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(404).body(
                new ApiResponse<>(false, 404, e.getMessage(), null)
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long id) {
        try {
            authService.deleteUser(id);
            return ResponseEntity.ok(
                new ApiResponse<>(true, 200, "User deleted successfully", null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(404).body(
                new ApiResponse<>(false, 404, e.getMessage(), null)
            );
        }
    }
}