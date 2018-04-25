
public class Rectangle {
	/*
	 * 		 ----------------
	 * 		|        |h      |
	 * 		|--------.       |
	 * 		|   w    ↑(x, y) |
	 * 		 ----------------
	 */
	
	// center of Rectangle
	public Point pos;
	
	// the distance between center to left(top) border or right(bottom) border
	public float width;
	public float height;
	
	public Rectangle(float x, float y, float w, float h) {
		this.pos = new Point(x, y);
		width = w;
		height = h;
	}
	
	public boolean contains(Point targetPos) {
		return pos.x - width <= targetPos.x && targetPos.x <= pos.x + width &&
				pos.y - height <= targetPos.y && targetPos.y <= pos.y + height;
	}
}
