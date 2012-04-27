/**
 * Pr�cticas de Metodolog�a de la Programaci�n.
 * Clase Partida - representa el estado de una partida y viene definida como: 
 * un conjunto de personajes, el tablero donde se mueven, la ronda actual y el total de rondas a jugar
 * @author Jorge Puente Peinador y Ramiro Varela Arias
 *
 */

public class Partida {
	protected Personaje personajes []; // personajes de la partida
	private Tablero tablero = null; // tablero de juego
	private int rondaActual; // ronda actual
	private int totalRondas; // n�mero m�ximo de rondas a jugar
	
	/**
	 * Obtiene el total de rondas de la partida
	 * @return el total de rondas de la partida
	 */
	public int getTotalRondas() {
		return totalRondas;
	}

	/** 
	 * Fija el total de rondas 
	 * (es un m�todo privado)
	 * @param totalRondas el total de rondas a jugar
	 */
	private void setTotalRondas(int totalRondas) {
		this.totalRondas = totalRondas;
	}

	/**
	 * Crea una partida nueva parametrizada
	 * @param numHeroes total de personajes h�roe a crear
	 * @param numMonstruos total de personajes monstruo a crear
	 * @param filas alto del tablero a crear
	 * @param columnas ancho del tablero a crear
	 * @param totalRondas n�mero total de rondas a jugar en la partida
	 */
	public Partida(int numHeroes, int numMonstruos, int filas, int columnas, int totalRondas)
	{
		// n�mero total de rondas
		setTotalRondas(totalRondas);

		// Creamos un tablero con las dimensiones dadas
		tablero = new Tablero(filas,columnas);

		// creamos los personajes
		personajes = new Personaje[numHeroes + numMonstruos];
		
		// h�roes al azar
		for(int x = 0; x < numHeroes; x++)
			if(Dado.tira()%2 == 0)// si es par creo un barbaro
				personajes[x] = new Barbaro("Barbaro" + x, "<SinJugador>");
			else // si es impar creo un enano
				personajes[x] = new Enano("Enano" + x, "<SinJugador>");

		// monstruos al azar
		for(int y = 0; y < numMonstruos; y++)
			if(Dado.tira()%2 == 0)// si es par creo una momia
				personajes[numHeroes + y] = new Momia("Momia" + y);
			else // si es impar creo un goblin
				personajes[numHeroes + y] = new Goblin("Goblin" + y);
		

		// primera ronda
		rondaActual = 1;
	}

	/**
	 * Obt�n la ronda actual de la partida
	 * @return la ronda actual 
	 */
	public int getRondaActual() {
		return rondaActual;
	}

	/**
	 * Fija la ronda actual
	 * @param rondaActual la nueva ronda actual
	 */
	public void setRondaActual(int rondaActual) {
		this.rondaActual = rondaActual;
	}


	/**
	 * Obt�n los personajes de la partida 
	 * @return array con los personajes de la partida 
	 */
	public Personaje[] getPersonajes()
	{
		return personajes;
	}

	/**
	 * Obt�n el tablero de la partida
	 * @return el tablero de la partida
	 */
	public Tablero getTablero() {
		return tablero;
	}

	/** 
	 * Genera la versi�n String imprimible del objeto
	 *  (M�todo reescrito)
	 * @return La versi�n como String imprimible de la partida
	 */
	@Override
	public String toString()
	{
		String s = "";
		for(int x = 0; x < personajes.length;x++ )
		{
			s += String.format("%s\n", personajes[x]);
		}
		s += getTablero();
		return s;
	}

}
