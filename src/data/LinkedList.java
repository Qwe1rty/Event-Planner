package data;

/**
 * A generic Linked List object. Allows for a dynamic list of items
 * with no size limit. Each item is singly linked with another child item.
 *   
 * @author Caleb Choi
 * @param <E>
 */
public class LinkedList<E> {
	
	private Link<E> headLink;
	
	public LinkedList(E item) {headLink = new Link<E>(item);}
	public LinkedList() {headLink = null;}
	
	/**
	 * Appends an item to the bottom of the list. A boolean is returned
	 * depending on whether the operation is successful
	 * 
	 * @param item Item to be appended to the list
	 * @return Returns true upon successful append
	 */
	public boolean append(E item) {
		if (headLink == null) {
			headLink = new Link(item);
			return true;
		} else return headLink.append(item);
	}
	
	/**
	 * Returns an item from the list
	 * 
	 * @param index Index of the item in the linked list
	 * @return The item at the index
	 */
	public E get(int index) {
		if (headLink == null) return null;
		else return headLink.getItem(index);			
	}
	
	/**
	 * Returns the index of the first instance of a given item
	 * 
	 * @param item Item to index in list
	 * @return The index of the first occurrence of the given item. 
	 * 		If not found, method will return -1
	 */
	public int indexOf(E item) {
		if (headLink == null) return -1;
		else return headLink.indexOf(item, 0);
	}
	
	/**
	 * Removes an item from the list, given its index
	 * 
	 * @param index Index of the item to be removed
	 * @return Will return true if operation is successful. Otherwise, false is returned
	 */
	public boolean remove(int index) {
		if (headLink == null || index < 0) return false;
		else if (index == 0) {
			if (headLink.getLink() == null) headLink = null;
			else headLink = headLink.getLink();
			return true;
		} else return headLink.remove(index);
	}
	/**
	 * Removes an item from the list, given the item
	 * 
	 * @param item Item to be removed
	 * @return Will return true if operation is successful. Otherwise, false is returned
	 */
	public boolean remove(E item) {
		if (headLink == null) return false;
		else if (headLink.getItem() == item) {
			if (headLink.getLink() == null) headLink = null;
			else headLink = headLink.getLink();
			return true;
		} else return headLink.remove(item);
	}
	
	/**
	 * Inserts an item into the list
	 * 
	 * @param index Index of the item to be inserted to
	 * @param item Item to be inserted
	 * @return Will return true if operation is successful. Otherwise, false is returned
	 */
	public boolean insert(int index, E item) {
		int size = size();
		if (index < 0 || index > size) return false;
		else if (index == 0 && headLink != null) {
			Link newLink = new Link(item);
			newLink.setLink(headLink);
			headLink = newLink;
			return true;
		} else if (index == 0) {
			headLink = new Link(item);
			return true;
		} else return headLink.insert(index, item);
	}
	
	/**
	 * Returns the current size of the list
	 * 
	 * @return Size of the list
	 */
	public int size() {
		if (headLink == null) return 0;
		else return headLink.size(1);
	}
	
	/**
	 * Prints out the contents of the list to the console. Used for debugging
	 */
	public void list() {
		if (headLink != null) headLink.print(0);
		else System.out.println("empty");
	}
	
	/**
	 * Fully empties and clears the linked list
	 */
	public void clear() {headLink = null;}
	
	/**
	 * Swaps two items in the list. Primarily useful for sorting algorithms
	 * @param a Index of the first item
	 * @param b Index of the second item
	 * @return Will return true if operation is successful. Otherwise, false is returned
	 * @throws Exception If either indexes are invalid, exception is thrown
	 */
	public boolean swap(int a, int b) throws Exception {
		int size = size();
		if (a < 0 || b < 0 || a >= size || b >= size) return false;

		E ib = get(b);
		E ia = get(a);
	
		remove(a);
		insert(a, ib);
			
		remove(b);
		insert(b, ia);
			
		return true;
	}
	
//	@SuppressWarnings("unchecked")
//	public void sort(Parameter p, boolean ascending) {
//		int length = this.size();
//		for (int gap = length / 2; gap > 0; gap /= 2) {
//			for (int wall = gap; wall < length; wall++) {
//				try {
//					for (int index = wall; index >= gap && get(index).compareTo(get(index - gap)) < 0; index -= gap) {
//						swap(index - gap, index);
//					}
//				} catch (Exception e) {System.out.println("nope");}
//			}
//		}
//	}

	/**
	 * Generic link container. Holds the item, and another child link.
	 * 
	 * @author Caleb Choi
	 * @param <E>
	 */
	private class Link<E> {
		
		private E item;
		private Link<E> link;
	
		private Link(Object item) {
			set(item);
			link = null;
		}
		
		private void set(Object item) {this.item = (E) item;}
		
		private E getItem() {return item;}
		private E getItem(int i) {
			if (i == 0) return item;
			else if (getLink() == null) return null;
			else return getLink().getItem(i - 1);
		}
		private Link<E> getLink() {return link;}
		private void setLink(Link link) {this.link = link;}
		
		private boolean append(E item) {
			if (getLink() == null) {
				setLink(new Link(item));
				return true;
			}
			else return link.append(item);
		}
		
		private int indexOf(E e, int i) {
			if (e.equals(getLink())) return i;
			else if (getLink() != null) return getLink().indexOf(e, i + 1);
			else return -1;
		}
		
		private boolean remove(int index) {
			if (getLink() == null) return false;
			else if (index == 1) {
				if (link.getLink() == null) setLink(null);
				else setLink(getLink().getLink());
				return true;
			} else return link.remove(index - 1); 
		}
		private boolean remove(E e) {
			if (getLink() == null) return false;
			else if (getLink().getItem() == e) {
				if (link.getLink() == null) setLink(null);
				else setLink(getLink().getLink());
				return true;
			} else return link.remove(e);
		}
		
		private boolean insert(int index, E e) {
			if (index == 1) {
				Link newLink = new Link(e);
				if (getLink() != null) newLink.setLink(link);
				setLink(newLink);
				return true;
			} else if (getLink() == null) return false;
			else return getLink().insert(index - 1, e);
		}
		
		private int size(int count) {
			if (getLink() == null) return count;
			else return link.size(count + 1);
		}
		
		private void print(int index) {
			System.out.println(index + " " + item);
			if (getLink() != null) link.print(index + 1);
		}
	}
	
}
