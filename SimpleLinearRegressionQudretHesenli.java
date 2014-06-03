import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class SimpleLinearRegressionQudretHesenli {
	
	public static double learnA1(double y[], double x[]) {
		
		double a = 0;
		for(int i = 0; i < 358; i++)
			a+= x[i]*y[i];
		a *= 358;
		
		double b = 0, c =0;
		for(int i = 0; i < 358; i++) {
			b += x[i];
			c += y[i];
		}
		
		double d = 0;
		for(int i = 0; i < 358; i++) {
			d += x[i]*x[i];
		}
		
		double e = 0;
		for(int i = 0; i < 358; i++) {
			e += x[i];
		}
		e *= e;
		
		return (a - b*c)/(d - e);
	}
	
	public static double learnA0(double y[], double x[], double a1) {
		
		double a = 0;
		for(int i = 0; i < 358; i++) {
			a += y[i];
		}
		
		double b = 0;
		for(int i = 0; i < 358; i++) {
			b += x[i];
		}
		return (a - a1*b)/358;
	}

	public static void main(String[] args) {

		// The data concerns city-cycle fuel consumption in miles per gallon(mpg)
		
		// Actually mpg depends on several variables, but I have simplified for myself
		// the task and I have done the simple linear regression where mpg depends on
		// other arguments separately

		double mpg[] = new double[358];
		double cylinders[] = new double[358];
		double displacement[] = new double[358];
		double horsePower[] = new double[358];
		double weight[] = new double[358];
		double acceleration[] = new double[358];
		double modelYear[] = new double[358];
		double origin[] = new double[358];
		
		double expectedMpg[] = new double[40];
		double testCylinders[] = new double[40];
		double testDisplacement[] = new double[40];
		double testHorsePower[] = new double[40];
		double testWeight[] = new double[40];
		double testAcceleration[] = new double[40];
		double testModelYear[] = new double[40];
		double testOrigin[] = new double[40];
		
		// 1)Reading the data set
		//   the first 358 samples I will use for learning, the last 40 - for testing
		
		try {
			
			//BufferedReader br = new BufferedReader(new FileReader("auto-mpg.data.txt"));
			BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Gudrat\\Desktop\\auto-mpg.data.txt"));

			String line;
			StringTokenizer st;
			//reading learning data
			for(int i = 0; i < 358; i++) {
				line = br.readLine();
				st = new StringTokenizer(line);
				
				mpg[i] = Double.parseDouble(st.nextToken());
				cylinders[i] = Double.parseDouble(st.nextToken());
				displacement[i] = Double.parseDouble(st.nextToken());
				horsePower[i] = Double.parseDouble(st.nextToken());
				weight[i] = Double.parseDouble(st.nextToken());
				acceleration[i] = Double.parseDouble(st.nextToken());
				modelYear[i] = Double.parseDouble(st.nextToken());
				origin[i] = Double.parseDouble(st.nextToken());
			}
			//reading test data
			for(int i = 358; i < 398; i++) {
				line = br.readLine();
				st = new StringTokenizer(line);
				
				expectedMpg[i-358] = Double.parseDouble(st.nextToken());
				testCylinders[i-358] = Double.parseDouble(st.nextToken());
				testDisplacement[i-358] = Double.parseDouble(st.nextToken());
				testHorsePower[i-358] = Double.parseDouble(st.nextToken());
				testWeight[i-358] = Double.parseDouble(st.nextToken());
				testAcceleration[i-358] = Double.parseDouble(st.nextToken());
				testModelYear[i-358] = Double.parseDouble(st.nextToken());
				testOrigin[i-358] = Double.parseDouble(st.nextToken());
			}
			br.close();
			
		} catch(IOException e) {
			e.printStackTrace();
		}

		// 2) Learning slopes from learning samples
		
		double A0[] = new double[7];
		double A1[] = new double[7];
		
		A1[0] = learnA1(mpg, cylinders);
		A0[0] = learnA0(mpg, cylinders, A1[0]);
		A1[1] = learnA1(mpg, displacement);
		A0[1] = learnA0(mpg, displacement, A1[1]);
		A1[2] = learnA1(mpg, horsePower);
		A0[2] = learnA0(mpg, horsePower, A1[2]);
		A1[3] = learnA1(mpg, weight);
		A0[3] = learnA0(mpg, weight, A1[3]);
		A1[4] = learnA1(mpg, acceleration);
		A0[4] = learnA0(mpg, acceleration, A1[4]);
		A1[5] = learnA1(mpg, modelYear);
		A0[5] = learnA0(mpg, modelYear, A1[5]);
		A1[6] = learnA1(mpg, origin);
		A0[6] = learnA0(mpg, origin, A1[6]);
		
		// 3) Calculating results from test samples
		
		double Y[][] = new double[40][7];
		for(int i = 0; i < 40; i++) {
			Y[i][0] = A0[0] + A1[0]*testCylinders[i];
			Y[i][1] = A0[1] + A1[1]*testDisplacement[i];
			Y[i][2] = A0[2] + A1[2]*testHorsePower[i];
			Y[i][3] = A0[3] + A1[3]*testWeight[i];
			Y[i][4] = A0[4] + A1[4]*testAcceleration[i];
			Y[i][5] = A0[5] + A1[5]*testModelYear[i];
			Y[i][6] = A0[6] + A1[6]*testOrigin[i];
		}
		
		// 4) Displaying the result
		
		for(int i = 0; i < 40; i++) {
			System.out.println("Test " + i + ":" );
			System.out.println("Expected result: " + expectedMpg[i]);
			System.out.println("According to cylniders: " + Y[i][0]);
			System.out.println("According to displacement: " + Y[i][1]);
			System.out.println("According to horse power: " + Y[i][2]);
			System.out.println("According to weight: " + Y[i][3]);
			System.out.println("According to acceleration: " + Y[i][4]);
			System.out.println("According to model year: " + Y[i][5]);
			System.out.println("According to origin: " + Y[i][6]);
			
		}
	}

}
