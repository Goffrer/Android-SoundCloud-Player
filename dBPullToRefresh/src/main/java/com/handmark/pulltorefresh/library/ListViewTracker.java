package com.handmark.pulltorefresh.library;

import android.util.SparseIntArray;
import android.widget.AbsListView;

public class ListViewTracker {
	private AbsListView mListView;
	private SparseIntArray mPositions;

	public ListViewTracker(final AbsListView listView) {
		mListView = listView;
	}

	/**
	 * Call from an AbsListView.OnScrollListener to calculate the incremental
	 * offset (change in scroll offset since the last calculation).
	 * 
	 * @param firstVisiblePosition
	 *            First visible item position in the list.
	 * @param visibleItemCount
	 *            Number of visible items in the list.
	 * @return The incremental offset, or 0 if it wasn't possible to calculate
	 *         the offset.
	 */
	public int calculateIncrementalOffset(final int firstVisiblePosition, final int visibleItemCount) {
		// Remember previous positions, if any
		SparseIntArray previousPositions = mPositions;

		// Store new positions
		mPositions = new SparseIntArray();
		for (int i = 0; i < visibleItemCount; i++) {
			mPositions.put(firstVisiblePosition + i, mListView.getChildAt(i).getTop());
		}

		if (previousPositions != null) {
			// Find position which exists in both mPositions and
			// previousPositions, then return the difference
			// of the new and old Y values.
			for (int i = 0; i < previousPositions.size(); i++) {
				int position = previousPositions.keyAt(i);
				int previousTop = previousPositions.get(position);
				Integer newTop = mPositions.get(position);
				if (newTop != null) {
					return newTop - previousTop;
				}
			}
		}

		return 0; // No view's position was in both previousPositions and
					// mPositions
	}

	public void clear() {
		mPositions = null;
	}
}
