package main;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import clases.Animal;
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

	public static void mostrarAnimales(File fich) {
		boolean finArchivo = false;
		if (fich.exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fich));

				// Leer mientras no se alcance el fin del archivo
				while (!finArchivo) {
					try {
						Animal aux = (Animal) ois.readObject();
						System.out.println(aux.toString());
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
	}

	public static void añadir(File fich) {
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
				moos.writeObject(aux);
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
				oos.writeObject(aux);
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

	public static void modificarEdad(File fich) {
		boolean modificado = false;
		boolean finArchivo = false;
		File fichAux = new File("fichAux.dat");

		int edadMod;

		String id;
		System.out.println("Introduce el id del animal a modificar edad");
		id = Utilidades.introducirCadena();

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
							aux.setEdad(edadMod);
							System.out.println("Edad modificada");
							modificado = true;
						} else {
							System.out.println("No hay un animal con ese id");
						}
						oos.writeObject(aux);
					} catch (EOFException e) {
						// Fin del archivo alcanzado
						finArchivo = true;
					}
				}
				oos.close();
				ois.close();
				if (modificado) {
					System.out.println("Animal modificado");
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

	public static void eliminar(File fich) {
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
		File fich = new File("animales.dat");
		do {
			opcion = mostrarMenu();
			switch (opcion) {
			case 1:
				mostrarAnimales(fich);
				break;
			case 2:
				añadir(fich);
				break;
			case 3:
				modificarEdad(fich);
				break;
			case 4:
				eliminar(fich);
				break;
			}
		} while (opcion != 5);
	
	}

}
