/**
 * Pr�cticas de Metodolog�a de la Programaci�n.
 * Jeroquest - Un ejemplo de Programaci�n orientada a objetos.
 * GUI - Interfaz gr�fica de usuario del juego
 * Clases:
 * VentanaJeroquest - Ventana de la aplicaci�n que muestra la partida actual
 * 
 * @author Jorge Puente Peinador y Ramiro Varela Arias
 *
 */


import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JFrame; // necesario para la ventana
import javax.swing.JLabel; // muestra texto e im�genes

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import java.io.BufferedReader; // necesarios para leer pulsaciones de teclas del teclado
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Clase VentanaJeroquest, para representar informaci�n gr�fica
 * por medio de un panel y su objeto Partida a mostrar 
 * @author los profesores de MTP
 *
 */
public class VentanaJeroquest extends JFrame{
	// versi�n de la clase 1 (necesario para posibles serializaciones de la clase) 
	private static final long serialVersionUID = 1L;

	private MiPanelTablero panel = null; // panel de contenidos a mostrar
	
	/**
	 * Constructor de la ventana
	 */
	public VentanaJeroquest(Partida laPartida)
	{
		// creamos el objeto JFrame que representa la ventana
		super("Monitor Jeroquest");
		
		// creamos un panel tablero
		panel = new MiPanelTablero(laPartida);		
		
		// accion a realizar cuando se cierra la ventana -> se finaliza la aplicacion
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		
		// se a�ade el panel a la ventana
		add(panel);
		// establecemos las dimensiones de la ventana
		pack();
		// establecemos que no se pueda cambiar el tama�o
		setResizable(false);
		// hacemos visible la ventana
		setVisible(true);
	}
	
	
	public void muestraPartida()
	{
		panel.muestraPersonajes();
	}
	
	
	/**
	 * Finaliza la ventana y libera los recursos utilizados
	 */
	public void finaliza()
	{
		dispose();
	}
	
}


/**
 * Clase MiPanelTablero, representa un panel con los elementos
 * de la partida actual:
 * El tablero, los personajes, y la ronda actual
 * @author Profesores de Metodolog�a de la Programaci�n
 *
 */
class MiPanelTablero extends JLayeredPane 
{
	// esto evita un warning referido a clases serializables
	private static final long serialVersionUID = 1L;

	private Partida laPartida = null; // partida a mostrar
	
	// iconos de los elementos del tablero
	Icon casilla = new ImageIcon( getClass().getResource( "casilla.png" ) );
	
	// Etiqueta con la ronda actual
	JLabel rondaActualJLabel = null;
	
	/**
	 * Constructor del Panel con los personajes y el tablero
	 * @param p
	 */
	public MiPanelTablero(Partida p)
	{
		this.laPartida = p;
		iniciaTablero(p.getTablero().getFilas(),p.getTablero().getColumnas());	
	}
	
	/**
	 * Crea la respresentaci�n gr�fica del tablero en el panel
	 * @param filas n�mero de filas del tablero
	 * @param columnas n�mero de columnas del tablero
	 */
	private void iniciaTablero(int filas, int columnas)
	{
		// creamos la matriz de paneles/casilla
		setPreferredSize(new Dimension(32*(1 + columnas), 32*(1 + 1 + filas)));

		// creamos las etiquetas de las coordenadas
		for(Integer x = 0; x < columnas; x++){
			JLabel eti = new JLabel(x.toString());
			eti.setBounds(16+x*32, filas*32,32,32);
			add(eti, new Integer(1));
		}
		for(Integer x = 0; x < filas; x++){
			JLabel eti = new JLabel(x.toString());
			eti.setBounds(16+columnas*32, x*32,32,32);
			add(eti, new Integer(1));
		}			
			
		// creamos una distribuci�n en rejilla del tama�o del tablero
		// rellenamos la rejilla
		for(int x = 0; x < filas; x++)
			for(int y = 0; y < columnas; y++){
				// etiquta con la imagen de una casilla vac�a
				JLabel c = new JLabel(casilla);
				// dimensiones (32x32), posici�n (X,Y)
				c.setBounds(y*32, x*32,32,32);
				// se muestra el icono en su posici�n
				add(c, new Integer(1));
			}
		
		// creamos la etiqueta con la ronda actual
		rondaActualJLabel = new JLabel("Ronda: "+this.laPartida.getRondaActual()+"/"+this.laPartida.getTotalRondas());
		rondaActualJLabel.setBounds(16, (filas + 1) * 32, 128, 32);
		add(rondaActualJLabel,new Integer(1));
	}

	
	/**
	 * Muestra un icono representando el personaje como un icono en la capa 2
	 * @param pj personaje a mostrar
	 */
	private void muestraPersonaje(Personaje pj)
	{
		// si es un personaje grafico se pinta en el tablero
		if(pj instanceof PersonajeGrafico){
			XYLocation pos = pj.getPosicion();
			// si el personaje est� muerto no est� en el tablero
			if(pos != null){
				// se crea una label con el icono asociado a la clase del personaje
				JLabel lab = new MiJLabel(pj);
				// tama�o 32x32, y situado en su posici�n (X,Y)
				lab.setBounds(pos.getY()*32, pos.getX()*32,32,32);
				// se fija como tooltip la descripci�n como texto del personaje
				lab.setToolTipText(pj.toString());
				// se a�ade la etiqueta en la capa 2
				add(lab,new Integer(2)); 
			}
		}
	}

