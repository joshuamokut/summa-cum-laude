/**
 * 
 */
package taxicompare.app;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * @author joshu
 *
 */
public class WindowEventHandler implements WindowListener {
	private JFrame app;
	
	
	public JFrame getApp() {
		return app;
	}

	public void setApp(JFrame app) {
		this.app = app;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent we) {
		// TODO Auto-generated method stub
		if (we.getSource().equals(app)) {
			System.out.println("dispose of entrypoint class instance");
			app.dispose();
		}
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
