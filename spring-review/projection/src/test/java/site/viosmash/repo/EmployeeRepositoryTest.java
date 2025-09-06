package site.viosmash.repo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.viosmash.Employee;
import site.viosmash.projection.EmpView1;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void findById() {
        Employee employee = new Employee();
        employee.setFirstName("Phu");
        employee.setLastName("Quang");

        employeeRepository.save(employee);

        EmpView1 view = employeeRepository.findById(employee.getId());

        Assertions.assertEquals(view.getFullName(), "Phu Quang");
    }
}