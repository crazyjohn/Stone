package com.stone.core.map;

public class BinaryThreeMap<Key, Value> implements IBinaryThreeMap<Key, Value> {
	private Entry root = null;
	private int size;

	class Entry {
		private final Key key;
		private Value value;
		private Entry leftChild;
		private Entry rightChild;
		private Entry parent;

		public Entry(Key key, Value value) {
			this.key = key;
			this.value = value;
		}

		public Entry(Key key, Value value, Entry leftChild, Entry rightChild, Entry parent) {
			this(key, value);
			this.leftChild = leftChild;
			this.rightChild = rightChild;
			this.parent = parent;
		}

		public Entry getLeftChild() {
			return leftChild;
		}

		public void setLeftChild(Entry leftChild) {
			this.leftChild = leftChild;
		}

		public Entry getRightChild() {
			return rightChild;
		}

		public void setRightChild(Entry rightChild) {
			this.rightChild = rightChild;
		}

		public Entry getParent() {
			return parent;
		}

		public void setParent(Entry parent) {
			this.parent = parent;
		}

		public Key getKey() {
			return key;
		}

		public Value getValue() {
			return value;
		}

		public void setValue(Value value) {
			this.value = value;
		}

	}

	@Override
	public void put(Key key, Value value) {
		if (root == null) {
			root = new Entry(key, value);
			return;
		}
		// find the right place
		Entry root = this.root;
		if (!(value instanceof Comparable<?>)) {
			return;
		}
		if (root.key == key) {
			root.setValue(value);
		}
		// FIXME: find the right place to insert
	}

	@Override
	public void remove(Key key) {
		// TODO Auto-generated method stub

	}

	@Override
	public Value get(Key key) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getSize() {
		return size;
	}

}
