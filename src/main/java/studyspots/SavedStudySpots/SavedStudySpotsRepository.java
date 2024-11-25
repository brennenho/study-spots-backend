package studyspots.SavedStudySpots;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SavedStudySpotsRepository extends CrudRepository<SavedStudySpots, Integer> {
	List<SavedStudySpots> findByUserId(int userId);

	@Transactional
	void deleteByUserIdAndSpotId(int userId, int spotId);

	boolean existsByUserIdAndSpotId(int userId, int spotId);
}
