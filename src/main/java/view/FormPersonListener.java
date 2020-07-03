package view;

import java.io.File;
import java.util.EventListener;

import entity.Person;

public interface FormPersonListener extends EventListener{
	public void insertEventListener(Person per,File file);
}
