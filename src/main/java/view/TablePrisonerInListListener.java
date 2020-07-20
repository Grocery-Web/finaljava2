package view;

import java.util.EventListener;

import entity.PrisonList;

public interface TablePrisonerInListListener extends EventListener{
	public void releasePrisoner(int id);
	public void transferPrisoner(int idFrom, int idTo, int prisonerID );
	public void savePrisonInfo(String name, String address, int prisonID);
}