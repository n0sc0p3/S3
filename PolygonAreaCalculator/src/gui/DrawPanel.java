package gui;

import areacaluculator.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel implements MouseMotionListener {
	private Angles polygon = new Angles(); // This object stores all the properties of the polygon drawn on screen
	// This object stores all the properties of the polygon drawn on screen
	private int verticesSize = 22;
	private Rectangle[] vertices = new Rectangle[4]; // Vertices are invisible squares having sides of length
	// verticesSize that allow us to drag polygon's vertices
	private java.awt.Polygon poly;
	private int currentVertexIndex = -1; // This stores the index of vertex currently being dragged by the user
	public Color mainColor = new Color(213, 213, 32);
	private Color backgroundColor = new Color(48, 5, 34);
	private float polygonWidth = 4;
	private JButton areaLabel = new JButton();
	private JButton reset = new JButton("RESET");
	public DrawPanel(JFrame jFrame) {
		//        this.polygon = new Angles();
		setSize(1000, 600);
		setBackground(backgroundColor);
		setLayout(new BorderLayout());
		int xP[] = {(getWidth()/2-100),(getWidth()/2+100),(getWidth()/2+100),(getWidth()/2-100)};
		int yP[] = {(getHeight()/2-100),(getHeight()/2-100),(getHeight()/2+100),(getHeight()/2+100)};
		polygon.setXs(xP);
		polygon.setYs(yP);
		polygon.changePoint();
		poly = new java.awt.Polygon(polygon.getXs(), polygon.getYs(), 4);
		
		
		areaLabel.setFont(new Font("Century Gothic", Font.BOLD, 30));
		areaLabel.setForeground(mainColor);
		areaLabel.setBackground(backgroundColor);
		areaLabel.setSize(200,50);
		add(areaLabel,BorderLayout.PAGE_START);
		reset.setFont(new Font("Century Gothic", Font.BOLD, 30));
		reset.setForeground(mainColor);
		reset.setBackground(backgroundColor);
		reset.setSize(200,50);
		add(reset,BorderLayout.PAGE_END);

		for (int i = 0; i < 4; i++) // This forLoop sets the initial locations(x,y) of all the vertices
		{
			Rectangle r = new Rectangle();
			r.setBounds((int) (polygon.getX(i) - verticesSize * 0.5), (int) (polygon.getY(i) - verticesSize * 0.5),
					verticesSize, verticesSize);
			vertices[i] = r;
		}
		
		areaLabel.setText(" Area - " + polygon.getArea() + " unit\u00B2" );
		
		reset.addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	    	jFrame.getContentPane().removeAll();
    	    	Info info = new Info(jFrame,0);
    	    	jFrame.getContentPane().add(info);
    	    	
    	   	}
    	    
    	});

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent me) { // This method implements the change of CursorStyle when mouse is
				// pressed over a vertex
				int x = me.getX();
				int y = me.getY();
				currentVertexIndex = getVertexIndex(x, y);

				if (getVertexIndex(x, y) >= 0) {
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					setCursor(Cursor.getDefaultCursor());
				}
			}
		});

		addMouseMotionListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) { // Draws initial polygon and all of it's properties such as area,
		// lengths and angles on the Frame

		super.paintComponent(g);
		g.setColor(backgroundColor);

		for (int i = 0; i < 4; i++) {
			((Graphics2D) g).draw(vertices[i]);
		}

		g.setColor(mainColor);
		((Graphics2D) g).setStroke(new BasicStroke(polygonWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		((Graphics2D) g).draw(poly);
		((Graphics2D) g).setFont(new Font("Century Gothic", Font.BOLD, 15));
		((Graphics2D) g).drawString(polygon.getAngle(0) + "\u00B0", (int) polygon.getX(0) + 10,
				(int) polygon.getY(0) + 25);
		((Graphics2D) g).drawString(polygon.getAngle(1) + "\u00B0", (int) polygon.getX(1) - 45,
				(int) polygon.getY(1) + 25);
		((Graphics2D) g).drawString(polygon.getAngle(2) + "\u00B0", (int) polygon.getX(2) - 40,
				(int) polygon.getY(2) - 15);
		((Graphics2D) g).drawString(polygon.getAngle(3) + "\u00B0", (int) polygon.getX(3) + 10,
				(int) polygon.getY(3) - 15);
		((Graphics2D) g).drawString(polygon.getLength(0) + " units", (int) polygon.getXMid(0) - 30,
				(int) polygon.getYMid(0) - 10);
		((Graphics2D) g).drawString(polygon.getLength(1) + " units", (int) polygon.getXMid(1) + 10,
				(int) polygon.getYMid(1) + 5);
		((Graphics2D) g).drawString(polygon.getLength(2) + " units", (int) polygon.getXMid(2) -30,
				(int) polygon.getYMid(2) + 20);
		((Graphics2D) g).drawString(polygon.getLength(3) + " units", (int) polygon.getXMid(3) - 80,
				(int) polygon.getYMid(3) +5);
		((Graphics2D) g).setFont(new Font("Century Gothic", Font.BOLD, 25));
		areaLabel.setText(" Area - " + polygon.getArea() + " unit\u00B2");
	}

	private int getVertexIndex(int x, int y) { // This functions returns the index of vertexSquare that contains (x,y)
		// else it returns -1
		for (int i = 0; i < 4; i++) {
			if (vertices[i].contains(x, y)) {
				return i;
			}
		}

		return -1;
	}

	public void mouseMoved(MouseEvent me) { // This method implements the change of CursorStyle when mouse is moved over
		// a vertex
		int x = me.getX();
		int y = me.getY();

		if (getVertexIndex(x, y) >= 0) {
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		} else
			setCursor(Cursor.getDefaultCursor());
	}

	public void mouseDragged(MouseEvent me) { // When a vertex is dragged, this method gets rid of initial polygon and
		// draws a polygon updated with its new properties and location
		int x = me.getX();
		int y = me.getY();

		if (getBounds().contains(x, y)) {
			if (currentVertexIndex >= 0) {

				Graphics g = getGraphics();
				((Graphics2D) g)
				.setStroke(new BasicStroke(polygonWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g.setColor(backgroundColor);
				((Graphics2D) g).setFont(new Font("Century Gothic", Font.BOLD, 15));
				((Graphics2D) g).draw(poly);
				((Graphics2D) g).drawString(polygon.getAngle(0) + "\u00B0", (int) polygon.getX(0) + 10,
						(int) polygon.getY(0) + 25);
				((Graphics2D) g).drawString(polygon.getAngle(1) + "\u00B0", (int) polygon.getX(1) - 45,
						(int) polygon.getY(1) + 25);
				((Graphics2D) g).drawString(polygon.getAngle(2) + "\u00B0", (int) polygon.getX(2) - 40,
						(int) polygon.getY(2) - 15);
				((Graphics2D) g).drawString(polygon.getAngle(3) + "\u00B0", (int) polygon.getX(3) + 10,
						(int) polygon.getY(3) - 15);
				((Graphics2D) g).drawString(polygon.getLength(0) + " units", (int) polygon.getXMid(0) - 30,
						(int) polygon.getYMid(0) - 10);
				((Graphics2D) g).drawString(polygon.getLength(1) + " units", (int) polygon.getXMid(1) + 10,
						(int) polygon.getYMid(1) + 5);
				((Graphics2D) g).drawString(polygon.getLength(2) + " units", (int) polygon.getXMid(2) -30,
						(int) polygon.getYMid(2) + 20);
				((Graphics2D) g).drawString(polygon.getLength(3) + " units", (int) polygon.getXMid(3) - 80,
						(int) polygon.getYMid(3) +5);

				((Graphics2D) g).setFont(new Font("Century Gothic", Font.BOLD, 25));
				areaLabel.setText(" Area - " + polygon.getArea() + " unit\u00B2");

				polygon.changePoint(x, y, currentVertexIndex);
				//            System.out.println("0-"+polygon.getCrossProduct(0) + "    1-"+polygon.getCrossProduct(1) + "    2-" +polygon.getCrossProduct(2) + "    3-"+polygon.getCrossProduct(3) + "    \n");
				vertices[currentVertexIndex].x = (int) (polygon.getX(currentVertexIndex) - verticesSize * 0.5);
				vertices[currentVertexIndex].y = (int) (polygon.getY(currentVertexIndex) - verticesSize * 0.5);
				poly = new java.awt.Polygon(polygon.getXs(), polygon.getYs(), 4);
				((Graphics2D) g).draw(vertices[currentVertexIndex]);

				g.setColor(mainColor);
				((Graphics2D) g).setFont(new Font("Century Gothic", Font.BOLD, 25));
				areaLabel.setText(" Area - " + polygon.getArea() + " unit\u00B2");
				((Graphics2D) g)
				.setStroke(new BasicStroke(polygonWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				((Graphics2D) g).draw(poly);
				((Graphics2D) g).setFont(new Font("Century Gothic", Font.BOLD, 15));
				((Graphics2D) g).drawString(polygon.getAngle(0) + "\u00B0", (int) polygon.getX(0) + 10,
						(int) polygon.getY(0) + 25);
				((Graphics2D) g).drawString(polygon.getAngle(1) + "\u00B0", (int) polygon.getX(1) - 45,
						(int) polygon.getY(1) + 25);
				((Graphics2D) g).drawString(polygon.getAngle(2) + "\u00B0", (int) polygon.getX(2) - 40,
						(int) polygon.getY(2) - 15);
				((Graphics2D) g).drawString(polygon.getAngle(3) + "\u00B0", (int) polygon.getX(3) + 10,
						(int) polygon.getY(3) - 15);
				((Graphics2D) g).drawString(polygon.getLength(0) + " units", (int) polygon.getXMid(0) - 30,
						(int) polygon.getYMid(0) - 10);
				((Graphics2D) g).drawString(polygon.getLength(1) + " units", (int) polygon.getXMid(1) + 10,
						(int) polygon.getYMid(1) + 5);
				((Graphics2D) g).drawString(polygon.getLength(2) + " units", (int) polygon.getXMid(2) -30,
						(int) polygon.getYMid(2) + 20);
				((Graphics2D) g).drawString(polygon.getLength(3) + " units", (int) polygon.getXMid(3) - 80,
						(int) polygon.getYMid(3) +5);

				g.dispose();
			}
		}
	}

	

}

//complete project submission should contain the following:
//UML diagrams:-Use Case diagram, Class diagram, CRC diagram(s), illustrating
//the design of your program.
//All the Java source code is necessary to compile, execute and demonstrate.
