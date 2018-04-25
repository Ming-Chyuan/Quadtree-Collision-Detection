
public class Ball {
	public float r;
	public Point pos;
	public Point velocity;
	
	private Sketch sketch;
	private boolean highlight;
	
	public Ball(Sketch sketch, float x, float y, float r) {
		this.sketch = sketch;
		this.r = r;
		pos = new Point(x, y);
		velocity = new Point(
				(sketch.random(3) + 1) * (float)Math.pow(-1, (int)sketch.random(2)),
				(sketch.random(3) + 1) * (float)Math.pow(-1, (int)sketch.random(2))
			);
		highlight = false;
	}
	
	public void move() {
		if(xOutOfBounds()) {
			velocity.x *= -1;
		}
		if(yOutOfBounds()) {
			velocity.y *= -1;
		}
		pos.x += velocity.x;
		pos.y += velocity.y;
	}

	public boolean outOfBounds() {
		return xOutOfBounds() || yOutOfBounds();
	}
	
	public boolean xOutOfBounds() {
		return !(0 + r <= pos.x && pos.x <= sketch.canvasWidth - r);
	}
	
	public boolean yOutOfBounds() {
		return !(0 + r <= pos.y && pos.y <= sketch.canvasHeight - r);
	}
	
	public void show() {
		sketch.noFill();
		if(highlight) {
			sketch.stroke(255, 0, 0);
		} else {
			sketch.stroke(255);
		}
		sketch.ellipse(pos.x, pos.y, r, r);
	}
	
	public boolean intersects(Ball other) {
		float d = processing.core.PApplet.dist(pos.x, pos.y, other.pos.x, other.pos.y);
		return d < r + other.r;
	}
	
	public void setHighlight(boolean b) {
		highlight = b;
	}
}
