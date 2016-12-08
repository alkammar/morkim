package lib.morkim.mfw.usecase;

import lib.morkim.mfw.app.MorkimApp;

public interface TaskDependencies<A extends MorkimApp, T extends MorkimTask> {

	A getContext();
}
