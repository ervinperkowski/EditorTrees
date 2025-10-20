package editortrees;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class DisplayableBinaryTree extends JComponent {
	private static final long serialVersionUID = 1L;
	public static Node NULL_NODE = null;
	
	public static boolean hasParents = false;

	private static final Color BACKGROUND_COLOR = Color.DARK_GRAY;
	
	private static final Color FOWARD_ARROW_COLOR = new Color(0x3399FF);
	private static final Color PARENT_ARROW_COLOR = new Color(0x77619A);
	private static final String FONT_NAME = "Comic Sans MS"; 

	private int width;
	private int height;
	private EditTree tree;
	private JFrame frame;
	private double xDistance;
	private double circleRadius;
	private double yDistance;
	private double nodeX;
	private double nodeY;
	private double angle;
	private boolean goingCrazy;

	public DisplayableBinaryTree(EditTree tree, int windowWidth, int windowHeight, boolean visable) {
		this.angle = 0;
		this.width = windowWidth;
		this.height = windowHeight;
		this.tree = tree;
		
		this.goingCrazy = Math.random() < 0.05;
		this.show(visable);
		Runnable repainter = new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(10);
						repaint();
					}
				} catch (InterruptedException exception) {
					
				}
			}
		};
		new Thread(repainter).start();
	}

	public void show(boolean visable) {
		if (this.frame != null) {
			this.frame.toFront();
			return;
		}
		this.frame = new JFrame();
		this.frame.setFocusable(true);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setMinimumSize(new Dimension(this.tree.slowSize() * 20 + 18, this.tree.slowHeight() * 20 + 45));
		this.frame.setSize(new Dimension(this.width, this.height));
		
		this.frame.getContentPane().setBackground(BACKGROUND_COLOR);
		
		this.frame.add(this);
		this.frame.setVisible(visable);

	}

	public void close() {
		this.frame.dispose();
	}

	public void setSize(int windowWidth, int windowHeight) {
		this.width = windowWidth;
		this.height = windowHeight;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.width = this.frame.getWidth() - 18; 
		this.height = this.frame.getHeight() - 45; 

		int treeHeight = this.tree.slowHeight();
		int treeSize = this.tree.slowSize();
		if (treeSize < 1) {
			return;
		}

		this.xDistance = this.width / ((double) (treeSize)); 
		this.circleRadius = this.xDistance / 2.0; 
		Dimension minSize = new Dimension((int) (treeSize * 20 + 18), (int) (treeHeight * 30 + 45));
		if (minSize.getHeight() > 1080) {
			minSize.setSize(minSize.getWidth(), 1080);
		}
		if (minSize.getWidth() > 1920) {
			minSize.setSize(1920, minSize.getHeight());
		}
		this.frame.setMinimumSize(minSize);
		
		this.circleRadius *= 1.25;
		if (this.goingCrazy) {
			this.angle += 0.0001;
			
			this.circleRadius += 10 * Math.sin(3 * this.angle) + 2 * Math.cos(15 * this.angle);
		}
		this.xDistance = (this.width - this.circleRadius * 2) / ((double) (treeSize - 1));
		
		this.yDistance = (this.height - 2 * circleRadius) / ((double) (treeHeight));

		this.nodeX = this.circleRadius;
		this.nodeY = this.circleRadius;

		int size = 0;
		
		while (true) {
			
			FontMetrics metric = g2.getFontMetrics(new Font(FONT_NAME, Font.CENTER_BASELINE, size));
			int height = metric.getHeight();
			int width = metric.getMaxAdvance();
			
			double multiplyer = 1.5;
			
			if (Math.sqrt(height * height + width * width) > multiplyer * this.circleRadius) {
				g2.setFont(new Font(FONT_NAME, Font.PLAIN, --size));
				
				break; 
			}
			size++;
		}
		
		g2.setColor(Color.blue); 
		g2.fill(new Rectangle2D.Double(this.width - 5, 50, 10, 5));
		g2.fill(new Rectangle2D.Double(this.width - 10, 60, 20, 5));
		g2.fill(new Rectangle2D.Double(this.width - 15, 70, 30, 5));
		g2.fill(new Rectangle2D.Double(this.width - 20, 80, 40, 5));
		g2.fill(new Rectangle2D.Double(this.width - 25, 90, 50, 5));
		
		DisplayableNodeWrapper current = this.tree.root.displayableNodeWrapper;
		
		this.paintHelper(g2, current, this.nodeY);
		this.lineHelper(g2, current);
		
	}

	private void paintHelper(Graphics2D g2, DisplayableNodeWrapper current, double nodeY) {
		if (current.getNode().hasLeft()) {
			this.paintHelper(g2, current.getLeft(), nodeY + this.yDistance); 
		}
		
		current.setPoint(this.nodeX, nodeY);
		current.setCircleRadius(this.circleRadius);
		current.displayNode(g2); 
		this.nodeX += this.xDistance;
		if (current.getNode().hasRight()) {
			this.paintHelper(g2, current.getRight(), nodeY + this.yDistance); 
		}
	}

	private void lineHelper(Graphics2D g2, DisplayableNodeWrapper current) {
		if (hasParents) {
			if (current.getNode().hasParent()) {
				this.drawParentArrow(g2, current);
			}
		}
		
		if (current.getLeft() != null) {
			
			this.drawFowardArrow(g2, current.getPoint(), current.getLeft().getPoint());
			this.lineHelper(g2, current.getLeft()); 
		}
		
		if (current.getRight() != null) {
			
			this.drawFowardArrow(g2, current.getPoint(), current.getRight().getPoint());
			this.lineHelper(g2, current.getRight()); 
		}
	}

	private void drawParentArrow(Graphics2D g2, DisplayableNodeWrapper node) {
		Point2D.Double start = node.getPoint();
		Point2D.Double end = node.getParent().getPoint();
		g2.setColor(PARENT_ARROW_COLOR);
		double SIZE_MULTIPLIER = 1.5;
		AffineTransform transform = g2.getTransform(); 
		double angle = 0;
		try {
			angle = Math.atan2(end.getY() - start.getY(), end.getX() - start.getX());
		} catch (NullPointerException e) {
			
			return;
		}
		g2.translate(end.getX(), end.getY()); 
		g2.rotate(angle + Math.PI / 2.0); 
		
		g2.translate(0, this.circleRadius);
		double arrowLength = start.distance(end) - 2 * this.circleRadius; 
		double arrowLengthSqrt = Math.sqrt(arrowLength); 
		Node dataNode = node.getNode();
		if (dataNode == dataNode.getParent().left || dataNode == dataNode.getParent().right) {
			Line2D.Double line = new Line2D.Double(0, 0, 0, arrowLength - arrowLengthSqrt * 2);
			g2.draw(line);
		}

		Path2D.Double arrowHead = new Path2D.Double(); 
		
		arrowHead.moveTo(0, 0);
		arrowHead.lineTo(-arrowLengthSqrt / SIZE_MULTIPLIER, 2 * arrowLengthSqrt / SIZE_MULTIPLIER);
		arrowHead.lineTo(arrowLengthSqrt / SIZE_MULTIPLIER, 2 * arrowLengthSqrt / SIZE_MULTIPLIER);
		arrowHead.closePath();

		g2.fill(arrowHead);
		g2.setTransform(transform); 
	}

	private void drawFowardArrow(Graphics2D g2, Point2D.Double start, Point2D.Double end) {
		g2.setColor(FOWARD_ARROW_COLOR);
		AffineTransform transform = g2.getTransform(); 
		
		if (end == null || start == null) {
			System.out.println("NULL ANGLE");
		}
		double angle = 0;
		try {
			angle = Math.atan2(end.getY() - start.getY(), end.getX() - start.getX());
		} catch (NullPointerException e) {
			
			return;
		}
		g2.translate(end.getX(), end.getY()); 
		g2.rotate(angle + Math.PI / 2.0); 
		g2.translate(0, this.circleRadius); 
		double arrowLength = start.distance(end) - 2 * this.circleRadius; 
		Line2D.Double line = new Line2D.Double(0, 0, 0, arrowLength);
		g2.draw(line);

		Path2D.Double arrowHead = new Path2D.Double(); 
		double arrowLengthSqrt = Math.sqrt(arrowLength); 
		
		arrowHead.moveTo(0, 0);
		arrowHead.lineTo(-arrowLengthSqrt, arrowLengthSqrt * 2);
		arrowHead.lineTo(arrowLengthSqrt, arrowLengthSqrt * 2);
		arrowHead.closePath();

		g2.fill(arrowHead);
		g2.setTransform(transform); 
	}

	public static String getTimeUnits(long time) {
		double newTime = time;
		if (time < 1000) {
			return String.format("%d NanoSeconds", time);
		} else {
			newTime = time / 1000.0;
			if (newTime < 1000) {
				return String.format("%f MicroSeconds", newTime);
			} else {
				newTime /= 1000.0;
				if (newTime < 1000) {
					return String.format("%f MiliSeconds", newTime);
				} else {
					newTime /= 1000.0;
					if (newTime < 300) {
						return String.format("%f Seconds", newTime);
					} else {
						newTime /= 60.0;
						if (newTime < 180) {
							return String.format("%f Minutes", newTime);
						} else {
							return String.format("%f Hours", newTime / 60.0);
						}
					}
				}
			}
		}
	}
}
