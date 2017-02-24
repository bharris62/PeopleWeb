import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static ArrayList<Person> persons = new ArrayList<>();
    static int peopleNumber = 0;
    public static void main(String[] args) throws FileNotFoundException {
        addCsvToArrayList();
        Spark.init();

        Spark.get("/", (request, response)->{
            String id = request.queryParams("id");
            int idNum = (id != null) ? Integer.valueOf(id) : 0;
            ArrayList<Person> paginate = new ArrayList<>();
            for(Person p : persons) {
                if(p.id >= idNum && p.id < idNum+20) {
                    paginate.add(p);
                }
            }

            HashMap m = new HashMap();
            m.put("person", paginate);
            return new ModelAndView(m, "home.html");
        }, new MustacheTemplateEngine());

        Spark.get("/person/:id", (request, response) -> {
            String id = request.params(":id");
            int idNum = (id != null) ? Integer.valueOf(id) : 1;

            HashMap m = new HashMap();
            return new ModelAndView(persons.get(idNum -1),"details.html");
        }, new MustacheTemplateEngine());

        Spark.post("/next", (request, response) -> {
            response.redirect("/");
            return "";
        });
    }




    public static void addCsvToArrayList() throws FileNotFoundException {
        File f = null;
        try {
            f = new File("people.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

