package com.cryption.model;

public class Content {
String text;

public Content(String text) {
	super();
	this.text = text;
}

@Override
public String toString() {
	return "Content [text=" + text + "]";
}

public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}

}
