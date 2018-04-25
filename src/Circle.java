
public class Circle {
	public Point pos;
	public float r;

	public Circle(float x, float y, float r) {
		this.pos = new Point(x, y);
		this.r = r;
	}
	
	public boolean contains(Point targetPos) {
		return processing.core.PApplet.dist(pos.x, pos.y, targetPos.x, targetPos.y) <= r;
	}
	
	public boolean intersects(Rectangle range) {
		float hSquared = range.height * range.height;
		float wSquared = range.width * range.width;
		float rectRadius = (float)Math.sqrt(hSquared + wSquared); // 矩形的外接圓半徑
		return processing.core.PApplet.dist(pos.x, pos.y, range.pos.x, range.pos.y) <= r + rectRadius;
	}
	
	public boolean intersects(Circle range) {
		return processing.core.PApplet.dist(pos.x, pos.y, range.pos.x, range.pos.y) <= r + range.r;
	}
}
