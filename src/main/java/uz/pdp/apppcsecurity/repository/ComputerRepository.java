package uz.pdp.apppcsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.apppcsecurity.entity.Computer;

@Repository
public interface ComputerRepository extends JpaRepository<Computer,Integer> {



}
