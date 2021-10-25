package areacaluculator;

public class Length extends Points {
	Length() {
		super();		
	}

	protected int[] lineMidPointsX = {0,0,0,0};
	protected int[] lineMidPointsY = {0,0,0,0};
	protected double[] lengths = {0,0,0,0};
	protected double diagonal1;
	protected double diagonal2;

	protected void calculateLengths() {
		double temp;
		double tempX, tempY;

		for (int i = 0; i < 3; i++) {
			temp = Math.sqrt((xPoints[i + 1] - xPoints[i]) * (xPoints[i + 1] - xPoints[i])
					+ (yPoints[i + 1] - yPoints[i]) * (yPoints[i + 1] - yPoints[i]));
			temp = ((double) Math.round(temp * 100)) / 100.0;
			lengths[i] = temp;

			tempX = (xPoints[i + 1] + xPoints[i]) / 2.0d;
			tempY = (yPoints[i + 1] + yPoints[i]) / 2.0d;

			lineMidPointsX[i] = (int) tempX;
			lineMidPointsY[i] = (int) tempY;

		}
		temp = Math.sqrt((xPoints[0] - xPoints[3]) * (xPoints[0] - xPoints[3])
				+ (yPoints[0] - yPoints[3]) * (yPoints[0] - yPoints[3]));
		temp = ((double) Math.round(temp * 100)) / 100.0;
		lengths[3] = temp;

		tempX = (xPoints[0] + xPoints[3]) / 2.0d;
		tempY = (yPoints[0] + yPoints[3]) / 2.0d;

		lineMidPointsX[3] = (int) tempX;
		lineMidPointsY[3] = (int) tempY;
		diagonal1 = Math.sqrt((xPoints[1] - xPoints[3]) * (xPoints[1] - xPoints[3])
				+ (yPoints[1] - yPoints[3]) * (yPoints[1] - yPoints[3]));
		diagonal2 = Math.sqrt((xPoints[0] - xPoints[2]) * (xPoints[0] - xPoints[2])
				+ (yPoints[0] - yPoints[2]) * (yPoints[0] - yPoints[2]));

	}

	public double getLength(int i) {
		return lengths[i];
	}

	public double getD1() {
		return diagonal1;
	}

	public double getXMid(int i) {
		return lineMidPointsX[i];
	}

	public double getYMid(int i) {
		return lineMidPointsY[i];
	}
}
