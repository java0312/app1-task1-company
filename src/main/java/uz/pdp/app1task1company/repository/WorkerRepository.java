package uz.pdp.app1task1company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.app1task1company.entity.Worker;

import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Integer> {

    List<Worker> findAllByDepartmentId(Integer department_id);

    List<Worker> findAllByDepartment_CompanyId(Integer department_company_id);

    boolean existsByPhoneNumberAndDepartmentId(String phoneNumber, Integer department_id);

    boolean existsByPhoneNumberAndDepartmentIdAndIdNot(String phoneNumber, Integer department_id, Integer id);

    void deleteAllByDepartmentId(Integer department_id);

    void deleteAllByDepartment_CompanyId(Integer department_company_id);

}
