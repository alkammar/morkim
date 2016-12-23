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

    private T task;
    private Class<T> taskClass;

    public UseCaseCreator() {

    }

    public UseCaseCreator<T> create(Class<T> taskClass) {

        this.taskClass = taskClass;

        try {
            task = taskClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return this;
    }

    public UseCaseCreator<T> add(Class<T> taskClass) {

        this.taskClass = taskClass;

        try {
            task = taskClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return this;
    }

    public <d extends UseCaseDependencies> T with(d dependenciesImpl) {

        Map<Class, Field> fieldTypes = new HashMap<>();

        Class cls = taskClass;

        do {
            for (Field field : cls.getDeclaredFields()) {
                addAnnotatedField(fieldTypes, field, GenericsUtils.resolveActualTypeArgs(taskClass, cls));
            }
            cls = cls.getSuperclass();
        } while (!cls.isInstance(Object.class));

        Class<?> dependenciesImplClass = dependenciesImpl.getClass();

        while (!dependenciesImplClass.isInstance(Object.class)) {
            for (Method method : dependenciesImplClass.getDeclaredMethods()) {

                Field field = fieldTypes.get(method.getReturnType());
                if (field != null)
                    try {
                        field.setAccessible(true);
                        field.set(task, method.invoke(dependenciesImpl));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
            }

            dependenciesImplClass = dependenciesImplClass.getSuperclass();
        }

        return task;
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
