package lib.morkim.mfw.app;

import lib.morkim.mfw.repo.Repository;

public interface RepoAccess {

	public abstract Repository getRepos();
	public abstract void setRepos(Repository repo);

}