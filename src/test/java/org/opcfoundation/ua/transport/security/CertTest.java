package org.opcfoundation.ua.transport.security;

import static org.junit.Assert.assertArrayEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opcfoundation.ua.utils.CertificateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.Arrays;

public class CertTest {

	private static final Logger logger = LoggerFactory
			.getLogger(CertTest.class);

	private static Cert cert;
	private static Cert cert2;

	@BeforeClass
	public static void generateCertificates() throws Exception {
		logger.info("Generating in-memory test certificates...");
		String applicationUri = "urn:localhost:OPCUA:testapplication";
		String hostNames = "localhost";
		String commonName = "commonname";
		String organisation = "test organization";
		int validityTime = 356; // days

		// test cert 1
		KeyPair kp = CertificateUtils.createApplicationInstanceCertificate(
				commonName, organisation, applicationUri, validityTime,
				hostNames);
		cert = kp.getCertificate();

		// test cert 2
		applicationUri = "urn:localhost:OPCUA:testapplication2";
		KeyPair kp2 = CertificateUtils.createApplicationInstanceCertificate(
				commonName, organisation, applicationUri, validityTime,
				hostNames);
		cert2 = kp2.getCertificate();

	}

	@Test
	public void thumbprintCalculationInCertChain() throws Exception {
		/*
		 * GH#145
		 * 
		 * Purpose of this test is to check that the thumprint of a cert is
		 * calculated correctly when there are more than one cert in the encoded
		 * form (see specification v.1.04 Part 6 section 6.2.3
		 * "Certificate Chains"
		 */

		byte[] data1 = cert.getEncoded();
		byte[] data2 = cert2.getEncoded();
		
		// Sidenote, the 2 certs are not a chain, but this works for the purpose of this test
		byte[] dataCombined = Arrays.concatenate(data1, data2);

		// if calculation is done correctly, the thumbprint of the combined
		// should be the one of the first, i.e. data1
		Cert combined = new Cert(dataCombined);
		assertArrayEquals(cert.getEncodedThumbprint(), combined.getEncodedThumbprint());

	}

}
