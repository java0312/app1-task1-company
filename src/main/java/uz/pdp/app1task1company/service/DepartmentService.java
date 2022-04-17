package uz.pdp.app1task1company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app1task1company.entity.Company;
import uz.pdp.app1task1company.entity.Department;
import uz.pdp.app1task1company.payload.ApiResponse;
import uz.pdp.app1task1company.payload.CompanyDto;
import uz.pdp.app1task1company.payload.DepartmentDto;
import uz.pdp.app1task1company.repository.CompanyRepository;
import uz.pdp.app1task1company.repository.DepartmentRepository;
import uz.pdp.app1task1company.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    WorkerRepository workerRepository;

    //__GET ALL
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    //__GET ONE BY ID
    public Department getDepartment(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

    //__GET ALL BY COMPANY
    public List<Department> getAllDepartmentByCompanyId(Integer companyId) {
        return departmentRepository.findAllByCompanyId(companyId);
    }

    //__ADD
    public ApiResponse addDepartment(DepartmentDto departmentDto) {
        boolean exists = departmentRepository.existsByNameAndCompanyId(
                departmentDto.getName(),
                departmentDto.getCompanyId());
        if (exists)
            return new ApiResponse("this department already exists!", false);

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (optionalCompany.isEmpty())
            return new ApiResponse("company not found!", false);
        Company company = optionalCompany.get();

        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setCompany(company);

        Department savedDepartment = departmentRepository.save(department);
        return new ApiResponse("department saved", true, savedDepartment);
    }

    //__EDIT
    public ApiResponse editDepartment(Integer id, DepartmentDto departmentDto) {

        boolean exists = departmentRepository.existsByNameAndCompanyIdAndIdNot(
                departmentDto.getName(),
                departmentDto.getCompanyId(),
                id);
        if (exists)
            return new ApiResponse("this department already exists!", false);

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (optionalCompany.isEmpty())
            return new ApiResponse("company not found!", false);

        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isEmpty())
            return new ApiResponse("department not found!", false);

        Company company = optionalCompany.get();
        Department department = optionalDepartment.get();

        department.setName(departmentDto.getName());
        department.setCompany(company);
        Department savedDepartment = departmentRepository.save(department);
        return new ApiResponse("department edited!", true, savedDepartment);
    }

    //__DELETE
    public ApiResponse deleteDepartment(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isEmpty())
            return new ApiResponse("department not found!", false);

        workerRepository.deleteAllByDepartmentId(id);
        departmentRepository.deleteById(id);
        return new ApiResponse("department deleted!", true, optionalDepartment.get());
    }
}
