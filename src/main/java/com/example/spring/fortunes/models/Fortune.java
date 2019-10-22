package com.example.spring.fortunes.models;

public class Fortune {
	private String content="";
	
	public Fortune(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("fortune [content=");
		builder.append(content);
		builder.append("]");
		return builder.toString();
	}
	
}
