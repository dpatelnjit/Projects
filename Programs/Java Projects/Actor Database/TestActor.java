import java.util.ArrayList;

public class TestActor {

  public static void main(String[] args) throws Exception {

    RetrieveActors ra = new RetrieveActors("../RESOURCES/IMDB/actors.list.gz");

    String content;
    String[] tkn;
    int count = 0;

    while ((content = ra.getNext()) != null) {

      ++count;
      if (count % 1000 == 0)
        System.out.println("count= " + count);
      tkn = content.split("@@@");
      System.out.print(tkn[0]+"  ");
      for (int i = 1; i < tkn.length; ++i){
        System.out.print(tkn[i].substring(2)+"  ");
      }
      System.out.println();
    }
  }
}
