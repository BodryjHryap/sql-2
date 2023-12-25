package pro.sky.hw35.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.hw35.model.AverageAgeOfStudents;
import pro.sky.hw35.model.LastFiveStudentsById;
import pro.sky.hw35.model.NumberOfStudents;
import pro.sky.hw35.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByAgeBetween(Integer min, Integer max);

    @Query(value = "SELECT COUNT(DISTINCT(id)) FROM student", nativeQuery = true)
    List<NumberOfStudents> getNumberOfStudents();

    @Query(value = "SELECT AVG(age) from student", nativeQuery = true)
    List<AverageAgeOfStudents> getAvgAgeOfStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<LastFiveStudentsById> lastFiveStudentsById();
}