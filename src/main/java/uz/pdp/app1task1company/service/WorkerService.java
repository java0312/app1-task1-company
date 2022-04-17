package uz.pdp.app1task1company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app1task1company.entity.Address;
import uz.pdp.app1task1company.entity.Department;
import uz.pdp.app1task1company.entity.Worker;
import uz.pdp.app1task1company.payload.ApiResponse;
import uz.pdp.app1task1company.payload.WorkerDto;
import uz.pdp.app1task1company.repository.AddressRepository;
import uz.pdp.app1task1company.repository.DepartmentRepository;
import uz.pdp.app1task1company.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    AddressRepository addressRepository;

    //__GET ALL
    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    //__GET ALL IN DEPARTMENT
    public List<Worker> getAllWorkersByDepartmentId(Integer departmentId) {
        return workerRepository.findAllByDepartmentId(departmentId);
    }

    //__GET ALL IN COMPANY
    public List<Worker> getAllWorkersByCompanyId(Integer companyId){
        return workerRepository.findAllByDepartment_CompanyId(companyId);
    }

    //__GET ONE BY ID
    public Worker getWorker(Integer id){
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);
    }


    //__ADD
    public ApiResponse addWorker(WorkerDto workerDto) {
        String phoneNumber = workerDto.getPhoneNumber();
        Integer departmentId = workerDto.getDepartmentId();

        boolean exists = workerRepository.existsByPhoneNumberAndDepartmentId(phoneNumber, departmentId);
        if (exists)
            return new ApiResponse("This worker exists!", false);

        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        if (optionalDepartment.isEmpty())
            return new ApiResponse("department not found!", false);

        Address address = new Address();
        address.setStreet(workerDto.getStreet());
        address.setHomeNumber(workerDto.getHomeNumber());
        Address savedAddress = addressRepository.save(address);

        Department department = optionalDepartment.get();

        Worker worker = new Worker();
        worker.setAddress(savedAddress);
        worker.setFullName(workerDto.getFullName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setDepartment(department);
        Worker savedWorker = workerRepository.save(worker);
        return new ApiResponse("worker saved!", true, savedWorker);
    }

    //__EDIT
    public ApiResponse editWorker(Integer id, WorkerDto workerDto) {
        String phoneNumber = workerDto.getPhoneNumber();
        Integer departmentId = workerDto.getDepartmentId();

        boolean exists = workerRepository.existsByPhoneNumberAndDepartmentIdAndIdNot(phoneNumber, departmentId, id);
        if (exists)
            return new ApiResponse("This worker exists!", false);

        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        if (optionalDepartment.isEmpty())
            return new ApiResponse("department not found!", false);

        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isEmpty())
            return new ApiResponse("worker not found!", false);
        Worker editingWorker = optionalWorker.get();

        Address address = editingWorker.getAddress();
        address.setStreet(workerDto.getStreet());
        address.setHomeNumber(workerDto.getHomeNumber());
        Address savedAddress = addressRepository.save(address);

        Department department = optionalDepartment.get();

        Worker worker = new Worker();
        worker.setAddress(savedAddress);
        worker.setFullName(workerDto.getFullName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setDepartment(department);
        Worker savedWorker = workerRepository.save(worker);
        return new ApiResponse("worker edited!", true, savedWorker);
    }

    //__DELETE
    public ApiResponse deleteWorker(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isEmpty())
            return new ApiResponse("worker not found!", false);

        workerRepository.deleteById(id);
        return new ApiResponse("Worker deleted!", true, optionalWorker.get());
    }
}
