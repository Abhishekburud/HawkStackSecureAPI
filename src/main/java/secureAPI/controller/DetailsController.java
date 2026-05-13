package secureAPI.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import secureAPI.dto.ApiResponse;
import secureAPI.entity.Details;
import secureAPI.service.DetailsService;

@RestController
@RequestMapping("/details")
public class DetailsController {

    private final DetailsService detailsService;

    public DetailsController(DetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Details>> create(
            @RequestPart("name") String name,
            @RequestPart("email") String email,
            @RequestPart("countryCode") String countryCode,
            @RequestPart("phone") String phone,
            @RequestPart("address") String address,
            @RequestPart(value = "pdf", required = false) MultipartFile pdf,
            @RequestPart(value = "video", required = false) MultipartFile video) {
        try {
            Details saved = detailsService.save(name, email, countryCode,
                                                 phone, address, pdf, video);
            return ResponseEntity.status(201).body(
                new ApiResponse<>(true, 201, "Details created successfully", saved)
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                new ApiResponse<>(false, 500, e.getMessage(), null)
            );
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Details>>> getAll() {
        List<Details> list = detailsService.getAll();
        return ResponseEntity.ok(
            new ApiResponse<>(true, 200, "Details fetched successfully", list)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Details>> getById(@PathVariable Long id) {
        try {
            Details details = detailsService.getById(id);
            return ResponseEntity.ok(
                new ApiResponse<>(true, 200, "Detail fetched successfully", details)
            );
        } catch (Exception e) {
            return ResponseEntity.status(404).body(
                new ApiResponse<>(false, 404, e.getMessage(), null)
            );
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Details>> update(
            @PathVariable Long id,
            @RequestPart("name") String name,
            @RequestPart("email") String email,
            @RequestPart("countryCode") String countryCode,
            @RequestPart("phone") String phone,
            @RequestPart("address") String address,
            @RequestPart(value = "pdf", required = false) MultipartFile pdf,
            @RequestPart(value = "video", required = false) MultipartFile video) {
        try {
            Details updated = detailsService.update(id, name, email, countryCode,
                                                     phone, address, pdf, video);
            return ResponseEntity.ok(
                new ApiResponse<>(true, 200, "Details updated successfully", updated)
            );
        } catch (Exception e) {
            return ResponseEntity.status(404).body(
                new ApiResponse<>(false, 404, e.getMessage(), null)
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        try {
            detailsService.delete(id);
            return ResponseEntity.ok(
                new ApiResponse<>(true, 200, "Details deleted successfully", null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(404).body(
                new ApiResponse<>(false, 404, e.getMessage(), null)
            );
        }
    }
}