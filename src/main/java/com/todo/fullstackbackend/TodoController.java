package com.todo.fullstackbackend;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController	
@CrossOrigin("http://localhost:3000")
public class TodoController {
	
	@Autowired
	private TodoRepository todoRepository;
	
	@PostMapping("/todos")
	 public Todo createTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }
	
	 @GetMapping("/todos")
	    public List<Todo> getAllTodos() {
	        return todoRepository.findAll();
	  }
	 @GetMapping("/todos/{id}")
	 public Todo getTodoById(@PathVariable Long id) {
		 return todoRepository.findById(id).orElseThrow(()-> new TodoNotFoundException(id));
	 }
	 @PutMapping("/todos/{id}")
	 public Todo updatetask(@RequestBody Todo newtodo , @PathVariable Long id) {
		 	return todoRepository.findById(id)
		 			.map(todo ->{
		 				todo.setTitle(newtodo.getTitle());
		 				todo.setDescription(newtodo.getDescription());
		 				todo.setImportant(newtodo.isImportant());
		 				return todoRepository.save(todo);
		 			}).orElseThrow(()-> new TodoNotFoundException(id));	
	 }
	 @PutMapping("/todos/mark/{id}")
	 public Todo updatetask1(@PathVariable Long id) {
		 Todo change = todoRepository.findById(id).orElse(new Todo());
		 change.setCompleted(true);
		 return todoRepository.save(change);
	 }
	 
	 @DeleteMapping("/todos/{id}")
	 String deleteTodo(@PathVariable Long id) {
		 if(!todoRepository.existsById(id)) {
			 throw new TodoNotFoundException(id);
		 }
		 todoRepository.deleteById(id);
		 return  "task deleted sucessfully";
	 }
	 
}







