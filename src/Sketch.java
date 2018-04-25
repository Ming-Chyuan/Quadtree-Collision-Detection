import java.util.ArrayList;

import processing.core.PApplet;

public class Sketch extends PApplet {
	
	public int canvasWidth;
	public int canvasHeight;
	public int infoHeight;
	
	private int iniNum;
	private int minR;
	private int maxR;
	private int collisions;
	private int infoTextSize;
	private boolean useQaudtree;
	private boolean showSearchingRange;
	private Rectangle boundary;
	private ArrayList<Ball> balls;
	
	public static void main(String[] args) {
		PApplet.main("Sketch");
	}
	
	public void settings() {
		iniNum = 5;
		minR = 3;
		maxR = 10;
		canvasWidth = 1200;
		canvasHeight = 800;
		infoHeight = 100;
		infoTextSize = 14;
		useQaudtree = true;
		showSearchingRange = true;
		size(canvasWidth, canvasHeight + infoHeight);
	}
	
	public void setup() {
		background(0);
		rectMode(CENTER);
		ellipseMode(RADIUS);
		textSize(infoTextSize);
		surface.setTitle("Collision Detection");
		boundary = new Rectangle(width / 2, (height - infoHeight) / 2, canvasWidth / 2, canvasHeight / 2);

		balls = new ArrayList<>();
		while(balls.size() < iniNum) {
			addParticle(random(canvasWidth), random(canvasHeight), minR + random(maxR - minR + 1));
		}
	}
	
	public void draw() {		
		background(0);
		collisions = 0;

		Quadtree quadtree = new Quadtree(this, boundary);

		for(Ball b : balls) {
			if(useQaudtree) quadtree.insert(b);
			b.move();
			b.show();
		}

		for(Ball b : balls) {
			b.setHighlight(false);
			Circle range = new Circle(b.pos.x, b.pos.y, b.r + maxR);
			ArrayList<Ball> findings = quadtree.queryRange(range);
			for(Ball other : useQaudtree ? findings : balls) { // findings: n log n, balls: n^2
				if(showSearchingRange) {
					if(useQaudtree) {
						showSearchingRange(range);
					} else {
						showSearchingRange(b, other);
					}
				}
				if(b != other && b.intersects(other)) {
					b.setHighlight(true);
					collisions++;
				}
			}
		}

		quadtree.show();
		displayInfo();
		
		if(mousePressed && mouseButton == LEFT)
			addParticle(mouseX, mouseY, minR + random(maxR - minR + 1));
	}
	
	private void addParticle(float x, float y, float r) {
		Ball b = new Ball(this, x, y, r);
		if(b.outOfBounds()) return;
		if(balls.size() > 0) {
			for(Ball other : balls) {
				if(b.intersects(other)) {
					return;
				}
			}
		}
		balls.add(b);
	}
	
	public void mouseClicked() {
		if(mouseButton == RIGHT) useQaudtree = !useQaudtree;
	}
	
	public void keyTyped() {
		if(key == 'r') setup();
		if(key == 's') showSearchingRange = !showSearchingRange;
	}
	
	public void showSearchingRange(Circle range) {
		stroke(255, 110, 255, 50);
		ellipse(range.pos.x, range.pos.y, range.r, range.r);
	}
	
	public void showSearchingRange(Ball b, Ball other) {
		stroke(255, 110, 255, 50);
		line(b.pos.x, b.pos.y, other.pos.x, other.pos.y);
	}
	
	private void displayInfo() {
		stroke(255);
		line(0, canvasHeight, canvasWidth, canvasHeight);
		
		int margin = infoTextSize + 4;
		int w = 10;
		int h = canvasHeight + margin;
		
		fill(255, 255, 0);
		text("FPS: " + (int)frameRate, w, h += margin);
		text("Balls: " + balls.size(), w, h += margin);
		text("Collisions: " + collisions / 2, w, h += margin);
		
		w += 300;
		h = canvasHeight + margin;
		text("Key", w, h += margin);
		text("R: Reset", w, h += margin);
		text("S: Show(Hide) Searching Range", w, h += margin);
		
		w += 300;
		h = canvasHeight + margin;
		text("Mouse", w, h += margin);
		text("Left Button: Add Ball", w, h += margin);
		text("Right Button: Enable(Disable) Quadtree", w, h += margin);
	}
}
