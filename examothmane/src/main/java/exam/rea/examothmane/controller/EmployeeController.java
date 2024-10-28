package exam.rea.examothmane.controller;

import exam.rea.examothmane.bean.Employee;
import exam.rea.examothmane.dao.EmployeeDao;
import exam.rea.examothmane.exception.EmployeeNotFoundException;
import exam.rea.examothmane.exception.EmailAlreadyUsedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    
    private final EmployeeDao employeeDao;

    public EmployeeController() {
        this.employeeDao = new EmployeeDao(); // Assurez-vous que cela est adapté à votre gestion de connexion
    }

    // CREATE: Ajouter un employé
    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        try {
            employeeDao.saveEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employé ajouté");
        } catch (EmailAlreadyUsedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // READ: Récupérer tous les employés
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }

    // READ: Récupérer un employé par ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        try {
            Employee employee = employeeDao.getEmployeeById(id);
            return ResponseEntity.ok(employee);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // UPDATE: Mettre à jour un employé
    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            employee.setId(id); // Assurez-vous que l'ID est correctement défini avant la mise à jour
            employeeDao.updateEmployee(employee);
            return ResponseEntity.ok("Employé mis à jour");
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE: Supprimer un employé par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        try {
            employeeDao.deleteEmployeeById(id);
            return ResponseEntity.ok("Employé supprimé");
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}






