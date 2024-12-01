package studyspots.AllStudySpots;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface AllStudySpotsRepository extends CrudRepository<StudySpot, String>{
	
	boolean existsByName(String name);
}
