package org.example.resources;

/*import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;*/
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.example.entites.Task;
import org.example.restaurant.Club;

@Path("hellorest")
public class HelloRest {
	
	@GET
	@Path("string")
	@Produces(MediaType.APPLICATION_JSON)
	public String sayHello(){
		return "{\"message\" : \"Hello World!\"}";
	}
	
	/*@GET
	@Path("object")
	@Produces(MediaType.APPLICATION_JSON)
	public Task sayTask(){
		Task task = new Task();
		task.setName("teszt");
		task.setPriority(5);
		return task;
	}
	
	@GET
	@Path("jpa")
	@Produces(MediaType.APPLICATION_JSON)
	public Task jpaYo(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "org.hibernate.tutorial.jpa" );
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Task newTask = new Task();
		
		newTask.setName("To do do");
		newTask.setPriority(3);
		entityManager.persist(newTask);
		entityManager.getTransaction().commit();
		
		Task tt = entityManager.createQuery("from Task where id=1", Task.class).getResultList().get(0);
		
		entityManager.close();
		return tt;
	}*/
	
	@GET
	@Path("kaja")
	@Produces(MediaType.TEXT_PLAIN)
	public String kaja(){
		return Club.getMenu();
	}
	
}
