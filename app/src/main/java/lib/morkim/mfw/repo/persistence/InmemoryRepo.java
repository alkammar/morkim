package lib.morkim.mfw.repo.persistence;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.repo.Filter;
import lib.morkim.mfw.repo.gateway.AbstractGateway;

public abstract class InmemoryRepo<E extends Entity> extends AbstractGateway<E> {
	
	protected SparseArray<E> entities;

	public InmemoryRepo(MorkimApp morkimApp) {
		super(morkimApp);

		entities = new SparseArray<>();
	}

	@Override
	public void persist(Entity entity) {
		this.entities.put(entity.getLocalId(), (E) entity);;
	}

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
	
	public static <C> List<C> asList(SparseArray<C> sparseArray) {
	    
		if (sparseArray == null) return null;
	    
		List<C> arrayList = new ArrayList<C>(sparseArray.size());
	    
		for (int i = 0; i < sparseArray.size(); i++)
	        arrayList.add(sparseArray.valueAt(i));
	    
		return arrayList;
	}
}
