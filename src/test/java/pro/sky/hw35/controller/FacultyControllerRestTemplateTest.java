package pro.sky.hw35.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import pro.sky.hw35.model.Faculty;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void create_success() throws Exception {
        //Подготовака входных данных
        Faculty facultyForCreate = new Faculty("Slytherin", "yellow");
        //Подготовка ожидаемого результата
        Faculty expectedFaculty = new Faculty("Slytherin", "yellow");

        //Начало теста
        Faculty actualFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyForCreate, Faculty.class);
        expectedFaculty.setId(actualFaculty.getId());
        assertNotNull(actualFaculty);
        assertEquals(expectedFaculty, actualFaculty);
    }

    @Test
    void get_success() throws Exception {
        //Подготовака входных данных
        Faculty facultyForCreate = new Faculty("Slytherin", "yellow");

        //Подготовка ожидаемого результата
        Faculty postedFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyForCreate, Faculty.class);
        long id = postedFaculty.getId();

        //Начало теста
        Faculty actualFaculty = this.restTemplate.getForObject("http://localhost:" + port + "/faculty?id=" + id, Faculty.class);
        assertNotNull(actualFaculty);
        assertEquals(postedFaculty, actualFaculty);
    }

    @Test
    void update_success() throws Exception {
        //Подготовака входных данных
        Faculty facultyForCreate = new Faculty("Slytherin", "yellow");

        //Подготовка ожидаемого результата
        Faculty postedFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyForCreate, Faculty.class);

        long id = postedFaculty.getId();

        Faculty facultyForUpdate = new Faculty("Slytherin", "yellow");
        facultyForUpdate.setId(id);

        //Начало теста
        this.restTemplate.put("http://localhost:" + port + "/faculty", facultyForUpdate);
        Faculty actualFaculty = this.restTemplate.getForObject("http://localhost:" + port + "/faculty?id=" + id, Faculty.class);
        assertNotNull(actualFaculty);
        assertEquals(facultyForUpdate, actualFaculty);
    }

    @Test
    void delete_success() throws Exception {
        //Подготовака входных данных
        Faculty facultyForCreate = new Faculty("Slytherin", "yellow");

        //Подготовка ожидаемого результата
        Faculty postedFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyForCreate, Faculty.class);
        long id = postedFaculty.getId();

        //Начало теста
        Faculty facultyForDelete = this.restTemplate.getForObject("http://localhost:" + port + "/faculty?id=" + id, Faculty.class);
        assertNotNull(facultyForDelete);
        assertEquals(postedFaculty, facultyForDelete);
        this.restTemplate.delete("http://localhost:" + port + "/faculty?id=" + id);
        Faculty facultyAfterDelete = this.restTemplate.getForObject("http://localhost:" + port + "/faculty?id=" + id, Faculty.class);
        assertNull(facultyAfterDelete.getId());
        assertNull(facultyAfterDelete.getColor());
        assertNull(facultyAfterDelete.getName());
    }
}
