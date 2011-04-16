package com.mashery.examples.api.server;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public class Persistence {
	
    private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private Persistence() {
    	super();
    }

    public static PersistenceManagerFactory getPMF() {
        return pmfInstance;
    }
}