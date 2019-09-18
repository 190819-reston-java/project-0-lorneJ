package com.revature.repository;

import java.util.List;

import com.revature.model.BAcct;

//Data Access Object

public interface BAcctDAO {
	
	BAcct getBAcct(int id);
	
	BAcct getBAcct(String name);
	
	List<BAcct> getBAcct();
	
	boolean createBAcct(BAcct b);
	
	boolean updateBAcct(BAcct b);
}
