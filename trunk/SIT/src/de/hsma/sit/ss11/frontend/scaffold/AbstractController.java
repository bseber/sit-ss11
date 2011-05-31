package de.hsma.sit.ss11.frontend.scaffold;

/**
 * @author seebaer
 * 
 * @param <V>
 *            the corresponding view
 * @param <U>
 *            the corresponding uiHandler
 */
public abstract class AbstractController<V extends View, U extends UIHandler> {

	protected final V view;
	protected final U uiHandler;

	public AbstractController(V view, U uiHandler) {
		this.view = view;
		this.uiHandler = uiHandler;
	}

	public void hideView() {
		onHide();
		view.setVisible(false);
	}

	public void showView() {
		onShow();
		view.setVisible(true);
	}

	/**
	 * Called within {@link #showView()}. Default implementation does nothing.
	 */
	protected void onHide() {
	}

	/**
	 * Called within {@link #showView()}. Default implementation does nothing.
	 */
	protected void onShow() {
	}

}
