package com.yelpoauth.app.android.test.model;

import java.util.List;

import com.yelpoauth.app.android.models.Query;

import junit.framework.TestCase;

public class QuerySpec extends TestCase {
	
	/**
	 * test store query to ensure it is storing queries properly in the db
	 */
	public void testStoreQuery(){
		String queryString = "test";
		
		//store query
		Query.storeQuery(queryString);	
		
		//get last query, should be test
		Query lastItem = Query.getLastStoredItem();
		String lastItemQuery = lastItem.getQuery();
		
		assertTrue(lastItemQuery.equals(queryString));
	}
	
	/**
	 * test to ensure the following methods are returning correctly
	 * Query.getAllQueries()
	 * Query.populateQueryList(int)
	 * Query.deleteAll()
	 * 
	 */
	public void testPopulateAndGetQueries(){
		int limit = 50;
		
		//clear query list so we can start fresh
		Query.deleteAll();
		
		//populate query list with 50 random queries to test with
		Query.populateQueryList(limit);
		
		//get full Query list to verify it was populated correctly
		List<Query> testQueryList = Query.getAllQueries();
		
		int queryListLength = testQueryList.size();
				
		assertEquals(limit, queryListLength);
	}
	
	/**
	 * test to insure delete all queries is properly doing so
	 */
	public void testDeleteQueryHistory(){
		Query.deleteAll();
		int queryListLength = Query.getAllQueries().size();
		assertEquals(queryListLength, 0);
	}
	
	
}
