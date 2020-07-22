package view;

import java.util.EventListener;
import java.util.List;

import entity.PrisonList;
import entity.Prisoner;

public interface TablePrisonerInListListener extends EventListener{
	public void savePrisonInfo(PrisonList prl, List<Prisoner> listReleasedPrisoners, List<Prisoner> listTransferedPrisoners);
}