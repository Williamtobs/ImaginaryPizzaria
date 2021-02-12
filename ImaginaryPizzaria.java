import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

//file location C:\Users\HP\Downloads\a_example
// C:\Users\HP\Downloads\b_little_bit_of_everything.in
// C:\Users\HP\Downloads\e_many_teams.in
// C:\Users\HP\Downloads\c_many_ingredients.in
// C:\Users\HP\Downloads\d_many_pizzas.in

public class ImaginaryPizzaria {
	public static void main(String[] args) throws IOException {
		double pizzaScore = 0;
		int teamNumber;
		Scanner in = new Scanner(System.in);
		File file = new File(in.next());
		FileReader read = new FileReader(file);
		BufferedReader myObj = new BufferedReader(read);
		String saveLocation = "C:\\Users\\HP\\Downloads\\e_many_teams.out";
		FileWriter location = new FileWriter(saveLocation);
		BufferedWriter writer = new BufferedWriter(location);
		Map<Integer, ArrayList<String>> listMap = new TreeMap<Integer, ArrayList<String>>();
		
		writer.write("first ");
		
		String firstLine = firstLine(file);
		int pizzaSize = noOfPizza(firstLine);
		    
	    System.out.print(pizzaSize+ " pizzas, ");
	    System.out.print(teamOfTwo(firstLine)+" team(s) of 2, ");
	    System.out.print(teamOfThree(firstLine)+" team(s) of 3, ");
	    System.out.print(teamOfFour(firstLine)+" team(s) of 4.");
	    System.out.println();
	    System.out.println();
		teamNumber = 0;
		int TotalTeam = teamOfTwo(firstLine) + teamOfThree(firstLine) + teamOfFour(firstLine);
		
		listMap = remainingLine(myObj, listMap, noOfPizza(firstLine));		
		listMap.forEach((key, value) -> System.out.println(key + " : " + value));
		
		while(pizzaSize > 1) {
			if(noOfPizza(firstLine) > 0 && listMap.size() > 0 && TotalTeam > 0) {
				pizzaScore +=  TeamPizza2(pizzaSize, teamOfTwo(firstLine), listMap, writer);
				teamNumber++;
				TotalTeam--;
			}
			if(noOfPizza(firstLine) > 0 && listMap.size() > 0 && TotalTeam > 0) {
				pizzaScore += TeamPizza3(pizzaSize, teamOfThree(firstLine), listMap, writer);
				teamNumber++;
				TotalTeam--;
			}
			if(noOfPizza(firstLine) > 0 && listMap.size() > 0 && TotalTeam > 0) {
				pizzaScore += TeamPizza4(pizzaSize, teamOfFour(firstLine), listMap, writer);
				teamNumber++;
				TotalTeam--;
			}
			else {
				break;
			}
		}
		
		writer.flush();
		replaceLines(saveLocation, String.valueOf(teamNumber));
		System.out.println(TotalTeam);
		
		System.out.println("Total Delivery Score is "+pizzaScore);
		
		
	}
	
