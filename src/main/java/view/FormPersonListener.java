package view;

import java.util.EventListener;

import entity.Person;

public interface FormPersonListener extends EventListener{
	public void insertEventListener(Person per);
}
