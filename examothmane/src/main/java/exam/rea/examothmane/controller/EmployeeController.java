package exam.rea.examothmane.controller;

import exam.rea.examothmane.bean.Employee;
import exam.rea.examothmane.dao.EmployeeDao;
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
    public void createEmployee(@RequestBody Employee employee) {
        employeeDao.saveEmployee(employee);
    }

    // READ: Récupérer tous les employés
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }

    // READ: Récupérer un employé par ID
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeDao.getEmployeeById(id);
    }

    // UPDATE: Mettre à jour un employé
    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setId(id); // Assurez-vous que l'ID est correctement défini avant la mise à jour
        employeeDao.updateEmployee(employee);
    }

    // DELETE: Supprimer un employé par ID
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeDao.deleteEmployeeById(id);
    }
}





