package chalmers.dax021308.ecosystem.model.util;

/**
 * 
 * @author Sebbe
 *
 */
public class Vector {

	private double x;

	private double y;
	
	/**
	 * Creates a 2-Dimensional vector with start values (0,0).
	 */
	public Vector(){
		x = 0;
		y = 0;
	}
	
	/**
	 * Creates a 2-Dimensional vector.
	 * @param x, start value for x-space
	 * @param y, start value for y-space.
	 */
	public Vector(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Creates a 2-Dimensional vector as the difference between two positions.
	 * @param p1
	 * @param p2
	 */
	public Vector(Position p1, Position p2){
		this.x = p1.getX()-p2.getX();
		this.y = p1.getY()-p2.getY();
	}
	
	public Vector(Vector v){
		this.x = v.getX();
		this.y = v.getY();
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Sets this vector to the values of another vector
	 * @param v the vector with the new values.
	 */
	public void setVector(Vector v){
		this.x = v.getX();
		this.y = v.getY();
	}
	
	/**
	 * Sets the vector to given x and y.
	 * @param x
	 * @param y
	 */
	public void setVector(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Adds another vector to this.
	 * @param v vector to add.
	 */
	public Vector add(Vector v){
		this.x = this.x + v.getX();
		this.y = this.y + v.getY();
		return this;
	}
	
	/**
	 * 
	 * @param d the double to multiply with
	 */
	public Vector multiply(double d){
		this.x = this.x*d;
		this.y = this.y*d;
		return this;
	}

	/**
	 * 
	 * @return the norm of the vector.
	 */
	public double getNorm(){
		return Math.sqrt(Math.pow(this.getX(),2)+Math.pow(this.getY(),2));
	}

	public Vector scaleVector(double scalar) {
		return null;
	}
	
	public Vector toUnitVector() {
		double length = this.getNorm();
		this.x = this.x / length;
		this.y = this.y / length;
		return this;
	}
	
	@Override
	public String toString(){
		return "("+this.x+","+this.y+")";
	}
}
