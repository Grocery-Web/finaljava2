package view;

import java.util.EventListener;

import entity.PrisonList;
import entity.Prisoner;

public interface RelevantPrisonerFormListener extends EventListener {
	public void prisonerFormEventListener(Prisoner prisoner, PrisonList prison);
}
