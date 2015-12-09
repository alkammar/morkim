package lib.morkim.mfw.domain;

import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

import lib.morkim.mfw.repo.Repository;
import lib.morkim.mfw.repo.gateway.GatewayPersistException;
import lib.morkim.mfw.repo.gateway.GatewayRetrieveException;

public abstract class Entity extends Observable {

	public static final String ID = "id";

	public static final int EVENT_CHANGE = 0;

	public static final int NO_LOCAL_ID = -1;
	public static final int NO_PERSISTANCE = -1;

	private UUID sysId;
	private int localId = NO_LOCAL_ID;

	public Entity() {

	}

	public Entity(Entity entity) {

		set(entity);
	}

	public void set(Entity entity) {

		if (entity != null) {
			if (entity.localId != NO_LOCAL_ID)
				this.localId = entity.localId;
		}
	}

	public void merge(Entity entity) {

		if (entity != null) {
			if (this.localId == NO_LOCAL_ID)
				this.localId = entity.localId;
		}
	}

	public void setSysId(UUID id) {
		this.sysId = id;
	}

	/**
	 * Get system id
	 * 
	 * @return
	 */
	public UUID getSysId() {
		return sysId;
	}

	public int getLocalId() {
		return localId;
	}

	public void setLocalId(int id) {
		this.localId = id;
	}

	@Override
	public void notifyObservers() {
		
		synchronized (this) {
			super.notifyObservers();
		}
	}

	@Override
	public synchronized void deleteObserver(Observer observer) {

		synchronized (this) {
			super.deleteObserver(observer);
		}
	}

	protected int persistenceId() {
		return NO_PERSISTANCE;
	}

	/**
	 * Saves this entity to persistence repository
	 * @throws GatewayPersistException
	 */
	public void save(Repository repos) throws GatewayPersistException {
		repos.get(this.getClass()).persist(this);
	}

	/**
	 * Restores this entity's state from persistence. Will do nothing if
	 * {@code localId} = {@code NO_LOCAL_ID} or it does not exist in persistence
	 * @throws GatewayRetrieveException 
	 */
	public void load(Repository repos) throws GatewayRetrieveException {

		if (localId != NO_LOCAL_ID) {

			Object data = repos.get(this.getClass()).retrieve(localId);
			set((Entity) data);
		}
	}
}
