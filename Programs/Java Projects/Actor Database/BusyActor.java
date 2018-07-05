import java.util.ArrayList;

public class BusyActor {

  public static void main(String[] args) throws Exception {
	  
    ArrayList<ActorRecord> act = new ArrayList<ActorRecord>();

    String fname = "actors.list.gz";

    RetrieveActors ra = new RetrieveActors(fname);

    String content;
    String[] tkn;
    int count = 0;

    while ((content = ra.getNext()) != null) {
      ++count;
      //if (count % 1000 == 0)
        //System.out.println("count= " + count);
      tkn = content.split("@@@");
      ActorRecord ar = new ActorRecord(tkn[0]);
      for (int i = 1; i < tkn.length; ++i){
        if(tkn[i].substring(0, 2).equals("TS"))
        ar.addTS(tkn[i].substring(2));
      }
      act.add(ar);
    }
    ra.close();

    String busyActor = "";   
    
    int max = 0;
    for (ActorRecord a : act)
      if (a.TS.size() > max) {
        max = a.TS.size();
        busyActor = a.name;
      }
    System.out.println("Actor: " + busyActor + " has the most TV roles with " + max);
  }
}
