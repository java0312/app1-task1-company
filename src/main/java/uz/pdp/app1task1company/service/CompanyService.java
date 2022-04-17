package uz.pdp.app1task1company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app1task1company.entity.Address;
import uz.pdp.app1task1company.entity.Company;
import uz.pdp.app1task1company.payload.ApiResponse;
import uz.pdp.app1task1company.payload.CompanyDto;
import uz.pdp.app1task1company.repository.AddressRepository;
import uz.pdp.app1task1company.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AddressRepository addressRepository;

    //__GET ALL
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    //__GET ONE
    public Company getCompany(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }

    //__ADD
    public ApiResponse addCompany(CompanyDto companyDto) {
        String corpName = companyDto.getCorpName();
        boolean exists = companyRepository.existsByCorpName(corpName);
        if (exists)
            return new ApiResponse("This company already exists!", false);

        Address address = new Address();
        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomeNumber());
        Address savedAddress = addressRepository.save(address);

        Company company = new Company();
        company.setCorpName(corpName);
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(savedAddress);
        Company savedCompany = companyRepository.save(company);

        return new ApiResponse("Company saved!", true, savedCompany);
    }

    //__EDITE
    public ApiResponse editCompany(Integer id, CompanyDto companyDto) {

        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty())
            return new ApiResponse("Company not found!", false);

        String corpName = companyDto.getCorpName();
        boolean exists = companyRepository.existsByCorpNameAndIdNot(corpName, id);
        if (exists)
            return new ApiResponse("Company exists!", false);

        Company company = optionalCompany.get();
        Address address = company.getAddress();

        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomeNumber());
        Address savedAddress = addressRepository.save(address);

        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(savedAddress);
        Company savedCompany = companyRepository.save(company);

        return new ApiResponse("Company edited!", true, savedCompany);
    }

    //__DELETE
    public ApiResponse deleteCompany(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty())
            return new ApiResponse("Company not found!", false);

        companyRepository.deleteById(id);
        return new ApiResponse("company deleted!", true, optionalCompany.get());
    }
}
