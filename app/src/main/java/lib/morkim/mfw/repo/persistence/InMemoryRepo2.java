package lib.morkim.mfw.repo.persistence;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.repo.Filter;
import lib.morkim.mfw.repo.gateway.AbstractGateway;

public abstract class InMemoryRepo2<E extends Entity> extends AbstractGateway<E> {
	
	private SparseArray<E> entities;

	public InMemoryRepo2() {
		entities = new SparseArray<>();
	}

	@Override
	public void persist(E entity) {
		this.entities.put(entity.getLocalId(), copy(entity));
	}

	protected abstract E copy(E entity);

	@Override
	public E retrieve() {
		return entities.get(0);
	}

	@Override
	public E retrieve(int id) {
		return entities.get(id);
	}

	@Override
	public List<E> retrieveAll() {
		return asList(entities);
	}

	@Override
	public List<E> retrieve(Filter filter) {
		return asList(entities);
	}
	
	private static <C> List<C> asList(SparseArray<C> sparseArray) {
	    
		if (sparseArray == null) return null;
	    
		List<C> arrayList = new ArrayList<>(sparseArray.size());
	    
		for (int i = 0; i < sparseArray.size(); i++)
	        arrayList.add(sparseArray.valueAt(i));
	    
		return arrayList;
	}
}
