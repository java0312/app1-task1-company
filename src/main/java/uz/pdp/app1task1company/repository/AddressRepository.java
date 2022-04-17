package uz.pdp.app1task1company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.app1task1company.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
