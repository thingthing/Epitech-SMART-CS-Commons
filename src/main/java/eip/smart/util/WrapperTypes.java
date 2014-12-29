package eip.smart.util;

import java.util.HashSet;
import java.util.Set;

public class WrapperTypes {
	private static Set<Class<?>> getWrapperTypes() {
		Set<Class<?>> ret = new HashSet<>();
		ret.add(Boolean.class);
		ret.add(Character.class);
		ret.add(Byte.class);
		ret.add(Short.class);
		ret.add(Integer.class);
		ret.add(Long.class);
		ret.add(Float.class);
		ret.add(Double.class);
		ret.add(Void.class);
		return ret;
	}

	public static boolean isWrapperType(Class<?> clazz) {
		return WrapperTypes.WRAPPER_TYPES.contains(clazz);
	}

	private static final Set<Class<?>>	WRAPPER_TYPES	= WrapperTypes.getWrapperTypes();
}
