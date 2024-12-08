package studyspots.AllStudySpots;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AllStudySpotsRepository extends JpaRepository<StudySpot, Integer>{
	
	boolean existsByName(String name);
	StudySpot findById(int spot_id);
	List<StudySpot> findByName(String name);
	List<StudySpot> findAll();
	void delete(StudySpot entity);
}
