package secureAPI.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import secureAPI.entity.Details;

public interface DetailsService {

    Details save(String name, String email, String countryCode,
                 String phone, String address,
                 MultipartFile pdf, MultipartFile video);

    List<Details> getAll();

    Details getById(Long id);

    Details update(Long id, String name, String email, String countryCode,
                   String phone, String address,
                   MultipartFile pdf, MultipartFile video);

    void delete(Long id);
}