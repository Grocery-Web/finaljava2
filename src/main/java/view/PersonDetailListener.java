package view;

import java.util.EventListener;

import entity.Person;

public interface PersonDetailListener  extends EventListener{
	public void formEventListener(int id);
	public void updateEventListener(Person acc);
}
