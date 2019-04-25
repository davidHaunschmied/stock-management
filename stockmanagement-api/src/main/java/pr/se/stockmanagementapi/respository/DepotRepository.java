package pr.se.stockmanagementapi.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pr.se.stockmanagementapi.model.Depot;
import pr.se.stockmanagementapi.model.lightweights.DepotIdAndName;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepotRepository extends JpaRepository<Depot, Long> {
    Optional<Depot> findDepotByName(String name);

    @Query("SELECT d FROM Depot d")
    List<DepotIdAndName> findAllDepotIdAndName();
}
