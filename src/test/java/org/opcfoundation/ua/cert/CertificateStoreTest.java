package org.opcfoundation.ua.cert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.transport.security.Cert;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.utils.CertificateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CertificateStoreTest {

	private static final Logger logger = LoggerFactory.getLogger(CertificateStoreTest.class);
	private static Cert cert;
	private static Cert cert2;

	@Test
	public void testCerts() throws Exception {
		CertificateStore store = mock(CertificateStore.class);
		Set<Cert> temp = new HashSet<Cert>();
		temp.add(cert);
		when(store.getTrustedCerts()).thenReturn(temp);
		
		DefaultCertificateValidator validator = new DefaultCertificateValidator(store);
		
		//this cert should be accepted because it is in trusted dir
		StatusCode code = validator.validateCertificate(cert);
		assertTrue(code.isGood());
		
		//this cert should not be accepted because it is not in trusted dir
		StatusCode code2 = validator.validateCertificate(cert2);
		assertTrue(!code2.isGood());
		assertEquals(StatusCodes.Bad_SecurityChecksFailed.intValue(), code2.getValue().intValue());
	}
	
	@BeforeClass
	public static void generateCertificates() throws Exception{
		logger.info("Generating in-memory test certificates...");
		String applicationUri = "urn:localhost:OPCUA:testapplication";
		String hostNames = "localhost";
		String commonName = "commonname";
		String organisation = "test organization";
		int validityTime = 356; //days
		
		//test cert 1
		KeyPair kp = CertificateUtils.createApplicationInstanceCertificate(commonName, organisation, applicationUri, validityTime, hostNames);
		cert = kp.getCertificate();
		
		//test cert 2
		applicationUri = "urn:localhost:OPCUA:testapplication2";
		KeyPair kp2 = CertificateUtils.createApplicationInstanceCertificate(commonName, organisation, applicationUri, validityTime, hostNames);
		cert2 = kp2.getCertificate();
		
	}
	
}
