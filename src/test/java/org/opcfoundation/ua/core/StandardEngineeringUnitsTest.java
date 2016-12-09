package org.opcfoundation.ua.core;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class StandardEngineeringUnitsTest {

	@Test
	public void testPrint() throws Exception {
		print(StandardEngineeringUnits.AIR_DRY_TON);
		print(StandardEngineeringUnits.TONNE_PER_CUBIC_METRE);

	}
	
	@Test
	public void testFindWithCommonCode() throws Exception {
		EUInformation eu = StandardEngineeringUnits.getByCommonCode("D41");
		assertSame(StandardEngineeringUnits.TONNE_PER_CUBIC_METRE, eu);		
	}
	
	@Test
	public void testFindWithUnitId() throws Exception {
		EUInformation eu = StandardEngineeringUnits.getByUnitId(4534840);
		assertSame(StandardEngineeringUnits.AIR_DRY_TON, eu);
	}
	
	private static void print(EUInformation eu){
		System.out.println(eu.getDescription());
		System.out.println(eu.getDisplayName());
	}
	

}
