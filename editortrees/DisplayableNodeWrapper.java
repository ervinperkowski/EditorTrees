package editortrees;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class DisplayableNodeWrapper {
	
	private static Color CIRCLE_COLOR = Color.WHITE;
	
	private static Color TEXT_COLOR = new Color(0x66FFB2);
	private Point.Double point;
	private double radius;
	private Node node;

	public DisplayableNodeWrapper(Node node) {
		this.point = null;
		this.radius = -10;
		this.node = node;
	}

	public DisplayableNodeWrapper getLeft() {
		if (this.node.left != Node.NULL_NODE) {
			return this.node.left.displayableNodeWrapper;
		}
		return null;
	}

	public DisplayableNodeWrapper getRight() {
		if (this.node.right != Node.NULL_NODE) {
			return this.node.right.displayableNodeWrapper;
		}
		return null;
	}
	
	public DisplayableNodeWrapper getParent() {
		return this.node.getParent().displayableNodeWrapper;
	}
	
	public Node getNode() {
		return this.node;
	}
	
	public void setPoint(double x, double y) {
		this.point = new Point.Double(x, y);
	}

	public void setPoint(Point.Double newPoint) {
		this.point = newPoint;
	}

	public Point.Double getPoint() {
		return this.point;
	}

	public void setCircleRadius(double newRadius) {
		this.radius = newRadius;
	}

	public double getCircleRadius() {
		return this.radius;
	}

	public void displayNode(Graphics2D g2) {
		
		g2.setColor(CIRCLE_COLOR);
		
		Ellipse2D circle = new Ellipse2D.Double(this.point.x - this.radius, this.point.y - this.radius,
				this.radius * 2, this.radius * 2);
		g2.draw(circle);
		
		g2.setColor(TEXT_COLOR);

		String rank = this.node.rank + "";
		Rectangle2D bounds = g2.getFontMetrics().getStringBounds(rank, g2);
		int upperLeftX = (int) (this.point.x - bounds.getWidth() / 2);
		int upperLeftY = (int) (this.point.y - 1 * bounds.getHeight() / 3); 
		g2.drawString(rank, upperLeftX, upperLeftY);
		
		String balance;
		if (this.node.balance == null) {
			
			balance = "null";
		} else {
			balance = this.node.balance.toString();
		}
		bounds = g2.getFontMetrics().getStringBounds(balance, g2);
		upperLeftX = (int) (this.point.x - bounds.getWidth() / 2);
		upperLeftY = (int) (this.point.y + 1 * bounds.getHeight() / 4); 
		g2.drawString(balance, upperLeftX, upperLeftY);

		String text = String.valueOf(this.node.data);
		bounds = g2.getFontMetrics().getStringBounds(text, g2);
		upperLeftX = (int) (this.point.x - bounds.getWidth() / 2);
		upperLeftY = (int) (this.point.y + 5 * bounds.getHeight() / 6); 
		g2.drawString(text, upperLeftX, upperLeftY);

	}

}
