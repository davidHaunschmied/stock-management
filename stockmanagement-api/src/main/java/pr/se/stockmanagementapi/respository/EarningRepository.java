package pr.se.stockmanagementapi.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pr.se.stockmanagementapi.model.Earning;

import java.util.Date;
import java.util.Optional;

@Repository
public interface EarningRepository extends JpaRepository<Earning, Long> {
    Optional<Earning> findByDate(Date date);
}
