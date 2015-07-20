package eip.smart.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public class Pair<A, B> implements JsonSerializable {

	final private A	key;

	final private B	value;

	public Pair(A first, B second) {
		super();
		this.key = first;
		this.value = second;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Pair) {
			Pair<?, ?> otherPair = (Pair<?, ?>) other;
			return ((this.key == otherPair.key || (this.key != null && otherPair.key != null && this.key.equals(otherPair.key))) && (this.value == otherPair.value || (this.value != null && otherPair.value != null && this.value.equals(otherPair.value))));
		}

		return false;
	}

	public A getKey() {
		return this.key;
	}

	public B getValue() {
		return this.value;
	}

	@Override
	public int hashCode() {
		int hashFirst = this.key != null ? this.key.hashCode() : 0;
		int hashSecond = this.value != null ? this.value.hashCode() : 0;

		return (hashFirst + hashSecond) * hashSecond + hashFirst;
	}

	@Override
	public void serialize(JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeStartObject();
		jgen.writeFieldName(this.key.toString());
		jgen.writeString(this.value.toString());
		jgen.writeEndObject();
	}

	@Override
	public void serializeWithType(JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException, JsonProcessingException {}

	@Override
	public String toString() {
		return "(" + this.key + ", " + this.value + ")";
	}
}
