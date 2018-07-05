import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;

public class ReadActors {

  public static void main(String[] args) throws Exception {
    ArrayList<String> actors = new ArrayList<String>();
    ArrayList<LinkedList<String>> movies = new ArrayList<LinkedList<String>>();
    ArrayList<ActorRecord> act = new ArrayList<ActorRecord>();

    BufferedReader in = new BufferedReader(new InputStreamReader(
        new GZIPInputStream(new FileInputStream("../../calvin/RESOURCES/IMDB/actors.list.gz"))));
    // "F:/gawiki-20090614-stub-meta-history.xml.gz"))));

    String content;

    while ((content = in.readLine()) != null) {
      if (content.contains("Name") && content.contains("Title")) {
        content = in.readLine();
        break;
      }
    }

    int count = 0;
    String[] acMv;
    while ((content = in.readLine()) != null) {
      if (content.contains("-----------"))  // end of entries
        break;
      ++count;
      if (count % 1000 == 0)
        System.out.println("count= " + count);
      acMv = content.split("[\t]+");
      ActorRecord ar = new ActorRecord(acMv[0]);
      ar.addMovie(acMv[1]);
      while (true) {
        content = in.readLine();
        if (content.length() < 1)
          break;
        content = content.replaceAll("\t", "");
        // System.out.println("Adding movie " + content);
        ar.addMovie(content);
      }
      act.add(ar);
    }
    //for (ActorRecord a : act)
      //System.out.println(a);
    
    String busyActor = "";
    int max = 0;
    for (ActorRecord a : act)
      if(a.movies.size()>max){
        max = a.movies.size();
        busyActor = a.name;
      }
    System.out.println("Busiest actor: "+busyActor+", "+max+" movies.");
  }
}
