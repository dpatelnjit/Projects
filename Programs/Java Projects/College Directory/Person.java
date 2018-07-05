public class Person {
	protected String Position; 
	protected String UCID;
	protected String First;
	protected String Last;

	public Person(String position, String ucid, String first, String last) {
		Position = position;
		UCID = ucid;
		First = first;
		Last = last;
	}

	void setPosition(String position) {
		Position = position;
	}

	String getPosition() {
		return Position;
	}

	void setUCID(String ucid) {
		UCID = ucid;
	}

	String getUCID() {
		return UCID;
	}

	void setFirst(String first) {
		First = first;
	}

	String getFirst() {
		return First;
	}

	void setLast(String last) {
		Last = last;
	}

	String getLast() {
		return Last;
	}

	@Override
	public String toString() {
		return Position + " " + UCID + " " + First + " " + Last;
		// return(String.format("%s, %d, %s, %s", Position, UCID, First, Last));
	}
}