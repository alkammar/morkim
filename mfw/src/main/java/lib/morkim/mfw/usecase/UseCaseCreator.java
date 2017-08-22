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

    public UseCaseCreator<T> add(Class<T> useCaseClass) {

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

    public <d extends UseCaseDependencies> T with(d dependenciesImpl) {

        Map<Class, Field> fieldTypes = new HashMap<>();

        Class cls = useCaseClass;

        do {
            for (Field field : cls.getDeclaredFields()) {
                addAnnotatedField(fieldTypes, field, GenericsUtils.resolveActualTypeArgs(useCaseClass, cls));
            }
            cls = cls.getSuperclass();
        } while (!cls.isInstance(Object.class));

        for (Method method : dependenciesImpl.getClass().getDeclaredMethods()) {

            Field field = fieldTypes.get(method.getReturnType());
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
