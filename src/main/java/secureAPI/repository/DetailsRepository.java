package secureAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import secureAPI.entity.Details;



@Repository
public interface DetailsRepository extends JpaRepository<Details, Long> {
}