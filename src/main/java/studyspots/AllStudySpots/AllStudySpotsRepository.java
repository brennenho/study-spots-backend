package studyspots.AllStudySpots;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface AllStudySpotsRepository extends CrudRepository<StudySpot, Integer>{
	
	boolean existsByName(String name);
	StudySpot findById(int spot_id);
	List<StudySpot> findByName(String name);
	List<StudySpot> findAll();
	void delete(StudySpot entity);
}
