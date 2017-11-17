package org.opcfoundation.ua.core;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.EnumSet;

import org.junit.Test;

public class ServerCapabilityTest {
	
	@Test
	public void testGetDescription() throws Exception {
		String actual = ServerCapability.PLC.getDescription();
		String expected = "Supports the PLCopen information model.";
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetIdentifier() throws Exception {
		String actual = ServerCapability.AC.getIdentifier();
		String expected = "AC";
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetIdentifiersWithEnumSet() throws Exception {
		String[] expected = new String[] { "FDI", "FDIC" };
		String[] actual = ServerCapability.getIdentifiers(EnumSet.of(ServerCapability.FDI,
				ServerCapability.FDIC));
		assertTrue(Arrays.equals(expected, actual));
	}
	
	@Test
	public void testGetIdentifiersWithList() throws Exception {
		String[] expected = new String[] { "FDI", "FDIC" };
		String[] actual = ServerCapability.getIdentifiers(ServerCapability.FDI,
				ServerCapability.FDIC);
		assertTrue(Arrays.deepEquals(expected, actual));
	}
	
	@Test
	public void testGetIdentifiersWithNoInputArguments() throws Exception {
		String[] expected = new String[] { "NA" };
		String[] actual = ServerCapability.getIdentifiers();
		assertTrue(Arrays.equals(expected, actual));
	}
	
	@Test
	public void testGetSet() throws Exception {
		EnumSet<ServerCapability> expected = EnumSet.of(ServerCapability.HD, ServerCapability.ADI,
				ServerCapability.S95);
		EnumSet<ServerCapability> actual = ServerCapability.getSet("HD", "ADI", "S95");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetSetSpecialCase() throws Exception {
		EnumSet<ServerCapability> expected = EnumSet.of(ServerCapability.DA, ServerCapability.GDS,
				ServerCapability.PLC);
		EnumSet<ServerCapability> actual = ServerCapability.getSet("DA", "GDS", "NA", "PLC");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testValueOfId() throws Exception {
		ServerCapability expected = ServerCapability.HE;
		ServerCapability actual = ServerCapability.valueOfId("HE");
		assertEquals(expected, actual);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testValueOfIdIllegalArgument() throws Exception {
		ServerCapability actual = ServerCapability.valueOfId("A");
	}
	
	@Test
	public void testValueOfIdSpecialCase() throws Exception {
		ServerCapability actual = ServerCapability.valueOfId("NA");
		assertNull(actual);
	}
	
}
