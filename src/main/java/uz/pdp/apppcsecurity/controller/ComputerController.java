package uz.pdp.apppcsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apppcsecurity.entity.Computer;
import uz.pdp.apppcsecurity.repository.ComputerRepository;

import java.util.Optional;

@RestController
@RequestMapping("api/computer")
public class ComputerController {

    @Autowired
    ComputerRepository computerRepository;

    @PreAuthorize(value = "hasAuthority('GET_ALL_COMPUTERS')")
    @GetMapping
    public HttpEntity<?> getComputers(){
        return ResponseEntity.ok(computerRepository.findAll());
    }

    @PreAuthorize(value = "hasAuthority('ADD_COMPUTER')")
    @PostMapping()
    public HttpEntity<?> addComputer(@RequestBody Computer computer){
        return ResponseEntity.ok(computerRepository.save(computer));
    }

    @PreAuthorize(value = "hasAuthority('EDITE_COMPUTER')")
    @PutMapping("/{id}")
    public HttpEntity<?> editeComputer(@PathVariable Integer id, @RequestBody Computer computer){
        Optional<Computer> optionalComputer = computerRepository.findById(id);
        if (optionalComputer.isEmpty())
            return ResponseEntity.status(404).body(null);
        Computer editeComputer = optionalComputer.get();
        editeComputer.setName(computer.getName());
        editeComputer.setPrice(computer.getPrice());
        editeComputer.setGuarantee(computer.getGuarantee());
        return ResponseEntity.ok(computerRepository.save(editeComputer));
    }

    @PreAuthorize(value = "hasAuthority('DELETE_COMPUTER')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteComputer(@PathVariable Integer id){
        Optional<Computer> optionalComputer = computerRepository.findById(id);
        if (optionalComputer.isEmpty())
            return ResponseEntity.status(404).body(null);
        computerRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }

    @PreAuthorize(value = "hasAuthority('GET_ONE_COMPUTER')")
    @GetMapping("/{id}")
    public HttpEntity<?> getComputerById(@PathVariable Integer id){
        Optional<Computer> optionalComputer = computerRepository.findById(id);
        if (optionalComputer.isEmpty())
            return ResponseEntity.status(404).body(null);
        return ResponseEntity.ok(optionalComputer.get());
    }

}
