package eip.smart.util;

public class Pair<A, B> {
	final private A	first;
	final private B	second;

	public Pair(A first, B second) {
		super();
		this.first = first;
		this.second = second;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Pair) {
			Pair<?, ?> otherPair = (Pair<?, ?>) other;
			return ((this.first == otherPair.first || (this.first != null && otherPair.first != null && this.first.equals(otherPair.first))) && (this.second == otherPair.second || (this.second != null && otherPair.second != null && this.second.equals(otherPair.second))));
		}

		return false;
	}

	public A getFirst() {
		return this.first;
	}

	public B getSecond() {
		return this.second;
	}

	@Override
	public int hashCode() {
		int hashFirst = this.first != null ? this.first.hashCode() : 0;
		int hashSecond = this.second != null ? this.second.hashCode() : 0;

		return (hashFirst + hashSecond) * hashSecond + hashFirst;
	}

	@Override
	public String toString() {
		return "(" + this.first + ", " + this.second + ")";
	}
}
