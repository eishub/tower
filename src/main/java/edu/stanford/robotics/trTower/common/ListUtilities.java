package edu.stanford.robotics.trTower.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class ListUtilities {

	public static List<String> stringToList(final String s) {
		return stringToList(s, null);
	}

	public static List<String> stringToList(final String s, final String delimiter) {
		final List<String> newList = new LinkedList<>();
		StringTokenizer st;
		if (delimiter != null) {
			st = new StringTokenizer(s, delimiter);
		} else {
			st = new StringTokenizer(s);
		}

		while (st.hasMoreTokens()) {
			newList.add(st.nextToken());
		}

		return newList;
	}

	// input: list of Strings
	public static String listToString(final List<String> l) {
		return listToString(l, ", ", "(", ")");
	}

	public static String listToString(final List<String> l, final String delimiter, final String beginList,
			final String endList) {
		final StringBuffer sb = new StringBuffer();
		sb.append(beginList);

		// append first item
		final Iterator<String> i = l.iterator();
		if (i.hasNext()) {
			final String item = (i.next());
			sb.append(item);
		}

		// append the rest of items
		while (i.hasNext()) {
			final String items = (i.next());
			sb.append(delimiter + items);
		}

		sb.append(endList);

		return sb.toString();
	}

	public static List<String> newList(final String item) {
		final List<String> list = new ArrayList<>(1);
		list.add(item);
		return list;
	}

	public static List<String> newList(final String item1, final String item2) {
		final List<String> list = new ArrayList<>(2);
		list.add(item1);
		list.add(item2);
		return list;
	}

	public static String car(final List<String> l) {
		final Iterator<String> i = l.iterator();
		if (i.hasNext()) {
			return i.next();
		} else {
			return null;
		}
	}

	public static List<String> cdr(final List<String> l) {
		final Iterator<String> i = l.iterator();
		final List<String> list = new LinkedList<>();
		if (i.hasNext()) {
			i.next();
			while (i.hasNext()) {
				list.add(i.next());
			}
		}
		return list;
	}

	public static String cadr(final List<String> l) {
		// return second element on list
		return car(cdr(l));
	}

	public static List<String> cons(final String o, final List<String> l) {
		final List<String> newList = new ArrayList<>(l.size() + 1);
		newList.add(o);
		newList.addAll(1, l);
		return newList;
	}

	public static List<List<String>> listLengthFilter(final List<List<String>> listOfLists, final int c) {
		final List<List<String>> newList = new LinkedList<>();
		final Iterator<List<String>> i = listOfLists.iterator();
		while (i.hasNext()) {
			final List<String> listItem = (i.next());
			if (listItem.size() == c) {
				newList.add(listItem);
			}
		}
		return newList;
	}
}
