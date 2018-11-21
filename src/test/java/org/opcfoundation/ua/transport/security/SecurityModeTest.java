package org.opcfoundation.ua.transport.security;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.opcfoundation.ua.core.MessageSecurityMode;

public class SecurityModeTest {

	@Test
	public void combinationsEmptyAndNull() throws Exception {
		assertTrue(SecurityMode.combinations(null, null).isEmpty());
		assertTrue(SecurityMode.combinations(EnumSet.of(MessageSecurityMode.None), null).isEmpty());
		assertTrue(SecurityMode.combinations(null, EnumSet.of(SecurityPolicy.NONE)).isEmpty());
		assertTrue(SecurityMode.combinations(new HashSet<MessageSecurityMode>(), new HashSet<SecurityPolicy>()).isEmpty());
	}
	
	@Test
	public void combinationsNonePairsOnlyWithNone() throws Exception {
		assertExactContentsEquals(SecurityMode.NONE, SecurityMode.combinations(EnumSet.of(MessageSecurityMode.None, MessageSecurityMode.Sign), EnumSet.of(SecurityPolicy.NONE)));
		assertExactContentsEquals(SecurityMode.NONE, SecurityMode.combinations(EnumSet.of(MessageSecurityMode.None), EnumSet.allOf(SecurityPolicy.class)));
	}
	
	@Test
	public void combinations() throws Exception {
		Set<SecurityMode> expected = new HashSet<SecurityMode>();
		expected.add(SecurityMode.NONE);
		expected.add(SecurityMode.BASIC128RSA15_SIGN);
		expected.add(SecurityMode.BASIC128RSA15_SIGN_ENCRYPT);
		expected.add(SecurityMode.BASIC256_SIGN);
		expected.add(SecurityMode.BASIC256_SIGN_ENCRYPT);
		
		Set<SecurityMode> actual = SecurityMode.combinations(EnumSet.of(MessageSecurityMode.None, MessageSecurityMode.Sign, MessageSecurityMode.SignAndEncrypt), 
				EnumSet.of(SecurityPolicy.NONE, SecurityPolicy.BASIC128RSA15, SecurityPolicy.BASIC256));
		System.out.println(actual);
		assertExactContentsEquals(expected, actual);
	}

	private <T> void assertExactContentsEquals(T element,
			Collection<T> collectionThatShouldContainOnlyElement) {
		assertEquals(1, collectionThatShouldContainOnlyElement.size());
		assertTrue(collectionThatShouldContainOnlyElement.contains(element));
	}
	
	private <T> void assertExactContentsEquals(Collection<T> elements,
			Collection<T> collectionThatShouldContainOnlyElements) {
		//Note that this could be done nicer, but will work for now..
		assertEquals(elements.size(), collectionThatShouldContainOnlyElements.size());
		for(T element : elements) {
			assertTrue(collectionThatShouldContainOnlyElements.contains(element));
		}
	}
}
