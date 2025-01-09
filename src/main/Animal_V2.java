package main;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import clases.*;
import utilidades.MyObjectOutputStream;
import utilidades.Utilidades;

public class Animal_V2 {
	
	public static int mostrarMenu() {
		System.out.println("1. Mostrar animales");
		System.out.println("2. Añadir animales");
		System.out.println("3. Modificar la edad de un animal por su id");
		System.out.println("4. Eliminar un animal por su id");
		System.out.println("5. Salir");
		return Utilidades.leerInt();
	}

	public static void mostrarAnimales(File fich,ArrayList<Animal> animales) {
		boolean finArchivo = false;
		if (fich.exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fich));

				// Leer mientras no se alcance el fin del archivo
				while (!finArchivo) {
					try {
						Animal aux = (Animal) ois.readObject();
						animales.add(aux);
					} catch (EOFException e) {
						// Fin del archivo alcanzado
						finArchivo = true;
					}
				}
				ois.close();

			} catch (Exception e) {
				System.out.println("Fatal error");
			}
		} else {
			System.out.println("Fichero nuevo");
		}
		if(animales.isEmpty())
		{
			System.out.println("No hay animales registrados");
		}else
		{
			for(Animal animal : animales)
			{
				System.out.println(animal.toString());
			}
		}
	}

	public static void añadir(File fich,ArrayList<Animal> animales) {
		String id, nombre;
		int edad;

		if (fich.exists()) {
			System.out.println("El fichero ya existe, se añadirán al final");
			MyObjectOutputStream moos;
			try {
				moos = new MyObjectOutputStream(new FileOutputStream(fich, true));
				System.out.println("Introduce el id: ");
				id = Utilidades.introducirCadena();
				System.out.println("Introduce el nombre: ");
				nombre = Utilidades.introducirCadena();
				System.out.println("Introduce la edad: ");
				edad = Utilidades.leerInt();
				Animal aux = new Animal(id, nombre, edad);
				animales.add(aux);
				moos.writeObject(animales);
				moos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("Fichero nuevo");
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(new FileOutputStream(fich));
				System.out.println("Introduce el id: ");
				id = Utilidades.introducirCadena();
				System.out.println("Introduce el nombre: ");
				nombre = Utilidades.introducirCadena();
				System.out.println("Introduce la edad: ");
				edad = Utilidades.leerInt();
				Animal aux = new Animal(id, nombre, edad);
				animales.add(aux);
				oos.writeObject(animales);
				oos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static void modificarEdad(File fich,ArrayList<Animal> animales) {
		boolean modificado = false;
		boolean finArchivo = false;
		boolean encontrado = false;
		File fichAux = new File("fichAux.dat");

		int edadMod;
		int pos=0;

		String id;
		System.out.println("Introduce el id del animal a modificar edad");
		id = Utilidades.introducirCadena();
		
		for(int i=0;i<animales.size()&&!encontrado;i++)
		{
			if(animales.get(i).getId().equalsIgnoreCase(id))
			{
				pos=i;
			}
		}

		if (fich.exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fich));
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichAux));

				// Leer mientras no se alcance el fin del archivo
				while (!finArchivo) {
					try {
						Animal aux = (Animal) ois.readObject();
						if (aux.getId().equalsIgnoreCase(id)) {
							System.out.println("Introduce la nueva edad");
							edadMod = Utilidades.leerInt();
							animales.get(pos).setEdad(edadMod);
							System.out.println("Edad modificada");
							modificado = true;
						} else {
							System.out.println("No hay un animal con ese id");
						}
						oos.writeObject(animales);
					} catch (EOFException e) {
						// Fin del archivo alcanzado
						finArchivo = true;
					}
				}
				oos.close();
				ois.close();
				if (modificado) {
					System.out.println("ArrayList modificado");
					if (fich.delete()) {
						fichAux.renameTo(fich);
					}
				}

			} catch (Exception e) {
				System.out.println("Fatal error");
			}
		} else {
			System.out.println("Fichero nuevo");
		}
	}

	public static void eliminar(File fich,ArrayList<Animal> animales) {
		File fichAux = new File("animalesAux.dat");
		boolean finArchivo = false;
		boolean modificado = false;
		ObjectOutputStream oos;
		ObjectInputStream ois;

		if (fich.exists()) {
			System.out.println("Introduce el id del animal: ");
			String id = Utilidades.introducirCadena();
			try {
				ois = new ObjectInputStream(new FileInputStream(fich));
				oos = new ObjectOutputStream(new FileOutputStream(fichAux));
				// Leer mientras no se alcance el fin del archivo
				while (!finArchivo) {
					try {
						Animal aux = (Animal) ois.readObject();
						if (!aux.getId().equals(id)) {
							oos.writeObject(aux);

						} else {
							modificado = true;
						}

					} catch (EOFException e) {
						// Fin del archivo alcanzado
						finArchivo = true;
					}
				}
				oos.close();
				ois.close();
				if (modificado) {
					System.out.println("Animal eliminado");
					if (fich.delete()) {
						fichAux.renameTo(fich);
					}
				} else {
					System.out.println("No existe un animal con ese id");
				}

			} catch (Exception e) {
				System.out.println("Fatal error");
			}
		} else {
			System.out.println("El fichero no existe");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int opcion;
		File fich = new File("animalesV2.dat");
		
		Animal a1 = new Animal("1","Leon", 3);
		Animal a2 = new Animal("2","Cabra", 2);
		
		ArrayList<Animal> animales =new ArrayList<>();
		animales.add(a1);
		animales.add(a2);
		
		ObjectOutputStream oos = null;

		try {
			oos = new ObjectOutputStream(new FileOutputStream(fich));
			oos.writeObject(animales);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		do {
			opcion = mostrarMenu();
			switch (opcion) {
			case 1:
				mostrarAnimales(fich,animales);
				break;
			case 2:
				añadir(fich,animales);
				break;
			case 3:
				modificarEdad(fich,animales);
				break;
			case 4:
				eliminar(fich,animales);
				break;
			}
		} while (opcion != 5);
	
	}

}
