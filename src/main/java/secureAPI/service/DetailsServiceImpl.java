package secureAPI.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import secureAPI.entity.Details;
import secureAPI.repository.DetailsRepository;

@Service
public class DetailsServiceImpl implements DetailsService {

    private final DetailsRepository repository;

    public DetailsServiceImpl(DetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Details save(String name, String email, String countryCode,
                        String phone, String address,
                        MultipartFile pdf, MultipartFile video) {

        Details details = new Details();
        details.setName(name);
        details.setEmail(email);
        details.setCountryCode(countryCode);
        details.setPhone(phone);
        details.setAddress(address);

        if (pdf != null && !pdf.isEmpty()) {
            try {
                String pdfDir = System.getProperty("user.dir") + "/uploads/pdfs/";
                new java.io.File(pdfDir).mkdirs();
                String pdfFileName = pdf.getOriginalFilename().replace(" ", "_");
                String pdfPath = pdfDir + pdfFileName;
                pdf.transferTo(new java.io.File(pdfPath));
                details.setPdfPath(pdfPath);
            } catch (Exception e) {
                throw new RuntimeException("Failed to save PDF: " + e.getMessage());
            }
        }

        if (video != null && !video.isEmpty()) {
            try {
                String videoDir = System.getProperty("user.dir") + "/uploads/videos/";
                new java.io.File(videoDir).mkdirs();
                String videoFileName = video.getOriginalFilename().replace(" ", "_");
                String videoPath = videoDir + videoFileName;
                video.transferTo(new java.io.File(videoPath));
                details.setVideoPath(videoPath);
            } catch (Exception e) {
                throw new RuntimeException("Failed to save Video: " + e.getMessage());
            }
        }

        return repository.save(details);
    }

    @Override
    public List<Details> getAll() {
        return repository.findAll();
    }

    @Override
    public Details getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Details not found with id: " + id));
    }

    @Override
    public Details update(Long id, String name, String email, String countryCode,
                          String phone, String address,
                          MultipartFile pdf, MultipartFile video) {

        Details existing = getById(id);
        existing.setName(name);
        existing.setEmail(email);
        existing.setCountryCode(countryCode);
        existing.setPhone(phone);
        existing.setAddress(address);

        if (pdf != null && !pdf.isEmpty()) {
            try {
                String pdfDir = System.getProperty("user.dir") + "/uploads/pdfs/";
                new java.io.File(pdfDir).mkdirs();
                String pdfFileName = pdf.getOriginalFilename().replace(" ", "_");
                String pdfPath = pdfDir + pdfFileName;
                pdf.transferTo(new java.io.File(pdfPath));
                existing.setPdfPath(pdfPath);
            } catch (Exception e) {
                throw new RuntimeException("Failed to save PDF: " + e.getMessage());
            }
        }

        if (video != null && !video.isEmpty()) {
            try {
                String videoDir = System.getProperty("user.dir") + "/uploads/videos/";
                new java.io.File(videoDir).mkdirs();
                String videoFileName = video.getOriginalFilename().replace(" ", "_");
                String videoPath = videoDir + videoFileName;
                video.transferTo(new java.io.File(videoPath));
                existing.setVideoPath(videoPath);
            } catch (Exception e) {
                throw new RuntimeException("Failed to save Video: " + e.getMessage());
            }
        }

        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Details not found with id: " + id);
        }
        repository.deleteById(id);
    }
}