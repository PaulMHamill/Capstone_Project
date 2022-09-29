package repositorys;

import models.Pod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PodRepository extends JpaRepository<Pod, Long> {
}
