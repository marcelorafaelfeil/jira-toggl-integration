package marcelorafael.lab.jiratogglintegration.core.system.menuactions;

import marcelorafael.lab.jiratogglintegration.core.ExecutionServices;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseAction implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		ExecutionServices.close();
	}
}
