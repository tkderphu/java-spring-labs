package site.viosmash.projection;

import org.springframework.beans.factory.annotation.Value;

public interface EmpView1 {
    int getId();

    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();
    int getAge();
}
