package org.openoa.mapper;

import org.junit.jupiter.api.Test;
import org.openoa.FlowOAApplication;
import org.openoa.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = FlowOAApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class StudentMapperIntegrationTest {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testGetStudentList() {
        System.out.println("Creating table t_student if not exists...");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS t_student (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), age INT)");
        
        System.out.println("Inserting dummy data...");
        jdbcTemplate.update("INSERT INTO t_student (name, age) VALUES (?, ?)", "Test Student", 20);

        System.out.println("Testing getStudentList...");
        List<Student> students = studentMapper.getStudentList(null);
        assertNotNull(students);
        System.out.println("getStudentList returned size: " + students.size());
        
        if (students.isEmpty()) {
             throw new RuntimeException("Expected students list to not be empty after insertion");
        }
    }
}