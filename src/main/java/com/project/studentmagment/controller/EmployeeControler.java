package com.project.studentmagment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.studentmagment.model.Employee;
import com.project.studentmagment.repository.EmployeeRepository;

//Controlador rest api
@CrossOrigin(origins = "http://localhost:3000") //conexion con react
@RestController
@RequestMapping("/api")
public class EmployeeControler {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get lista de empleados
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();		
	}
	
	//añadir empleado
	//RequestBody le pide al front un empleado para realizar la creacion.
	@PostMapping(value = "/employees", consumes = "application/json")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	//obtener el id de empleado
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
	    Optional<Employee> optionalEmployee = employeeRepository.findById(id);
	    
	    if (optionalEmployee.isPresent()) {
	        Employee employee = optionalEmployee.get();
	        return ResponseEntity.ok(employee);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	//Actualizar informacion de empleado por id
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
	    Optional<Employee> optionalEmployee = employeeRepository.findById(id);
	    
	    if (optionalEmployee.isPresent()) {
	        Employee employee = optionalEmployee.get();
	        employee.setName(employeeDetails.getName());
	        employee.setSurname(employeeDetails.getSurname());
	        employee.setEmailId(employeeDetails.getEmailId());
	        
	        Employee updatedEmployee = employeeRepository.save(employee);
	        return ResponseEntity.ok(updatedEmployee);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	//eliminar empleado por id
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
	    Optional<Employee> optionalEmployee = employeeRepository.findById(id);

	    if (optionalEmployee.isPresent()) {
	        Employee employee = optionalEmployee.get();
	        employeeRepository.delete(employee);
	        Map<String, Boolean> response = new HashMap<>();
	        response.put("deleted", Boolean.TRUE);
	        return ResponseEntity.ok(response);
	    } else {
	        
	        Map<String, Boolean> response = new HashMap<>();
	        response.put("deleted", Boolean.FALSE); 
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }
	}


	

}
