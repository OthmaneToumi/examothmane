package exam.rea.examothmane.dao;

import exam.rea.examothmane.bean.Employee;
import exam.rea.examothmane.exception.EmployeeNotFoundException;
import exam.rea.examothmane.exception.EmailAlreadyUsedException; // Si vous avez besoin de cette exception
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    private final String jdbcUrl = "jdbc:h2:mem:examdb"; 
    private final String jdbcUsername = "myuser";
    private final String jdbcPassword = "mypassword";

    // Connexion
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
    }

    // CREATE : Ajouter un employé
    public void saveEmployee(Employee employee) throws EmailAlreadyUsedException {
        String query = "INSERT INTO employees (nom, prenom, email) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Vérification de l'unicité de l'email
            if (isEmailUsed(employee.getEmail())) {
                throw new EmailAlreadyUsedException("Email déjà utilisé");
            }

            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getPrenom());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Vérifier si l'email est déjà utilisé
    private boolean isEmailUsed(String email) {
        String query = "SELECT COUNT(*) FROM employees WHERE email = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ : Récupérer tous les employés
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setName(resultSet.getString("nom"));
                employee.setPrenom(resultSet.getString("prenom"));
                employee.setEmail(resultSet.getString("email"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // READ : Récupérer un employé par ID
    public Employee getEmployeeById(Long id) {
        String query = "SELECT * FROM employees WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Employee(
                    resultSet.getLong("id"),
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    resultSet.getString("email")
                );
            } else {
                throw new EmployeeNotFoundException("Employé introuvable");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération de l'employé", e); // Optionnel
        }
    }

    // UPDATE : Mettre à jour un employé par ID
    public void updateEmployee(Employee employee) {
        String query = "UPDATE employees SET nom = ?, prenom = ?, email = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getPrenom());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setLong(4, employee.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new EmployeeNotFoundException("Employé introuvable");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE : Supprimer un employé par ID
    public void deleteEmployeeById(Long id) {
        String query = "DELETE FROM employees WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new EmployeeNotFoundException("Employé introuvable");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

