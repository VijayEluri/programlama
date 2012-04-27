/**
 * Pr�cticas de Metodolog�a de la Programaci�n.
 * Jeroquest - Un ejemplo de Programaci�n orientada a objetos.
 * Interface PersonajeGrafico - Interfaz que modela la representaci�n gr�fica de un tipo de personaje
 *                              M�s concretamente, el icono que le representa en el tablero
 * @author Jorge Puente Peinador y Ramiro Varela Arias
 *
 */

import javax.swing.Icon;

public interface PersonajeGrafico {
	/**
	 * Devuelve la imagen de icono asociada a la clase del personaje 
	 * @return el icono asociado a la clase del personaje
	 */
	public Icon getImagenPersonaje();
}
