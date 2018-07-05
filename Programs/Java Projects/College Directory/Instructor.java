public class Instructor extends Employee {
	private String Email;
	private String Office;

	public Instructor(String position, String ucid, String first, String last, String email, String office, String dept) {
		super(position, ucid, first, last, dept);
		Email = email;
		Office = office;
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

	void setEmail(String email) {
		Email = email;
	}

	String getEmail() {
		return Email;
	}

	void setOffice(String office) {
		Office = office;
	}

	String getOffice() {
		return Office;
	}

	@Override
	public String toString() {
		return super.toString() + " " + Email + " " + Office;
	}
}