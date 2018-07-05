import java.util.ArrayList;

/*
 * Record actor's name and list of movies in which actor had a role.
 */

public class ActorRecord {
  public String name;
  ArrayList<String> movies;
  ArrayList<String> FM;
  ArrayList<String> TV;
  ArrayList<String> TS;
  ArrayList<String> VO;

  public ActorRecord(String n) {
    name = n;
    movies = new ArrayList<String>();
    FM = new ArrayList<String>();
    TV = new ArrayList<String>();
    TS = new ArrayList<String>();
    VO = new ArrayList<String>();
  }

  /* Add a movie to the list for this actor.  */
  public void addMovie(String m) {
    movies.add(m);
  }
  
  public void addFM(String m) {
	    FM.add(m);
	  }
  
  public void addTV(String m) {
	    TV.add(m);
	  }
  
  public void addTS(String m) {
	    TS.add(m);
	  }
  
  public void addVO(String m) {
	    VO.add(m);
	  }
  
  public String toString(){
    String s = name;
    for(String m : movies)
      s += " "+m;
    return s;
  }
}
