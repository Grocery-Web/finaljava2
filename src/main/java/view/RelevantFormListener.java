package view;

import java.util.EventListener;

import entity.ComplaintDetail;

public interface RelevantFormListener extends EventListener{
	public void formEventListener(ComplaintDetail comDetail);
}
