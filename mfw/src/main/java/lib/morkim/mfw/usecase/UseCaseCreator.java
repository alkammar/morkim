package lib.morkim.mfw.usecase;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

import lib.morkim.mfw.util.GenericsUtils;

public class UseCaseCreator<T extends UseCase> {

    private T useCase;
    private Class<T> useCaseClass;

    public UseCaseCreator() {

    }

    public UseCaseCreator<T> create(Class<T> useCaseClass) {

        this.useCaseClass = useCaseClass;

        try {
            useCase = useCaseClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return this;
    }

    public UseCaseCreator<T> add(Class<T> taskClass) {

        this.useCaseClass = taskClass;

        try {
            useCase = taskClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return this;
    }

    public <d extends UseCaseDependencies> T with(d dependenciesImpl) {

        Map<Class, Field> fieldTypes = new HashMap<>();

        Class ucc = useCaseClass;
        Class dic = dependenciesImpl.getClass();

        do {
            Type[] resolvedTaskClassTypes = GenericsUtils.resolveActualTypeArgs(useCaseClass, ucc);

            for (Field field : ucc.getDeclaredFields()) {
                addAnnotatedField(fieldTypes, field, resolvedTaskClassTypes);
            }
            ucc = ucc.getSuperclass();
        } while (!ucc.isInstance(Object.class));

        while (!dic.isInstance(Object.class)) {

            Type[] resolvedTypes = GenericsUtils.resolveActualTypeArgs(dependenciesImpl.getClass(), dic);

            for (Method method : dic.getDeclaredMethods()) {

                TypeVariable[] dependenciesTypeParams = dic.getTypeParameters();

                Class<?> returnType = method.getReturnType();

                for (int i = 0; i < dependenciesTypeParams.length; i++) {
                    if (dependenciesTypeParams[i].equals(method.getGenericReturnType())) {
                        returnType = (Class<?>) resolvedTypes[i];
                        break;
                    }
                }

                Field field = fieldTypes.get(returnType);
                if (field != null)
                    try {
                        field.setAccessible(true);
                        field.set(useCase, method.invoke(dependenciesImpl));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
            }

            dic = dic.getSuperclass();
        }

        return useCase;
    }

    private boolean isFieldGeneric(Field field) {
        return !field.getGenericType().equals(field.getType());
    }

    private void addAnnotatedField(Map<Class, Field> fieldTypes, Field field, Type[] types) {

        if (field.isAnnotationPresent(TaskDependency.class)) {
            Type fieldType = field.getType();

            TypeVariable<? extends Class<?>>[] typeParams;
            if (isFieldGeneric(field)) {
                typeParams = field.getDeclaringClass().getTypeParameters();
                for (int i = 0; i < typeParams.length; i++)
                    if (typeParams[i].equals(field.getGenericType())) {
                        fieldType = types[i];
                        break;
                    }
            }

            Class<?> cls = fieldType instanceof ParameterizedType ?
                    (Class<?>) ((ParameterizedType) fieldType).getRawType() :
                    (Class<?>) fieldType;

            fieldTypes.put(cls, field);
        }
    }
}
