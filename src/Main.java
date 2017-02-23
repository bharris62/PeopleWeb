import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Person> persons = new ArrayList<>();
    public static void main(String[] args) {
        try {
            addCsvToArrayList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(Person p: persons){
            System.out.println(p.getId());
        }
    }

    public static void addCsvToArrayList() throws FileNotFoundException {
        File f = new File("people.csv");
        Scanner scanner = new Scanner(f);

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            //0 id; 1 first_name; 2 last_name; 3 email; 4 country; 5 ip_address
            String[] c = line.split(",");
            if(c[0].equals("id")){
                continue;
            }
            Person p = new Person((Integer.valueOf(c[0])), c[1], c[2], c[3], c[4], c[5]);
            persons.add(p);

        }
    }
}

