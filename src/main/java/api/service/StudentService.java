package api.service;

import api.entity.Student;
import api.repository.StudentRepository;
import api.util.COURSE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);

       
        return studentOptional.orElse(null);
    }

    public Student createStudent(String num, String name, String email, COURSE course, double classification) {
        Student student = new Student(num, name, email, course, classification);
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, String num, String name, String email, COURSE course, Double classification) {
        Student s = getStudentById(id);
        if (s == null)
            return null;

        if (num == null || name == null || email == null || course == null || classification == null) {
            throw new RuntimeException("Parameters cannot be null.");
        }

        s.setNum(num);
        s.setName(name);
        s.setEmail(email);
        s.setCourse(course);
        s.setClassification(classification);
        return studentRepository.save(s);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

}
