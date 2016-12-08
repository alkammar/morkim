package lib.morkim.mfw.util;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GenericsUtils {

	/**
	 * Resolves the actual generic type arguments for a base class, as viewed from a subclass or implementation.
	 *
	 * @param <T> base type
	 * @param offspring class or interface subclassing or extending the base type
	 * @param base base class
	 * @param actualArgs the actual type arguments passed to the offspring class
	 * @return actual generic type arguments, must match the type parameters of the offspring class. If omitted, the
	 * type parameters will be used instead.
	 */
	public static <T> Type[] resolveActualTypeArgs (Class<? extends T> offspring, Class<T> base, Type... actualArgs) {

		assert offspring != null;
		assert base != null;
		assert actualArgs.length == 0 || actualArgs.length == offspring.getTypeParameters().length;

		//  If actual types are omitted, the type parameters will be used instead.
		if (actualArgs.length == 0) {
			actualArgs = offspring.getTypeParameters();
		}
		// map type parameters into the actual types
		Map<String, Type> typeVariables = new HashMap<String, Type>();
		for (int i = 0; i < actualArgs.length; i++) {
			TypeVariable<?> typeVariable = offspring.getTypeParameters()[i];
			typeVariables.put(typeVariable.getName(), actualArgs[i]);
		}

		// Find direct ancestors (superclass, interfaces)
		List<Type> ancestors = new LinkedList<>();
		if (offspring.getGenericSuperclass() != null) {
			ancestors.add(offspring.getGenericSuperclass());
		}

		Collections.addAll(ancestors, offspring.getGenericInterfaces());

		// Recurse into ancestors (superclass, interfaces)
		for (Type type : ancestors) {
			if (type instanceof Class<?>) {
				// ancestor is non-parameterized. Recurse only if it matches the base class.
				Class<?> ancestorClass = (Class<?>) type;
				if (base.isAssignableFrom(ancestorClass)) {
					Type[] result = resolveActualTypeArgs((Class<? extends T>) ancestorClass, base);
					if (result != null) {
						return result;
					}
				}
			}
			if (type instanceof ParameterizedType) {
				// ancestor is parameterized. Recurse only if the raw type matches the base class.
				ParameterizedType parameterizedType = (ParameterizedType) type;
				Type rawType = parameterizedType.getRawType();
				if (rawType instanceof Class<?>) {
					Class<?> rawTypeClass = (Class<?>) rawType;
					if (base.isAssignableFrom(rawTypeClass)) {

						// loop through all type arguments and replace type variables with the actually known types
						List<Type> resolvedTypes = new LinkedList<Type>();
						for (Type t : parameterizedType.getActualTypeArguments()) {
							if (t instanceof TypeVariable<?>) {
								Type resolvedType = typeVariables.get(((TypeVariable<?>) t).getName());
								resolvedTypes.add(resolvedType != null ? resolvedType : t);
							} else {
								resolvedTypes.add(t);
							}
						}

						Type[] result = resolveActualTypeArgs((Class<? extends T>) rawTypeClass, base, resolvedTypes.toArray(new Type[] {}));
						if (result != null) {
							return result;
						}
					}
				}
			}
		}

		// we have a result if we reached the base class.
		return offspring.equals(base) ? actualArgs : null;
	}

}
