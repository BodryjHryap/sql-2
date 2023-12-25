package pro.sky.hw35.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.hw35.model.AverageAgeOfStudents;
import pro.sky.hw35.service.StudentService;

import java.util.List;

@RestController
public class AverageAgeOfStudentsController {

    private final StudentService studentService;

    public AverageAgeOfStudentsController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/avg-age-of-students")
    public List<AverageAgeOfStudents> getAvgAgeOfStudents() {
        return studentService.getAvgAgeOfStudents();
    }
}
