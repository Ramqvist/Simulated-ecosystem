package chalmers.dax021308.ecosystem.model.util;

/**
 * 
 * TODO: Add more documentation.
 * 
 * @author Sebbe
 * 
 */
public class Vector {

	// Make these public?! No
	public double x;

	public double y;

	/**
	 * Creates a 2-Dimensional vector with start values (0,0).
	 */
	public Vector() {
		x = 0;
		y = 0;
	}

	/**
	 * Creates a 2-Dimensional vector.
	 * 
	 * @param x
	 *            , start value for x-space
	 * @param y
	 *            , start value for y-space.
	 */
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates a 2-Dimensional vector as the difference between two positions.
	 * 
	 * @param p1
	 * @param p2
	 */
	public Vector(Position p1, Position p2) {
		this.x = p1.getX() - p2.getX();
		this.y = p1.getY() - p2.getY();
	}

	/**
	 * Create a new Vector with the values of the parameter one.
	 */
	public Vector(Vector v) {
		this.x = v.x;
		this.y = v.y;
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
	 * 
	 * @param v
	 *            the vector with the new values.
	 */
	public void setVector(Vector v) {
		this.x = v.x;
		this.y = v.y;
	}

	/**
	 * Sets the vector to given x and y.
	 * 
	 * @param x
	 * @param y
	 */
	public void setVector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Adds another vector to this.
	 * 
	 * @param v
	 *            vector to add.
	 */
	public Vector add(Vector v) {
		this.x = this.x + v.x;
		this.y = this.y + v.y;
		return this;
	}

	/**
	 * 
	 * @param d
	 *            the double to multiply with
	 */
	public Vector multiply(double d) {
		this.x = this.x * d;
		this.y = this.y * d;
		return this;
	}

	/**
	 * TODO: Documentation needed. What is this?
	 * 
	 * @return the norm of the vector.
	 */
	public double getNorm() {
		return Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2));
	}

	/**
	 * TODO: Documentation needed. What is this?
	 */
	public Vector toUnitVector() {
		double length = this.getNorm();
		this.x = this.x / length;
		this.y = this.y / length;
		return this;
	}

	/**
	 * Rotates the Vector.
	 * 
	 * @param angle
	 *            - The rotation angle in radians.
	 * @return The rotated Vector.
	 */
	public Vector rotate(double angle) {
		double tempX = (this.x * Math.cos(angle)) - (this.y * Math.sin(angle));
		double tempY = (this.x * Math.sin(angle)) + (this.y * Math.cos(angle));
		this.x = tempX;
		this.y = tempY;
		return this;
	}

	/**
	 * Adds two vectors and returns a new vector.
	 * 
	 * @param v1
	 * @param v2
	 * @return v1 + v2
	 */
	public static Vector addVectors(Vector v1, Vector v2) {
		return new Vector(v1.x + v2.x, v1.y + v2.y);
	}

	@Override
	public String toString() {
		return "(" + Stat.roundNDecimals(this.x,3) + "," + Stat.roundNDecimals(this.y,3) + ")";
	}

	/**
	 * Returns a new empty vector.
	 * 
	 * @return
	 */
	public static Vector emptyVector() {
		return new Vector(0, 0);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		} else if (getClass() != o.getClass()) {
			return false;
		}

		Vector v = (Vector) o;
		return Double.doubleToLongBits(this.x) == Double.doubleToLongBits(v
				.getX())
				&& Double.doubleToLongBits(this.y) == Double.doubleToLongBits(v
						.getY());
	}

	/**
	 * Checks if the vector is a null vector, i.e. if x == 0 and y == 0. Not to
	 * be confused with the case of the vector object itself being null.
	 * 
	 * Rename to isEmpty()?
	 * 
	 * @return Returns true if x == 0 and y == 0, otherwise false
	 */
	public boolean isNullVector() {
		return x == 0 && y == 0;
	}
}
