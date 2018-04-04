//Made by chkrr00k in GNU GPLv3

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class IterableNodeList implements NodeList, Iterable<Node> {
	
	private NodeList nl;

	public IterableNodeList(NodeList nl) {
		this.nl = nl;
	}
	public IterableNodeList(Element root, boolean normalize) {
		if(normalize){
			root.normalize();
		}
		this.nl = root.getChildNodes();
	}
	public IterableNodeList(Element root) {
		this(root, true);
	}

	@Override
	public Node item(int index) {
		return this.nl.item(index);
	}

	@Override
	public int getLength() {
		return this.nl.getLength();
	}

	@Override
	public Iterator<Node> iterator() {
		return new IterableNodeListIterator(this);
	}
	
	public Stream<Node> stream(){
		return StreamSupport.stream(Spliterators.spliterator(this.iterator(), this.getLength(), Spliterator.SIZED), false);
	}

	public class IterableNodeListIterator implements Iterator<Node> {
		
		private IterableNodeList inl;
		private int cur;

		public IterableNodeListIterator(IterableNodeList inl) {
			this.inl = inl;
			this.cur = 0;
		}

		@Override
		public boolean hasNext() {
			return this.cur < this.inl.getLength();
		}

		@Override
		public Node next() {
			return this.inl.item(this.cur++);
		}

	}
	
}
