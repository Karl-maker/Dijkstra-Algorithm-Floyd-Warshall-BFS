import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Map graph = new Map();
        String filename = "Paths.txt";
        File getAbsolute = new File(filename);
        String absolute = getAbsolute.getAbsolutePath();

        int i = 0;
        ArrayList<String> getSource = new ArrayList<String>(), getDestination = new ArrayList<String>();
        ArrayList<String> getDistance = new ArrayList<String>();

        try{
            Scanner scanner = new Scanner(new FileReader(absolute));
            do{
                while(scanner.hasNext()){

                    scanner.useDelimiter(", |\\r\\n");

                    getSource.add(scanner.next());
                    getDestination.add(scanner.next());
                    getDistance.add(scanner.next());

                    int num =Integer.parseInt(getDistance.get(i));
                    graph.addPath(getSource.get(i), getDestination.get(i), num);
                    i++;
                }
            }while(scanner.nextLine() != null);

            scanner.close();

        } catch(Exception e){
            //System.out.println(e);
        }



        boolean isRunning = true;

        while(isRunning){

            switch(displayOptions()){

                case '1':
                    graph.BFS(getSource.get(0)); //First Option is Source
                    break;
                case '2':
                    System.out.println("Select Destination:");
                    graph.displayLocations();
                    try{
                        graph.Dijkstra(getSource.get(0), graph.getSource(pickLocations()));
                    }catch(Exception e){
                        System.out.println("Please Enter Something Sensible Im Tired And Cannot Handle Errors");
                    }
                    break;
                case '3':
                    isRunning = false;
                    break;
                default:
                    System.out.println("Please Select Options...");
            }
        }
    }

    public static char displayOptions(){

        Scanner scanner = new Scanner(System.in);
        System.out.print(
                "\n\nPlease Select Option Below By Entering Corresponding Number:\n\n" +
                        "1) Show All Locations\n" +
                        "2) Find Shortest Path To Place\n" +
                        "3) Exit System\n\nEnter Option Here: ");

        char option = scanner.next().charAt(0);
        return option;
    }

    public static int pickLocations(){

        System.out.print("\nSelect Location: ");
        Scanner scanner = new Scanner(System.in);

        String option = scanner.next();
        int i = Integer.parseInt(option);
        return i;
    }

}
