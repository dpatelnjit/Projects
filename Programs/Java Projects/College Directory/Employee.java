public class Employee extends Person {
	private String Dept;

	public Employee(String position, String ucid, String first, String last,String dept) {
		super(position, ucid, first, last);
		Dept = dept;
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

	void setDept(String dept) {
		Dept = dept;
	}

	String getDept() {
		return Dept;
	}

	@Override
	public String toString() {
		return super.toString() + " " + Dept;
	}
}