package com.demo.domain;

public class ClientErrorInformation {
 private String eString;
 private String RequestURI;
public ClientErrorInformation(String eString, String requestURI) {
	this.eString = eString;
	RequestURI = requestURI;
}
public String geteString() {
	return eString;
}
public void seteString(String eString) {
	this.eString = eString;
}
public String getRequestURI() {
	return RequestURI;
}
public void setRequestURI(String requestURI) {
	RequestURI = requestURI;
}


 
 
}
