import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;

public class disease {

	public static boolean checkPerfectSquare(int num) {
		double squareRoot = Math.sqrt(num);
		return ((squareRoot - Math.floor(squareRoot)) == 0); 
	} 
	
	public static double getRandomNum(double min, double max) {
		return (Math.random() * (max - min)) + min;
	} 
	
	public static String getInfected(int numOfNeighbor, double infectRate) {
		String status = "";
		double randomValue;
		double cumulativeInfectRate = infectRate * numOfNeighbor;
		if (cumulativeInfectRate > 1)
			cumulativeInfectRate = 1;
		randomValue = getRandomNum(0,1);
		if (cumulativeInfectRate >= randomValue)
			status = "I";
		else
			status = "S";
		return status;
	}
	
	public static void createFile(int population, String fileName) {
		try {
            FileWriter writer = new FileWriter(fileName + ".txt", false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            
            for (int i = 1; i <= population; i++) {
            	if (i == 1) {
            		bufferedWriter.write("I");
            	}
            	else {
            		bufferedWriter.write("S");
            	}
            	bufferedWriter.newLine();
            } // this loop populates all the individuals. the first individual will be assigned as infected  
            
            bufferedWriter.close();
        } 
		catch (IOException e) {
            e.printStackTrace();
        }
	} 
	
	public static String readFile(int population, String fileName) {
		String result = "";
		int squareCheck = 1;
		try {
            FileReader reader = new FileReader(fileName + ".txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            
            for (int i = 1; i <= population; i++) {
            	if (i == squareCheck * Math.sqrt(population)) {
            		result += bufferedReader.readLine() + "\n";
            		squareCheck++;
            	} // finds the last individual in a row and creates a new line
            	else {
            		result += bufferedReader.readLine() + " ";
            	}
            }
            
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
	} 
	
	public static String readLine(int line, String fileName) {
		String result = "";
		try {
            FileReader reader = new FileReader(fileName + ".txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            
            for (int i = 1; i <= line; i++) {
            	result = bufferedReader.readLine();
            }
            
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
		return result;
	} 
	
	public static void alterFile(int population, String fileName, double infectRate, double recoverRate, String previousFileName) {
		int neighbor;
		try {
            FileWriter writer = new FileWriter(fileName + ".txt", false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            
            for (int i = 1; i <= population; i++) {
            	if (readLine(i,previousFileName).contains("R")) {
            		bufferedWriter.write("R");
            	}
            	else if (readLine(i,previousFileName).contains("I")) {
            		if (recoverRate == 0)
            			bufferedWriter.write("I");
            		else if (recoverRate >= getRandomNum(0, 1))
            			bufferedWriter.write("R");
            		else
            			bufferedWriter.write("I");
            	}
            	else if (readLine(i,previousFileName).contains("S")) {
            		if (i < Math.sqrt(population) && i != 1 && i != Math.sqrt(population)) {
            			neighbor = 0;
            			if (readLine(i - 1,previousFileName).contains("I"))
                			neighbor += 1;
            			if (readLine(i + 1,previousFileName).contains("I"))
                			neighbor += 1;
            			if (readLine((int) (i + Math.sqrt(population)),previousFileName).contains("I"))
                			neighbor += 1;
                		bufferedWriter.write(getInfected(neighbor,infectRate));
                	} // top row
            		else if (i == Math.sqrt(population)) {
            			neighbor = 0;
            			if (readLine(i - 1,previousFileName).contains("I"))
                			neighbor += 1;
                		if (readLine((int) (i + Math.sqrt(population)),previousFileName).contains("I"))
                			neighbor += 1;
                		bufferedWriter.write(getInfected(neighbor,infectRate));
                	} // corner: top right
            		else if (i == (population - Math.sqrt(population) + 1)) {
            			neighbor = 0;
            			if (readLine(i + 1,previousFileName).contains("I"))
                			neighbor += 1;
                		if (readLine((int) (i - Math.sqrt(population)),previousFileName).contains("I"))
                			neighbor += 1;
                		bufferedWriter.write(getInfected(neighbor,infectRate));
                	} // corner: bottom left
            		else if ((i-1) % Math.sqrt(population) == 0 && i != 1 && i != (population - Math.sqrt(population) + 1)) {
            			neighbor = 0;
            			if (readLine(i + 1,previousFileName).contains("I"))
                			neighbor += 1;
                		if (readLine((int) (i + Math.sqrt(population)),previousFileName).contains("I"))
                			neighbor += 1;
                		if (readLine((int) (i - Math.sqrt(population)),previousFileName).contains("I"))
                			neighbor += 1;
                		bufferedWriter.write(getInfected(neighbor,infectRate));
                	} // left column
            		else if (i == population) {
            			neighbor = 0;
            			if (readLine(i-1,previousFileName).contains("I"))
                			neighbor += 1;
                		if (readLine((int) (i - Math.sqrt(population)),previousFileName).contains("I"))
                			neighbor += 1;
                		bufferedWriter.write(getInfected(neighbor,infectRate));
                	} // corner: bottom right
            		else if (i % Math.sqrt(population) == 0 && i != Math.sqrt(population) && i != population) {
            			neighbor = 0;
            			if (readLine(i-1,previousFileName).contains("I"))
                			neighbor += 1;
                		if (readLine((int) (i + Math.sqrt(population)),previousFileName).contains("I"))
                			neighbor += 1;
                		if (readLine((int) (i - Math.sqrt(population)),previousFileName).contains("I"))
                			neighbor += 1;
                		bufferedWriter.write(getInfected(neighbor,infectRate));
                	} // right column
            		else if (i > (population - Math.sqrt(population) + 1) && i != (population - Math.sqrt(population) + 1) && i != population) {
            			neighbor = 0;
            			if (readLine(i-1,previousFileName).contains("I"))
                			neighbor += 1;
                		if (readLine(i+1,previousFileName).contains("I"))
                			neighbor += 1;
                		if (readLine((int) (i - Math.sqrt(population)),previousFileName).contains("I"))
                			neighbor += 1;
                		bufferedWriter.write(getInfected(neighbor,infectRate));
                	} // bottom row
            		else {
            			neighbor = 0;
            			if (readLine(i-1,previousFileName).contains("I"))
                			neighbor += 1;
            			if (readLine(i+1,previousFileName).contains("I"))
                			neighbor += 1;
            			if (readLine((int) (i + Math.sqrt(population)),previousFileName).contains("I"))
                			neighbor += 1;
            			if (readLine((int) (i - Math.sqrt(population)),previousFileName).contains("I"))
                			neighbor += 1;
            			bufferedWriter.write(getInfected(neighbor,infectRate));
            		}
            	}
            	bufferedWriter.newLine();
            } 
            
            bufferedWriter.close();
        } 
		catch (IOException e) {
            e.printStackTrace();
        }
	} 
	
	public static void main(String[] args) {
		
		Scanner scnr = new Scanner(System.in);
		
		int population = 0;
		int step = 0;
		double infectRate = 0;
		double recoverRate = 0;
		
		String fileName = "data";
		String grid = "";
		
		boolean populationCheck = false;
		boolean infectCheck = false;
		boolean recoverCheck = false;
		boolean stepCheck = false;
		
		/* STAGE ONE: 
		 * asks the user for inputs
		 * creates a text file from given data
		 * prints the content of the text file
		 */
		
		while (populationCheck == false) {				
			System.out.print("Enter the population size: ");			
			try {
				population = Integer.parseInt(scnr.next());
				if (checkPerfectSquare(population) == true && population != 0)
					populationCheck = true;
				else if (population == 0)
					System.out.println("Population size cannot be 0. Please try again.");
				else 
					System.out.println("That is not a perfect square. Please try again.");	
			}
			catch (NumberFormatException ex) {
				System.out.println("That is not an integer nor a perfect square. Please try again.");	
			}			
		} // this loop checks if input for population is a perfect square
		
		while (infectCheck == false) {
			System.out.print("Enter the infection rate: ");
			try {
				infectRate = Double.parseDouble(scnr.next());
				if (infectRate >= 0 && infectRate <= 1) 
					infectCheck = true;
				else
					System.out.println("Infection rate must be between 0 and 1 inclusive. Please try again.");
			}
			catch (NumberFormatException ex) {
				System.out.println("That is not a number between 0 and 1 inclusive. Please try again.");
			}
		} // this loop checks if input for infection rate is a number between 0 and 1 inclusive
		
		while (recoverCheck == false) {
			System.out.print("Enter the recovery rate: ");
			try {
				recoverRate = Double.parseDouble(scnr.next());
				if (recoverRate >= 0 && recoverRate <= 1) 
					recoverCheck = true;
				else
					System.out.println("Recovery rate must be between 0 and 1 inclusive. Please try again.");
			}
			catch (NumberFormatException ex) {
				System.out.println("That is not a number between 0 and 1 inclusive. Please try again.");
			}
		} // this loop checks if input for recovery rate is a number between 0 and 1 inclusive
		
		while (stepCheck == false) {				
			System.out.print("Enter the number of steps: ");			
			try {
				step = Integer.parseInt(scnr.next());
				if (step >= 1)
					stepCheck = true;
				else 
					System.out.println("Number of steps cannot be smaller than 1. Please try again.");	
			}
			catch (NumberFormatException ex) {
				System.out.println("That is not an integer. Please try again.");	
			}			
		} // this loop checks if input for population is a perfect square
		
		scnr.close();
		
		System.out.println();
		System.out.println("PHASE 1:");
		createFile(population, fileName + "1");
		grid = readFile(population, fileName + "1");
		System.out.print(grid);
		
		/* STAGE TWO:
		 * 
		 */
		
		int phase = 2;
		int previousPhase = 1;
		int iCount = 0;
		int rCount = 0;
		
		while (phase <= step) {
			System.out.println();
			System.out.println("PHASE " + phase + ":");
			alterFile(population, fileName + phase, infectRate, recoverRate, fileName + previousPhase);
			grid = readFile (population, fileName + phase);
			System.out.print(grid);				
			for (int i = 0; i < grid.length(); i++) {
				if (grid.charAt(i) == 'I')
					iCount++;
				if (grid.charAt(i) == 'R')
					rCount++;
			}
			System.out.println();
			System.out.println("- Infected: " + iCount);
			System.out.println("- Recovered: " + rCount);
			System.out.printf("- Infected/Total: %.2f", iCount/(double)population);
			System.out.println();
			iCount = 0;
			rCount = 0;
			phase++;
			previousPhase++;
		} 
		System.out.println("Hi");

	}

}
