package edu.stanford.robotics.trTower.common;

import java.util.*;

public class ListUtilities {

    public static List stringToList(String s) {
	return stringToList(s, null);
    }

    public static List stringToList(String s, String delimiter) {

	List newList = new ArrayList();
	StringTokenizer st;
	if (delimiter != null)
	    st = new StringTokenizer(s, delimiter);
	else
	    st = new StringTokenizer(s);
	    
	while (st.hasMoreTokens()) {
	    newList.add(st.nextToken());
	}
	
	return newList;
    }

    // input: list of Strings
    public static String listToString(List l) {
	return listToString(l, ", ", "(", ")");
    }

    public static String listToString(List l, String delimiter, 
				      String beginList, String endList) {
	StringBuffer sb = new StringBuffer();
	sb.append(beginList);
	
	// append first item
	Iterator i = l.iterator();
	if (i.hasNext()) {
	    String item = (String)(i.next());
	    sb.append(item);
	}

	// append the rest of items
	while (i.hasNext()) {
	    String items = (String)(i.next());
	    sb.append(delimiter + items);
	}
	
	sb.append(endList);
	
	return sb.toString();
    }


    public static List newList(Object item) {
	List list = new ArrayList();
	list.add(item);
	return list;
    }
    public static List newList(Object item1, Object item2) {
	List list = new ArrayList();
	list.add(item1);
	list.add(item2);
	return list;
    }
	
    public static Object car(List l) {
	Iterator i = l.iterator();
	if (i.hasNext())
	    return i.next();
	else
	    return null;
    }

    public static List cdr(List l) {
	Iterator i = l.iterator();
	Object dummy;
	List list = new ArrayList();
	if (i.hasNext()) {
	    dummy = i.next();
	    while (i.hasNext()) {
		list.add(i.next());
	    }
	}
	return list;
    }
    
    public static Object cadr(List l) {
	// return second element on list
	return car(cdr(l));
    }
    
    public static List cons(Object o, List l) {
	List newList = new ArrayList();
	newList.add(o);
	newList.addAll(1, l);
	return newList;
    }
    public static List listLengthFilter(List listOfLists, int c) {
	List newList = new ArrayList();
	Iterator i = listOfLists.iterator();
	while (i.hasNext()) {
	    List listItem = (List)(i.next());
	    if (listItem.size() == c)
		newList.add(listItem);
	}
	return newList;
    }
}
