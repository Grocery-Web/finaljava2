package view;

import java.util.EventListener;

public interface TablePrisonerInListListener extends EventListener{
	public void releasePrisoner(int id);
	public void transferPrisoner(int idFrom, int idTo, int prisonerID );
}