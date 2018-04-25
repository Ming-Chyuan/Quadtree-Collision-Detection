import java.util.ArrayList;

public class Quadtree {
	private static final int capacity = 4;
	
	private Rectangle boundary;
	private ArrayList<Ball> balls;

	private boolean divided;
	private Quadtree northWest;
	private Quadtree northEast;
	private Quadtree southWest;
	private Quadtree southEast;
	
	private Sketch sketch;
	
	public static int size = 0;
	
	public Quadtree(Sketch sketch, Rectangle boundary) {
		this.sketch = sketch;
		this.boundary = boundary;
		balls = new ArrayList<>();
		divided = false;
	}
	
	public boolean insert(Ball b) {
		if(!boundary.contains(b.pos)) { // the point is not inside the region
			return false;
		}
		if(balls.size() < capacity) {
			balls.add(b);
			size++;
			return true;
		} else {
			if(!divided) {
				subdivide();
			}

			if(northWest.insert(b)) {
				return true;
			} else if(northEast.insert(b)) {
				return true;
			} else if(southWest.insert(b)) {
				return true;
			} else {
				return southEast.insert(b);
			}
		}
	}
	
	private void subdivide() {
		float x = boundary.pos.x;
		float y = boundary.pos.y;
		float w = boundary.width;
		float h = boundary.height;
		
		Rectangle nwBoundary = new Rectangle(x - w / 2, y - h / 2, w / 2, h / 2);
		northWest = new Quadtree(sketch, nwBoundary);
		Rectangle neBoundary = new Rectangle(x + w / 2, y - h / 2, w / 2, h / 2);
		northEast = new Quadtree(sketch, neBoundary);
		Rectangle swBoundary = new Rectangle(x - w / 2, y + h / 2, w / 2, h / 2);
		southWest = new Quadtree(sketch, swBoundary);
		Rectangle seBoundary = new Rectangle(x + w / 2, y + h / 2, w / 2, h / 2);
		southEast = new Quadtree(sketch, seBoundary);

		divided = true;
	}
	
	public ArrayList<Ball> queryRange(Circle range) {
		ArrayList<Ball> findings = new ArrayList<>();
		
		if(range.intersects(boundary)){
			for(Ball b : balls) {
				if(range.contains(b.pos)) {
					findings.add(b);
				}
			}
			
			if(divided) {
				findings.addAll(northWest.queryRange(range));
				findings.addAll(northEast.queryRange(range));
				findings.addAll(southWest.queryRange(range));
				findings.addAll(southEast.queryRange(range));
			}
		}
		return findings;
	}
	
	public void show() {
		sketch.stroke(0, 150, 0, 50);
		sketch.strokeWeight(1);
		sketch.noFill();
		sketch.rect(boundary.pos.x, boundary.pos.y, boundary.width * 2, boundary.height * 2);
		if(divided) {
			northWest.show();
			northEast.show();
			southWest.show();
			southEast.show();
		}
	}
}
