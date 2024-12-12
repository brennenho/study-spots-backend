package studyspots.AllStudySpots;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import jakarta.persistence.Table;

@Table(name = "AllStudySpots")
public interface AllStudySpotsRepository extends CrudRepository<StudySpot, Integer> {
	boolean existsByName(String name);
	StudySpot findById(int spot_id);
	List<StudySpot> findByName(String name);

	@Override
	List<StudySpot> findAll();
	List<StudySpot> findByUserId(int userId);

	@Override
	void delete(StudySpot entity);
}
