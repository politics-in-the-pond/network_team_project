package my_package;

import java.io.DataOutputStream;

public class member {

	private String name; // 닉네임
	private long nameid; // 회원 고유번호
	private DataOutputStream writer; // 송출부

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNameid() {
		return nameid;
	}

	public void setNameid(long nameid) {
		this.nameid = nameid;
	}

	public DataOutputStream getWriter() {
		return writer;
	}

	public void setWriter(DataOutputStream writer) {
		this.writer = writer;
	}
}
