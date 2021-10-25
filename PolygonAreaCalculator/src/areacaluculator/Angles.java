package areacaluculator;

public class Angles extends Length {
	double angles[];
	boolean[] crossProducts;

	public Angles() {
		super();
		this.crossProducts = new boolean[] { true, true, true, true };
		this.angles = new double[] { 90, 90, 90, 90 };
		

	}

	private void calculateAngles() {

		double a0, a1, a2, a3;
		int trueFrequency = 0;
		int falseFrequency = 0;

		a0 = Math.acos(((lengths[0] * lengths[0]) + (lengths[3] * lengths[3]) - (diagonal1 * diagonal1))
				/ (2 * lengths[0] * lengths[3]));
		a0 = (a0 / Math.PI) * 180;
		a0 = ((double) Math.round(a0 * 100)) / 100.0;

		a1 = Math.acos(((lengths[0] * lengths[0]) + (lengths[1] * lengths[1]) - (diagonal2 * diagonal2))
				/ (2 * lengths[0] * lengths[1]));
		a1 = (a1 / Math.PI) * 180;
		a1 = ((double) Math.round(a1 * 100)) / 100.0;

		a2 = Math.acos(((lengths[1] * lengths[1]) + (lengths[2] * lengths[2]) - (diagonal1 * diagonal1))
				/ (2 * lengths[2] * lengths[1]));
		a2 = (a2 / Math.PI) * 180;
		a2 = ((double) Math.round(a2 * 100)) / 100.0;

		a3 = Math.acos(((lengths[3] * lengths[3]) + (lengths[2] * lengths[2]) - (diagonal2 * diagonal2))
				/ (2 * lengths[3] * lengths[2]));
		a3 = (a3 / Math.PI) * 180;
		a3 = ((double) Math.round(a3 * 100)) / 100.0;

		angles[0] = a0;
		angles[1] = a1;
		angles[2] = a2;
		angles[3] = a3;

		// the following code will check whether the polygon is concave or not and it
		// will re-adjust the angle values
		// crossProduct[i] is equal to true if the scalar value of cross products of
		// origin vectors of lines forming angle[i] is positive and false if its
		// negative
		// for a concave polygon, the crossProducts[] value will be either {true, true,
		// false, true} or {false, false, true, false}
		// the index of the odd value, in above case, its [3], will be the index of the
		// concave angle
		// hence we re-adjust the value of that angle: e.g. angle[3] = 360 - angle[3];
		for (int i = 1; i < 3; i++) {
			crossProducts[i] = ((xPoints[i + 1] - xPoints[i])
					* (yPoints[i] - yPoints[i - 1])) > ((xPoints[i] - xPoints[i - 1]) * (yPoints[i + 1] - yPoints[i]));
		}
		crossProducts[0] = ((xPoints[1] - xPoints[0]) * (yPoints[0] - yPoints[3])) > ((xPoints[0] - xPoints[3])
				* (yPoints[1] - yPoints[0]));
		crossProducts[3] = ((xPoints[0] - xPoints[3]) * (yPoints[3] - yPoints[2])) > ((xPoints[3] - xPoints[2])
				* (yPoints[0] - yPoints[3]));
		for (int i = 0; i < 4; i++)
			if (crossProducts[i] == true)
				trueFrequency++;
			else
				falseFrequency++;
		if (falseFrequency > trueFrequency)
			for (int i = 0; i < 4; i++)
				crossProducts[i] = !crossProducts[i];
		for (int i = 0; i < 4; i++)
			if (crossProducts[i] == false)
				angles[i] = 360 - angles[i];

	}

	public double getAngle(int i) {
		return angles[i];
	}

	public boolean getCrossProduct(int i) {
		return crossProducts[i];
	}

	public void changePoint(int x, int y, int i) {
		xPoints[i] = x;
		yPoints[i] = y;
		calculateArea();
		calculateLengths();
		calculateAngles();
	}
	
	public void changePoint() {
		calculateArea();
		calculateLengths();
		calculateAngles();
	}
}