	/**
	 * Muestra todos los personajes en el tablero como iconos, donde su "tool tip" es
	 * la informaci�n del m�todo toString() de cada objeto
	 */
	protected void muestraPersonajes()
	{
		// muestra la ronda actual
		rondaActualJLabel.setText("Ronda: "+this.laPartida.getRondaActual()+"/"+this.laPartida.getTotalRondas());
		// elimina etiquetas de personaje anteriores
		for(Component comp: getComponents())
			if(getLayer(comp) == 2) // si est� en la capa 2 es un icono de personaje
				this.remove(comp);

		// regenera personajes
		for(Personaje pj: laPartida.getPersonajes())
			muestraPersonaje(pj);

		// actualiza su contenedor y lo refresca en pantalla
		validate();
		repaint();
	}
}

/**
 * Clase MiJLabel - modela una etiqueta gr�fica de Java con un personaje asociado
 * El disponer del personaje le permite mostrar una "barra de vida" encima del icono
 * @author Profesores de Metodolog�a de la Programaci�n
 *
 */
class MiJLabel extends JLabel{
	// esto evita un warning referido a clases serializables
	private static final long serialVersionUID = 1L;

	private Personaje pj;

	/**
	 * Constructor que crea el icono de un personaje sobre el tablero
	 * @param pj personaje a mostrar
	 */
	public MiJLabel(Personaje pj)
	{
		// asocia el icono del tipo de personaje al JLabel
		super(((PersonajeGrafico)pj).getImagenPersonaje());
		this.pj = pj;
	}
	
	/**
	 * Crea una barra indicando la vida actual del personaje
	 * El color indica su valor actual respecto al valor inicial (m�ximo) 
	 * @param g objeto Graphics sobre el que dibujar
	 * @param valorActual vida actual
	 * @param maximo vida m�xima
	 */
	private void barraProgreso(Graphics g, int valorActual, int maximo){
		// escalamos de forma que el m�ximo de vida ocupe 32 pixeles
		int width = (int) Math.round(valorActual*32/maximo);

		// Marco blanco de la barra
		g.setColor(Color.WHITE);
		g.drawRect(0, 28, 31, 3);

		// Fondo negro de la barra
		g.setColor(Color.BLACK);
		g.fillRect(1, 29, 30,2);

		// Fijamos color de la barra...
		// VERDE: si tiene m�s del 75% de la vida
		if(pj.getCuerpo() > 0.75 * pj.getCuerpoInicial())
			g.setColor(Color.GREEN);
		// AMARILLO: si tiene entre (50%..75%] de la vida
		else if(pj.getCuerpo() > 0.5 * pj.getCuerpoInicial())
			g.setColor(Color.YELLOW);
		// ROJO: si su vida es <= 50%
		else
			g.setColor(Color.RED);
		// Dibujamos la barra con la vida
		g.fillRect(1, 29, width-2,2);

	}
	
	/**
	 * Dibuja el personaje en su posici�n y sobre su icono la barra de vida
	 * @param objeto Graphics en el que se dibujar�
	 */
	@Override 
	public void paintComponent(Graphics g) {
		
		// el m�todo de JLabel mostrar� el icono del personaje
		super.paintComponent(g);

		// sobre el icono pintamos la barra de vida
		barraProgreso(g, pj.getCuerpo(), pj.getCuerpoInicial());
		
		
	}
	
}


/**
 * Clase est�tica Teclado para acceder a las pulsaciones del teclado
 * desde la consola de texto. 
 * (las ventanas tienen otro sistema, basado en eventos, para detectar pulsaciones) 
 * @author Profesores de Metodolog�a de la Programaci�n
 *
 */
class MiTeclado {
	/**
	 * Espera que se pulse la tecla Intro del teclado
	 */
	static	public void pulsaIntro() {

		BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Pulsa Intro para continuar");
		try {
			teclado.readLine();
		}
		catch (IOException ex1) {
		}
	}
}
