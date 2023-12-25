package pro.sky.hw35.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.hw35.model.NumberOfStudents;
import pro.sky.hw35.service.StudentService;

import java.util.List;

@RestController
public class NumberOfStudentsController {

    private final StudentService studentService;

    public NumberOfStudentsController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/number-of-students")
    public List<NumberOfStudents> getNumberOfStudents() {
        return studentService.getNumberOfStudents();
    }
}