	public static String firstLine(File file) {
		String firstLine = null;
		try {
			Scanner fileRead = new Scanner(file);
			for (int i = 0; i < 1; i++) {
				firstLine = fileRead.nextLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Line not found");
		}
		return firstLine;
		
	}
	
	public static Map<Integer, ArrayList<String>> remainingLine(BufferedReader file, Map<Integer, ArrayList<String>> listMap, int pizzaSize) {
		String Lines = "";
		String[] element;
		List<String> fixedList = null;
		ArrayList<String> ingredientsList = null;
		try {
			BufferedReader readLines = new BufferedReader(file);
			readLines.readLine();
			
			for (int i = 0; i < pizzaSize; i++) {
				Lines=readLines.readLine();
				element = Lines.split(" ");
				fixedList = Arrays.asList(element);
				ingredientsList = new ArrayList<String>(fixedList);
				ingredientsList.remove(0);
				listMap.put(i, ingredientsList);
			}
		}
		catch(IOException e) {
			System.out.println("Line not found");
		}
		
		
		return listMap;
	}
	
	
	public static int noOfPizza(String line) {
		int noOfPizza = 0;
		String[] lines = line.split("\\s+");
		while(!line.isEmpty()) {
			noOfPizza = Integer.parseInt(lines[0]);
			break;
		}
		return noOfPizza;
	}
	
	public static int teamOfTwo(String line) {
		int noOfTeam = 0;
		String[] lines = line.split("\\s+");
		while(!line.isEmpty()) {
			noOfTeam = Integer.parseInt(lines[1]);
			break;
		}
		return noOfTeam;
	}
	
	public static int teamOfThree(String line) {
		int noOfTeam = 0;
		String[] lines = line.split("\\s+");
		while(!line.isEmpty()) {
			noOfTeam = Integer.parseInt(lines[2]);
			break;
		}
		return noOfTeam;
	}
	
	public static int teamOfFour(String line) {
		int noOfTeam = 0;
		String[] lines = line.split("\\s+");
		while(!line.isEmpty()) {
			noOfTeam = Integer.parseInt(lines[3]);
			break;
		}
		return noOfTeam;
	}
	
	public static double TeamPizza2(int noOfPizza, int teamOfTwo, 
			Map<Integer, ArrayList<String>> listMap, BufferedWriter write) {
		HashSet<String> ingredients = new HashSet<>();
		ArrayList<String> deliveredPizza = new ArrayList<String>();
		double deliveryScore = 0;
		int member = 2;
		String[] iter = new String[2];
		
		while (noOfPizza > 1 && teamOfTwo != 0) {
			teamOfTwo--;
			for (int i = 0; i < 2; i++) {
				if(!listMap.isEmpty()) {
					deliveredPizza.add(String.valueOf(listMap.keySet().toArray()[new Random().nextInt(listMap.keySet().toArray().length)]));
					iter[i] = deliveredPizza.get(i);
					ingredients.addAll(listMap.remove(Integer.valueOf((iter[i]))));
					
					noOfPizza--;
				}
			}
			break;
		}
		StringBuilder sb = new StringBuilder();
		for (String s : deliveredPizza) {
					    sb.append(s);
					    sb.append(" ");
					}
					try {
						write.write(member + " "+ sb);
						
					}
					catch (Exception e){
						e.getStackTrace();
					}
		System.out.println(member + " "+ sb);
		System.out.println("Delivery score is "+ score(ingredients.size()));
		deliveryScore += (score(ingredients.size()));;
		return deliveryScore;
	}
	
	public static double TeamPizza3(int noOfPizza, int teamOfTwo, 
			Map<Integer, ArrayList<String>> listMap, BufferedWriter write) {
		HashSet<String> ingredients = new HashSet<>();
		ArrayList<String> deliveredPizza = new ArrayList<String>();
		double deliveryScore = 0;
		int member = 3;
		String[] iter = new String[3];
		
		while (noOfPizza > 2 && teamOfTwo != 0) {
			teamOfTwo--;
			for (int i = 0; i < 3; i++) {
				if(!listMap.isEmpty()) {
					deliveredPizza.add(String.valueOf(listMap.keySet().toArray()[new Random().nextInt(listMap.keySet().toArray().length)]));
					iter[i] = deliveredPizza.get(i);
					ingredients.addAll(listMap.remove(Integer.valueOf((iter[i]))));
					
					noOfPizza--;
				}
			}
			break;
		}
		StringBuilder sb = new StringBuilder();
		for (String s : deliveredPizza) {
					    sb.append(s);
					    sb.append(" ");
					}
					try {
						write.write(member + " "+ sb);
						
					}
					catch (Exception e){
						e.getStackTrace();
					}
		System.out.println(member + " "+ sb);
		System.out.println("Delivery score is "+ score(ingredients.size()));
		deliveryScore += (score(ingredients.size()));;
		return deliveryScore;
	}
	
	public static double TeamPizza4(int noOfPizza, int teamOfTwo, 
			Map<Integer, ArrayList<String>> listMap, BufferedWriter write) {
		HashSet<String> ingredients = new HashSet<>();
		ArrayList<String> deliveredPizza = new ArrayList<String>();
		double deliveryScore = 0;
		int member = 4;
		String[] iter = new String[4];
		
		while (noOfPizza > 3 && teamOfTwo != 0) {
			teamOfTwo--;
			for (int i = 0; i < 4; i++) {
				if(!listMap.isEmpty()) {
					deliveredPizza.add(String.valueOf(listMap.keySet().toArray()[new Random().nextInt(listMap.keySet().toArray().length)]));
					iter[i] = deliveredPizza.get(i);
					ingredients.addAll(listMap.remove(Integer.valueOf((iter[i]))));
					
					noOfPizza--;
				}
			}
			
			break;
		}
		StringBuilder sb = new StringBuilder();
		for (String s : deliveredPizza) {
					    sb.append(s);
					    sb.append(" ");
					}
					try {
						write.write(member + " "+ sb);
						
					}
					catch (Exception e){
						e.getStackTrace();
					}
		System.out.println(member + " "+ sb);
		System.out.println("Delivery score is "+ score(ingredients.size()));
		deliveryScore += (score(ingredients.size()));;
		return deliveryScore;
	}
	
	public static void replaceLines(String location, String first) throws IOException {
        // BufferedReader object for delete.txt 
        BufferedReader br2 = new BufferedReader(new FileReader(location)); 
          
        String line2 = br2.readLine();
        String parentStringValue = line2.substring(line2.indexOf(" ")+1);
          
        // hashset for storing lines of delete.txt 
        ArrayList<String> hs = new ArrayList<String>();
        hs.add(first+" ");
        
        // loop for each line of delete.txt 
        while(parentStringValue != null) 
        { 
            hs.add(parentStringValue); 
            parentStringValue = br2.readLine(); 
        }
        
        br2.close();
        BufferedWriter pw = new BufferedWriter(new FileWriter(location));
        
        for (int i = 0; i < hs.size(); i++) {
        	pw.write(hs.get(i));
        }
         
          
        pw.flush(); 
          
        // closing resources 
         
        pw.close(); 
          
        System.out.println("File operation performed successfully"); 
    }
	
	
	public static double score(int size) {
		return Math.pow(size, 2);
	}
	
}
