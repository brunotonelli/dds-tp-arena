package data;

import com.google.common.net.HttpHeaders;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import domain.Asignacion;
import domain.Estudiante;

import java.util.List;

import javax.ws.rs.core.MediaType;

public class ServiceNotitas {
	
	 private Client client;
	    private static final String API = "http://notitas.herokuapp.com";
	    private static final String TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIxMTEyMjIzMzMiLCJybmQiOiJ5SXNmZFI"
	    									+ "wN2lIR3BRRmVjYU9KT2VRPT0ifQ.9pVJGUXhrJPQ-TptNCt971l0h_1dWqWgMrHAWXJchho";
	   
	    private static final String STUDENT = "student";
	    private static final String ASSIGNMENTS = "student/assignments";

	    
	    public ServiceNotitas() {
	        this.client = Client.create();
	    }

	    public Estudiante getEstudiante(String legajo) {	    	
	    	ClientResponse response = this.client.resource(API).path(STUDENT)
	    			.header(HttpHeaders.AUTHORIZATION, TOKEN)  //cambiar a token parametro
	                .accept(MediaType.APPLICATION_JSON)
	                .get(ClientResponse.class);
	    	
	    	if (response.getStatus() != 200) {
	    		throw new RuntimeException("Failed : HTTP error code : "
	    		+ response.getStatus());
	    	}

	    	String output = response.getEntity(String.class);
	    	System.out.println(output);
	    	return MapperNotitas.mapEstudiante(output);
	    }
	    
	    public List<Asignacion> getAsignaciones(String legajo, String token) {
	    	ClientResponse response = this.client.resource(API).path(ASSIGNMENTS)
	    			.header(HttpHeaders.AUTHORIZATION, TOKEN)  //cambiar a token parametro
	                .accept(MediaType.APPLICATION_JSON)
	                .get(ClientResponse.class);
	    	
	    	if (response.getStatus() != 200) {
	    		throw new RuntimeException("Failed : HTTP error code : "
	    		+ response.getStatus());
	    	}

	    	String output = response.getEntity(String.class);
	    	System.out.println(output);
	    	return MapperNotitas.mapAsignaciones(output);
	    }
	    
	    public void actualizarEstudiante(String nombre, String apellido, String legajo, String usuario, String token) {
	    	String input = "{"
					+ "\"code\":	\""	+ legajo	+ "\","
					+ "\"first_name\":	\"" + nombre + "\","
					+ "\"last_name\":	\"" + apellido + "\","
					+ "\"github_user\":	\"" + usuario + "\""
					+ "}";
			
			ClientResponse response = this.client.resource(API).path(STUDENT)
	    			.header(HttpHeaders.AUTHORIZATION, TOKEN) //cambiar a token parametro
	                .accept(MediaType.APPLICATION_JSON)
	                .put(ClientResponse.class, input);

			if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
				     + response.getStatus());
			}

			System.out.println("Output from Server .... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);
	    }

}
