package de.hsma.sit.ss11.frontend.view.widgets;

import java.util.Arrays;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractListModel;

@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
public class SortedListModel extends AbstractListModel {

	private SortedSet model;

	public SortedListModel() {
		model = new TreeSet();
	}

	public int getSize() {
		return model.size();
	}

	public Object getElementAt(int index) {
		return model.toArray()[index];
	}

	public void add(Object element) {
		if (model.add(element)) {
			fireContentsChanged(this, 0, getSize());
		}
	}

	public void addAll(Object elements[]) {
		Collection c = Arrays.asList(elements);
		model.addAll(c);
		fireContentsChanged(this, 0, getSize());
	}

	/**
	 * Removes all of the elements from this set (optional operation). The set
	 * will be empty after this call returns.
	 */
	public void clear() {
		model.clear();
		fireContentsChanged(this, 0, getSize());
	}

	public boolean removeElement(Object element) {
		boolean removed = model.remove(element);
		if (removed) {
			fireContentsChanged(this, 0, getSize());
		}
		return removed;
	}

}
