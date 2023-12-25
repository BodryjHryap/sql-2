package pro.sky.hw35.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import pro.sky.hw35.model.Student;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void create_success() throws Exception {
        //Подготовака входных данных
        Student studentForCreate = new Student("Tariq Lamptey", 18);
        //Подготовка ожидаемого результата
        Student expectedStudent = new Student("Tariq Lamptey", 18);

        //Начало теста
        Student actualStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentForCreate, Student.class);
        expectedStudent.setId(actualStudent.getId());
        assertNotNull(actualStudent);
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void get_success() throws Exception {
        //Подготовака входных данных
        Student studentForCreate = new Student("Tariq Lamptey", 18);

        //Подготовка ожидаемого результата
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentForCreate, Student.class);
        long id = postedStudent.getId();

        //Начало теста
        Student actualStudent = this.restTemplate.getForObject("http://localhost:" + port + "/student?id=" + id, Student.class);
        assertNotNull(actualStudent);
        assertEquals(postedStudent, actualStudent);
    }

    @Test
    void update_success() throws Exception {
        //Подготовака входных данных
        Student studentForCreate = new Student("Tariq Lamptey", 18);

        //Подготовка ожидаемого результата
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentForCreate, Student.class);

        long id = postedStudent.getId();

        Student studentForUpdate = new Student("Tariq Lamptey", 19);
        studentForUpdate.setId(id);

        //Начало теста
        this.restTemplate.put("http://localhost:" + port + "/student", studentForUpdate);
        Student actualStudent = this.restTemplate.getForObject("http://localhost:" + port + "/student?id=" + id, Student.class);
        assertNotNull(actualStudent);
        assertEquals(studentForUpdate, actualStudent);
    }

    @Test
    void delete_success() throws Exception {
        //Подготовака входных данных
        Student studentForCreate = new Student("Tariq Lamptey", 18);

        //Подготовка ожидаемого результата
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentForCreate, Student.class);
        long id = postedStudent.getId();

        //Начало теста
        Student studentForDelete = this.restTemplate.getForObject("http://localhost:" + port + "/student?id=" + id, Student.class);
        assertNotNull(studentForDelete);
        assertEquals(postedStudent, studentForDelete);
        this.restTemplate.delete("http://localhost:" + port + "/student?id=" + id);
        Student studentAfterDelete = this.restTemplate.getForObject("http://localhost:" + port + "/student?id=" + id, Student.class);
        assertNull(studentAfterDelete.getId());
        assertEquals(studentAfterDelete.getAge(), 0);
        assertNull(studentAfterDelete.getName());
    }
}
