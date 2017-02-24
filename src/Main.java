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
    static final int numPerPage = 10;
    public static void main(String[] args) throws FileNotFoundException {
        addCsvToArrayList();
        Spark.init();

        Spark.get("/", (request, response)->{
            String id = request.queryParams("offset");
            int idNum = (id != null) ? Integer.valueOf(id) : 0;
            ArrayList<Person> paginate = new ArrayList<>();
            for(Person p : persons) {
                if(p.id > idNum && p.id <= idNum+numPerPage) {
                    paginate.add(p);
                }
            }
            Session session = request.session();
            session.attribute("idNum", idNum+numPerPage);

            HashMap m = new HashMap();
            m.put("person", paginate);
            m.put("numPerPage", numPerPage);
            return new ModelAndView(m, "home.html");
        }, new MustacheTemplateEngine());

        Spark.get("/person/id/:id", (request, response) -> {
            String id = request.params(":id");
            int idNum = (id != null) ? Integer.valueOf(id) : 0;

            HashMap m = new HashMap();
            return new ModelAndView(persons.get(idNum -1),"details.html");
        }, new MustacheTemplateEngine());

        Spark.post("/next", (request, response) -> {
            Session session = request.session();
            int id = session.attribute("idNum");
            if(id > persons.size()) {
                id -= numPerPage;
            }
            response.redirect("/?offset=" + id);
            return "";
        });

        Spark.post("/previous", (request, response) -> {
            Session session = request.session();
            int id = session.attribute("idNum");
            id = id - ((numPerPage * 2));
            if(id <= 0 ){
                id = 0;
            }
            response.redirect("/?offset=" + id);
            return "";
        });

        Spark.post("/search", (request, response)->{
            String name = request.queryParams("searchBox");
            //int idNumber = Integer.parseInt(idNum);
            Person person = persons.stream()
                                            .filter(m->m.first_name.toLowerCase().equals(name.toLowerCase()))
                                            .findFirst().get();
            response.redirect("/person/id/" + person.id);
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

