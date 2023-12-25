package pro.sky.hw35.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.hw35.model.Faculty;
import pro.sky.hw35.repository.FacultyRepository;

@Service
public class FacultyService {

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty add(String name, String color) {
        logger.info("Was invoked method for add faculty");
        Faculty newFaculty = new Faculty(name, color);
        newFaculty = facultyRepository.save(newFaculty);
        return newFaculty;
    }

    public Faculty get(long id) {
        logger.info("Was invoked method for get faculty");
        return facultyRepository.findById(id).get();
    }

    public Faculty update(long id, String name, String color) {
        logger.info("Was invoked method for update faculty");
        Faculty facultyForUpdate = facultyRepository.findById(id).get();
        facultyForUpdate.setName(name);
        facultyForUpdate.setColor(color);
        return facultyRepository.save(facultyForUpdate);
    }

    public Faculty delete(long id) {
        logger.info("Was invoked method for delete faculty");
        Faculty facultyForDelete = facultyRepository.findById(id).get();
        facultyRepository.deleteById(id);
        return facultyForDelete;
    }
}