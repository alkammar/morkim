package lib.morkim.mfw.util;

import android.support.annotation.NonNull;

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
	 * @return actual generic type arguments, must match the type parameters of the offspring class. If omitted, the
	 * type parameters will be used instead.
	 */
	public static <T> Type[] resolveActualTypeArgs(Class<? extends T> offspring, Class<T> base) {

		return resolveActualTypesInternal(offspring, base, null);
	}

	private static <T> Type[] resolveActualTypesInternal(Class<? extends T> offspring, Class<T> base, Type[] actualTypes) {

		Type[] resolvedTypes = new Type[0];

		Map<String, Type> mappedTypes = new HashMap<>();

		if (actualTypes == null) actualTypes = getDefaultTypes(base);

		if (base != null) {

			TypeVariable<? extends Class<? extends T>>[] offspringParameters = offspring.getTypeParameters();

			List<Type> ancestors = new LinkedList<>();
			if (offspring.getGenericSuperclass() != null) {
				ancestors.add(offspring.getGenericSuperclass());
			}

			Collections.addAll(ancestors, offspring.getGenericInterfaces());

			for (Type ancestor : ancestors) {

				if (!offspring.equals(base)) {

					if (offspringParameters.length == 0) {
						if (ancestor instanceof ParameterizedType) {
							Type[] parametrizedActualTypes = getRawTypes(((ParameterizedType) ancestor).getActualTypeArguments());
//                   actualTypes = new Type[parametrizedActualTypes.length];
//                   System.arraycopy(parametrizedActualTypes, 0, actualTypes, 0, parametrizedActualTypes.length);

							updateActualTypes(actualTypes, parametrizedActualTypes);
						}

						resolvedTypes = resolveActualTypesInternal(getRawType(ancestor), base, actualTypes);
					} else {

						for (TypeVariable typeVariable : offspringParameters)
							mappedTypes.put(typeVariable.getName(), typeVariable.getBounds()[0]);

						if (ancestor instanceof ParameterizedType) {
							ParameterizedType parametrizedGenericSuperClass = (ParameterizedType) ancestor;
							Type[] parametrizedGenericActualArguments = parametrizedGenericSuperClass.getActualTypeArguments();

							Type[] offspringTypes = new Type[parametrizedGenericActualArguments.length];

							if (actualTypes.length == 0)
								actualTypes = new Type[offspringTypes.length];

							for (int i = 0; i < parametrizedGenericActualArguments.length; i++) {
								Type mappedType = mappedTypes.get(parametrizedGenericActualArguments[i].toString());
								if (mappedType != null) {
									mappedType = findInActualTypes(actualTypes, mappedType);
									offspringTypes[i] = mappedType;
								} else {
									offspringTypes[i] = getRawType(parametrizedGenericActualArguments[i]);
								}
							}

							actualTypes = updateActualTypes(actualTypes, offspringTypes);

							resolvedTypes = resolveActualTypesInternal(getRawType(ancestor),  base, actualTypes);
						}
					}
				}

				if (resolvedTypes.length == 0) {
					if (offspringParameters.length > 0 && actualTypes.length == 0) {
						actualTypes = new Type[offspringParameters.length];
						for (int i = 0; i < offspringParameters.length; i++)
							actualTypes[i] = getRawType(offspringParameters[i].getBounds()[0]);
					}

					resolvedTypes = new Type[actualTypes.length];

					if (actualTypes.length > 0) {
						System.arraycopy(actualTypes, 0, resolvedTypes, 0, actualTypes.length);
					}
				} else {
					actualTypes = new Type[resolvedTypes.length];
					System.arraycopy(resolvedTypes, 0, actualTypes, 0, resolvedTypes.length);
				}
			}
		}

		return resolvedTypes;
	}

	private static Type[] getDefaultTypes(Class base) {

		Type[] types;

		if (base != null) {
			TypeVariable[] typeVariables = base.getTypeParameters();
			types = new Type[typeVariables.length];

			for (int i = 0; i < typeVariables.length; i++) {
				types[i] = typeVariables[i].getBounds()[0];
			}
		} else {
			types = new Type[0];
		}

		return types;
	}

	@NonNull
	private static Type[] updateActualTypes(Type[] actualTypes, Type[] offspringTypes) {

		for (Type offspringType : offspringTypes) {
			for (int j = 0; j < actualTypes.length; j++) {
				if (getRawType(actualTypes[j]).isAssignableFrom(getRawType(offspringType))) {
					actualTypes[j] = offspringType;
					break;
				}
			}
		}


		return actualTypes;
	}

	private static Type [] getRawTypes(Type [] types) {

		Type [] resolvedTypes = new Type[types.length];

		for (int i = 0; i < types.length; i++)
			resolvedTypes[i] = getRawType(types[i]);

		return resolvedTypes;
	}

	private static Type findInActualTypes(Type[] actualTypes, Type mappedType) {

		Class<?> raw = getRawType(mappedType);

		for (Type actualType : actualTypes) {
			Class actualRawType = getRawType(actualType);
			if (actualType != null && raw.isAssignableFrom(actualRawType)) {
				mappedType = actualRawType;
				break;
			}
		}

		return getRawType(mappedType);
	}

	public static Class getRawType(Type type) {
		return (Class) (type instanceof ParameterizedType ? ((ParameterizedType) type).getRawType() : type);
	}

}