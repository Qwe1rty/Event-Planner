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
	
	public boolean append(E item) {
		if (headLink == null) {
			headLink = new Link(item);
			return true;
		} else {
			return headLink.append(item);
		}
	}
	
	public E get(int index) {
		if (headLink == null) return null;
		else {return headLink.getItem(index);}			
	}
	
	public int indexOf(E e) {
		if (headLink == null) return -1;
		else return headLink.indexOf(e, 0);
	}
	
	public boolean remove(int index) {
		if (headLink == null || index < 0) return false;
		else if (index == 0) {
			if (headLink.getLink() == null) headLink = null;
			else headLink = headLink.getLink();
			return true;
		} else return headLink.remove(index);
	}
	public boolean remove(E e) {
		if (headLink == null) return false;
		else if (headLink.getItem() == e) {
			if (headLink.getLink() == null) headLink = null;
			else headLink = headLink.getLink();
			return true;
		} else return headLink.remove(e);
	}
	
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
	
	public int size() {
		if (headLink == null) return 0;
		else return headLink.size(1);
	}
	
	public void list() {
		if (headLink != null) headLink.print(0);
		else System.out.println("empty");
	}
	
	public void clear() {headLink = null;}
	
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
