package site.viosmash.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import site.viosmash.Employee;
import site.viosmash.projection.EmpView1;
import site.viosmash.projection.EmpView3;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    @Query("SELECT e.id as id,\n" +
            " concat(e.firstName,' ', e.lastName) as fullName \n" +
            "e.age as age FROM Employee e where e.id = :id")
    EmpView1 findById(int id);

    @Query("SELECT e.id as id, " //
            + " e.firstName as firstName, " // (***)
            + " e.lastName as lastName, " // (***)
            + " e.age as age " //
            + " FROM Employee e")
    List<EmpView1> listEmployeeView2s();


    @Query("""
            SELECT e.id as id,
            e.firstName as firstName,
            e.lastName as lastName,
            e.age as age,
            e.department as department
            FROM EmpView3 e
            """)
    List<EmpView3> listEmployeeView3s();
}
