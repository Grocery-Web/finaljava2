package view;

import java.util.EventListener;

import entity.ComplaintDetailEntity;

public interface RelevantFormListener extends EventListener{
	public void formEventListener(ComplaintDetailEntity comDetail);
}
