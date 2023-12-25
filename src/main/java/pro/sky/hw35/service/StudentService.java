package pro.sky.hw35.service;

import org.springframework.stereotype.Service;
import pro.sky.hw35.model.AverageAgeOfStudents;
import pro.sky.hw35.model.LastFiveStudentsById;
import pro.sky.hw35.model.NumberOfStudents;
import pro.sky.hw35.model.Student;
import pro.sky.hw35.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student add(String name, Integer age) {
        Student newStudent = new Student(name, age);
        newStudent = studentRepository.save(newStudent);
        return newStudent;
    }

    public Student get(long id) {
        return studentRepository.findById(id).get();
    }

    public Student update(long id, String name, Integer age) {
        Student studentForUpdate = get(id);
        studentForUpdate.setName(name);
        studentForUpdate.setAge(age);
        return studentRepository.save(studentForUpdate);
    }

    public Student delete(long id) {
        Student studentForDelete = get(id);
        studentRepository.deleteById(id);
        return studentForDelete;
    }

    public List<Student> getWhenAgeBetween(Integer min, Integer max) {
        return studentRepository.findAllByAgeBetween(min, max);
    }

    public List<NumberOfStudents> getNumberOfStudents() {
        return studentRepository.getNumberOfStudents();
    }

    public List<AverageAgeOfStudents> getAvgAgeOfStudents() {
        return studentRepository.getAvgAgeOfStudents();
    }

    public List<LastFiveStudentsById> getLastFiveStudents() {
        return studentRepository.lastFiveStudentsById();
    }
}
