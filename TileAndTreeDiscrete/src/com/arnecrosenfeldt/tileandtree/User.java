package com.arnecrosenfeldt.tileandtree;

public class User {
	private String name; // okay this one may also change. Need a chain
	private int id; // may be valid only for a limited period of time. Only valid for drivers. May change?
	private String password; // may change
	private int function; // todo decide if implemented as enum, or have a class hirarchy with static id

	public User(String name, int id, String password, int function) {
		this.name=name;
		this.id=id;
		this.password=password;
		this.function = function;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getFunction() {
		return function;
	}

	public void setFunction(int function) {
		this.function = function;
	}
}
