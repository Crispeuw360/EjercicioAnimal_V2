package clases;

import java.io.Serializable;

public class Animal implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String nombre;
	private int edad;
	
	public Animal(String id, String nombre, int edad) 
	{

		this.id = id;
		this.nombre = nombre;
		this.edad = edad;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	@Override
	public String toString() {
		return "Animal [id=" + id + ", nombre=" + nombre + ", edad=" + edad + "]";
	}
}
