/* Copyright (c) 1996-2015, OPC Foundation. All rights reserved.
   The source code in this file is covered under a dual-license scenario:
     - RCL: for OPC Foundation members in good-standing
     - GPL V2: everybody else
   RCL license terms accompanied with this source code. See http://opcfoundation.org/License/RCL/1.00/
   GNU General Public License as published by the Free Software Foundation;
   version 2 of the License are accompanied with this source code. See http://opcfoundation.org/License/GPLv2
   This source code is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
*/

package org.opcfoundation.ua.cert;

import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.EnumSet;
import java.util.Set;

import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.transport.security.Cert;
import org.opcfoundation.ua.transport.security.CertificateValidator;
import org.opcfoundation.ua.utils.CertificateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A certificate validator implementation.
 */
public class DefaultCertificateValidator implements CertificateValidator {

	private static final Logger logger = LoggerFactory.getLogger(DefaultCertificateValidator.class);
	private static final String INVALID_URI_ERROR = "invalid URI name:";
	
	private volatile DefaultCertificateValidatorListener validationListener;

	private final CertificateStore store;
	
	public DefaultCertificateValidator(CertificateStore certificateStore){
		this.store = certificateStore;
	}
	
	/**
	 * @return the certificateStore
	 */
	public CertificateStore getCertificateStore() {
		return store;
	}

	/**
	 *
	 * @return the validationListener
	 */
	public DefaultCertificateValidatorListener getValidationListener() {
		return validationListener;
	}

	/**
	 * Set a validationListener to use, if this validator wants to reject the
	 * certificate.
	 * <p>
	 * Use the validation listener to react to a failed validation result and
	 * provide additional custom handling. For example to enable prompting the
	 * user if he wants to trust a certificate which is not trusted otherwise.
	 *
	 * @param validationListener the listener to set
	 */
	public void setValidationListener(DefaultCertificateValidatorListener validationListener) {
		this.validationListener = validationListener;
	} 

