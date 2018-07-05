public class Student extends Person {
	private String Major;
	private String Email;

	public Student(String position, String ucid, String first, String last,	String major, String email) {
		super(position, ucid, first, last);
		Major = major;
		Email = email;
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

	void setMajor(String major) {
		Major = major;
	}

	String getMajor() {
		return Major;
	}

	void setEmail(String email) {
		Email = email;
	}

	String getEmail() {
		return Email;
	}

	@Override
	public String toString() {
		return super.toString() + " " + Major + " " + Email;
	}
}