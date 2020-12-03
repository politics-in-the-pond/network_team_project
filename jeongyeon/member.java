package my_package;

import java.io.PrintWriter;

public class member {
	private String name;
	private String nameid;
	private PrintWriter writer;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameid() {
		return nameid;
	}
	public void setNameid(String nameid) {
		this.nameid = nameid;
	}
	public PrintWriter getWriter() {
		return writer;
	}
	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}
}