	@Override
	public StatusCode validateCertificate(ApplicationDescription applicationDescription, Cert cert) {
		try{
			logger.debug("validateCertificate: applicationDescription={}", applicationDescription);
			logger.debug("cert={}", cert);
	
			boolean isRevoked = isRevoked(cert);
			logger.debug("isRevoked={}", isRevoked);
			if (isRevoked)
				return new StatusCode(StatusCodes.Bad_CertificateRevoked);
	
			// Default result if not revoked
			StatusCode result = StatusCode.GOOD;
			EnumSet<CertificateCheck> passedChecks = EnumSet.noneOf(CertificateCheck.class);
			final Set<Cert> trustedCerts = store.getTrustedCerts();
			
			if (trustedCerts != null && trustedCerts.contains(cert)) {
				logger.debug("trusted=yes");
				passedChecks.add(CertificateCheck.Trusted);
			}
			logger.debug("trusted={}", passedChecks.contains(CertificateCheck.Trusted));
	
			final X509Certificate certificate = cert.getCertificate();
			try {
				certificate.checkValidity();
				logger.debug("valid=yes");
				passedChecks.add(CertificateCheck.Validity);
			} catch (CertificateExpiredException e) {
				// Did not pass
			} catch (CertificateNotYetValidException e) {
				// Did not pass
			}
			logger.debug("valid={}", passedChecks.contains(CertificateCheck.Validity));
	
			// Check the signature
	
			try {
				// self-signed?
				certificate.verify(certificate.getPublicKey());
				logger.debug("signature=yes");
				logger.debug("self-signed=yes");
				passedChecks.add(CertificateCheck.Signature);
				passedChecks.add(CertificateCheck.SelfSigned);
			} catch (GeneralSecurityException e) {
				// NOT self signed
				// Is it signed by a trusted signer?
				boolean issuerFound = false;
				for (Cert c : trustedCerts) {
					try {
						PublicKey pkey = c.getCertificate().getPublicKey();
						certificate.verify(pkey);
						issuerFound = true;
						StatusCode issuerResult = validateCertificate(c);
						if (issuerResult.isStatusCode(
								StatusCodes.Bad_CertificateRevoked))
							result = new StatusCode(
									StatusCodes.Bad_CertificateIssuerRevoked);
						else if (issuerResult.isStatusCode(
								StatusCodes.Bad_CertificateTimeInvalid))
							result = new StatusCode(
									StatusCodes.Bad_CertificateIssuerTimeInvalid);
						else if (issuerResult.isStatusCode(
								StatusCodes.Bad_CertificateChainIncomplete)
								|| issuerResult.isStatusCode(
										StatusCodes.Bad_CertificateIssuerRevoked)
								|| issuerResult.isStatusCode(
										StatusCodes.Bad_CertificateIssuerTimeInvalid))
							result = issuerResult;
						else if (issuerResult.isNotGood())
							result = new StatusCode(
									StatusCodes.Bad_CertificateInvalid);
					} catch (GeneralSecurityException e1) {
						continue;
					}
				}
				if (!issuerFound)
					result = new StatusCode(
							StatusCodes.Bad_CertificateChainIncomplete);
				if (result.isNotGood()) {
					store.addCertificate(ValidationResult.Reject, cert);
					return result;
				}
				logger.debug("signature=yes");
				passedChecks.add(CertificateCheck.Signature);
				passedChecks.add(CertificateCheck.Trusted);
			}
	
			logger.debug("signature={}", passedChecks.contains(CertificateCheck.Signature));
			logger.debug("self-signed={}", passedChecks.contains(CertificateCheck.SelfSigned));
	
			// If applicationUri is defined, check that it is present and valid in
			// the certificate
			String applicationUriOfApplicationDescription = (applicationDescription == null) ? null
					: applicationDescription.getApplicationUri();
			boolean isUriOk = (applicationUriOfApplicationDescription == null);
			if (!isUriOk)
				try {
					String applicationUriOfCertificate = CertificateUtils.getApplicationUriOfCertificate(certificate);
					if (applicationUriOfCertificate.equals(applicationUriOfApplicationDescription))
						isUriOk = true;
	
				} catch (CertificateParsingException e) {
					// Catch invalid URI exceptions
					if (e.getCause().getMessage().contains(INVALID_URI_ERROR)) {
						// Reason for exception was the URI
						String[] causeSplit = e.getCause().getMessage().split(INVALID_URI_ERROR);
						if ((causeSplit.length == 2) && causeSplit[1].equals(applicationUriOfApplicationDescription)) {
							// The invalid URI matches the one in the application
							// description, accept it (the fact that the URI is
							// invalid can be checked from the flag UriValid)
							logger.warn("The provided certificate contains an invalid ApplicationURI: {}", causeSplit[1]);
							passedChecks.add(CertificateCheck.Uri);
						} else
							// The invalid URI is not a match
							logger.warn("The provided certificate does not define the ApplicationURI", e);
						// return new StatusCode(
						// StatusCodes.Bad_CertificateInvalid);
					} else
						// Reason for error was not the URI
						logger.warn("The provided certificate has an invalid SubjectAlternativeNames field", e);
					// if (logger.isDebugEnabled()) logger.debug(e);
					// return new StatusCode(StatusCodes.Bad_CertificateInvalid);
				}
			if (isUriOk) {
				passedChecks.add(CertificateCheck.Uri);
				passedChecks.add(CertificateCheck.UriValid);
			}
			logger.debug("uri={}", passedChecks.contains(CertificateCheck.Uri));
			logger.debug("uriValid={}", passedChecks.contains(CertificateCheck.UriValid));
	
			// Do we trust or reject the certificate - just once, or permanently
			// If permanently, add the certificate to the appropriate directory
			ValidationResult action = checkValidationAction(cert, applicationDescription, passedChecks);
			logger.debug("action={}", action);

			switch (action) {
			case AcceptPermanently:
				result = StatusCode.GOOD;
				store.addCertificate(ValidationResult.AcceptPermanently, cert);
				break;
			case AcceptOnce:
				result = StatusCode.GOOD;
				store.addCertificate(ValidationResult.AcceptOnce, cert);
				break;
			case Reject:
				//see Specification 1.02.2 errata as why these codes are used
				if (!passedChecks.contains(CertificateCheck.Trusted))
					result = new StatusCode(StatusCodes.Bad_SecurityChecksFailed);
				else if (!passedChecks.contains(CertificateCheck.Signature))
					result = new StatusCode(StatusCodes.Bad_SecurityChecksFailed);
				else if (!passedChecks.contains(CertificateCheck.Validity))
					result = new StatusCode(StatusCodes.Bad_CertificateTimeInvalid);
				else if (!passedChecks.contains(CertificateCheck.Uri))
					result = new StatusCode(StatusCodes.Bad_CertificateUriInvalid);
				else {
					//This should not happen in normal scenarios, but we cannot
					//return Good statuscode if the action is Reject
					logger.warn("Rejected a certificate which did contain passedchecks: {}", passedChecks);
					result = new StatusCode(StatusCodes.Bad_SecurityChecksFailed);
				}
				
				store.addCertificate(ValidationResult.Reject, cert);
				break;
			}
			return result;
		}catch(RuntimeException e){
			logger.error("error while validating certificates",e);
			return new StatusCode(StatusCodes.Bad_InternalError);
		}
	}

	private boolean isRevoked(Cert cert) {
		Set<X509CRL> crls = store.getRevocationLists();
		for(X509CRL crl : crls){
			if(crl.isRevoked(cert.getCertificate())){
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.opcfoundation.ua.transport.security.CertificateValidator#
	 * validateCertificate(org.opcfoundation.ua.transport.security.Cert)
	 */
	@Override
	public StatusCode validateCertificate(Cert c) {
		logger.debug("validateCertificate: Cert={}", c);
		if (c == null)
			return StatusCode.GOOD;
		return validateCertificate(null, c);
	}

	/**
	 * @param certificate
	 *            the certificate
	 * @param applicationDescription
	 * @param passedChecks
	 *            the certificate checks that were OK
	 * @return how to handle the certificate: trust or reject; once or
	 *         permanently?
	 */
	private ValidationResult checkValidationAction(Cert certificate, ApplicationDescription applicationDescription,
			EnumSet<CertificateCheck> passedChecks) {
		// Multithreading safeguard
		DefaultCertificateValidatorListener listener = validationListener;
		if (listener != null)
			return listener.onValidate(certificate, applicationDescription, passedChecks);
		// Accept if all of the "important" checks passed
		if (passedChecks.containsAll(CertificateCheck.COMPULSORY))
			return ValidationResult.AcceptPermanently;
		return ValidationResult.Reject;
	}

}
