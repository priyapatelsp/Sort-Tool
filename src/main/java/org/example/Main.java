package org.example;

import org.w3c.dom.Element;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static String extractFileName(String command) {
        // Regular expression to match the filename
        Pattern pattern = Pattern.compile("\\b(\\w+\\.txt)\\b");
        Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            return matcher.group(1); // Return the matched filename
        } else {
            return null; // Return null if no match found (handle error case)
        }
    }
    public static String extractAdditionalCommand(String command) {
        // Regular expression to match the additional command after the pipe (|)
        Pattern pattern = Pattern.compile("\\|\\s*(.+)$");
        Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            return matcher.group(1).trim(); // Return the matched additional command
        } else {
            return null; // Return null if no match found (handle error case)
        }
    }
    public static int extractLines(String command) {
        // Regular expression to match the number of lines following "-n" in the "head" command
        Pattern pattern = Pattern.compile("\\|\\s*head\\s*-n(\\d+)");
        Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            String digits = matcher.group(1); // Extract the digits after "-n"
            return Integer.parseInt(digits); // Convert the extracted digits to an integer
        } else {
            return -1; // Return a default value (or handle error case)
        }
    }
    public static void main(String[] args) throws IOException {
        String fileName="test.txt";

            System.out.println("Welcome to the sorting tool ::");
            System.out.println("----------------------------------");
            System.out.println("Please provide one of the below command to proceed");
            System.out.println("Normal all elements with all words :: sort test.txt");
            System.out.println("Use -u to get sorted elements ex:: sort -u test.txt");
            System.out.println("Use -head n5 to get fixed no of elements ex:: sort -u test.txt | head -n5");
            System.out.println("Use --random-sort to get random elements ex:: sort --random-sort -u test.txt | head -n5");
            System.out.println("To exit type ::exit");
            System.out.println("----------------------------------");
        while(true) {
            System.out.println("Please provide commmand");
            Scanner in = new Scanner(System.in);
            String userInputCommand = in.nextLine();
            // if user want to exit
            if(userInputCommand.toLowerCase().contains("exit")){
                break;
            }

            String userInput = extractFileName(userInputCommand);
            if(!userInputCommand.contains("sort")){
                System.out.println("Please give the correct input to proceed");
                continue;
            }
            String additionalCommand=extractAdditionalCommand(userInputCommand);

            if(userInput.isBlank())userInput=fileName;
        try{
            String path = "src/test/";
            File file = new File(path);
            String absolutePath = file.getAbsolutePath()+"/"+userInput;
            System.out.println(absolutePath);
            BufferedReader reader = new BufferedReader(new FileReader(absolutePath));
            String nextLine=reader.readLine();
            List<String> allWords=new ArrayList<>();
            Set<String> set = new HashSet<> ();
            while ( nextLine!= null) {
                if(!nextLine.isBlank())
                {   nextLine=nextLine.replaceAll("\\p{Punct}", "").replaceAll("[0-9]", "");
                    String[] split = nextLine.strip().trim().split("\\s");
                    for (String s :split )if (s != null && !s.isEmpty())allWords.add(s);
                    for (String s :split )if (s != null && !s.isEmpty())set.add(s);
                }
                nextLine=reader.readLine();
            }
            reader.close();

            // this is all the words they include repeated items too
            allWords.sort(null);


            if(userInputCommand.contains("--random-sort")){
                System.out.println("Sdf");
                List<String> numberList = new ArrayList<>(set);
                Collections.shuffle(numberList); // shuffle randomly
                System.out.println("This are your randomly shuffled items :: ");
                if(additionalCommand!=null && additionalCommand.contains("head")){
                    int n=extractLines(userInputCommand);
                    for(Object element : numberList){
                        if(n<0)break;
                        System.out.println(element);
                        n--;
                    }
                }else{
                    System.out.println(numberList);
                }
            }
            else if(userInputCommand.contains("-u")){
                // this helps us to sort the item
                TreeSet myTreeSet = new TreeSet(set);
                System.out.println("additionalCommand:"+additionalCommand);
                if(additionalCommand!=null && additionalCommand.contains("head")){
                    int n=extractLines(userInputCommand);

                    System.out.println("nn"+n);
                    for(Object element : myTreeSet){
                        if(n<0)break;
                        System.out.println(element);
                        n--;
                    }
                }else{
                    System.out.println(myTreeSet);
                }
            }else{
                if(additionalCommand!=null && additionalCommand.contains("head")){
                    int n=extractLines(userInputCommand);
                    for(Object element : set){
                        if(n<0)break;
                        System.out.println(element);
                        n--;
                    }
                }else{
                    System.out.println(set);
                }
            }
        }
        catch (Exception e){
            System.out.println("Please provide the correct path (absolute path) and try again :) ");
        }
        }
    }

}