package view;

import java.util.EventListener;

public interface TablePersonListener extends EventListener{
	public void tableEventDeleted(int id);
	public void tableEventAttached(int id);
	public void tableEventPersonDetail(int id);
	public void tableEventAddVictim(int id);
}
