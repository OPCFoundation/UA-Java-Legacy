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

package org.opcfoundation.ua.utils;

import java.lang.reflect.Array;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.AnonymousIdentityToken;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.IssuedIdentityToken;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.core.SignatureData;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.core.UserIdentityToken;
import org.opcfoundation.ua.core.UserNameIdentityToken;
import org.opcfoundation.ua.core.UserTokenPolicy;
import org.opcfoundation.ua.core.UserTokenType;
import org.opcfoundation.ua.core.X509IdentityToken;
import org.opcfoundation.ua.encoding.binary.BinaryEncoder;
import org.opcfoundation.ua.transport.UriUtil;
import org.opcfoundation.ua.transport.security.Cert;
import org.opcfoundation.ua.transport.security.SecurityAlgorithm;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.utils.bytebuffer.ByteBufferUtils;


/**
 * Discovery client enumerates endpoints.
 * Current version supports only opc.tcp protocol.
 */
public class EndpointUtil {

	private static Logger logger = LoggerFactory.getLogger(EndpointUtil.class);

	/**
	 * <p>select.</p>
	 *
	 * @param endpoints an array of {@link org.opcfoundation.ua.core.EndpointDescription} objects.
	 * @param url a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.core.EndpointDescription} object.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public static EndpointDescription select(EndpointDescription[] endpoints, String url)
	throws ServiceResultException
	{
		// Use best endpoint of the same uri
		EndpointDescription[] endpointsOfTheSameUri = EndpointUtil.select(endpoints, url, null, null, null, null);
		if ( endpointsOfTheSameUri.length>0 ) {
			return select(endpointsOfTheSameUri);
		}
			
		// Use best endpoint of the same scheme
		String scheme = UriUtil.getTransportProtocol( url );
		EndpointDescription[] endpointsOfTheSameScheme = EndpointUtil.select(endpoints, null, scheme, null, null, null);
		if ( endpointsOfTheSameScheme.length>0 ) {
			return select(endpointsOfTheSameScheme);
		}
		
		// Just choose one endpoint
		return select(endpoints);
	}
	
	/**
	 * Select an endpoint that is supported by the stack and has
	 * the highest security level.
	 *
	 * @param endpoints an array of {@link org.opcfoundation.ua.core.EndpointDescription} objects.
	 * @return encrypted endpoint
	 * @throws org.opcfoundation.ua.common.ServiceResultException error
	 */
	public static EndpointDescription select(EndpointDescription[] endpoints)
	throws ServiceResultException
	{
		// Filter out all but opc.tcp protocol endpoints
		exit: {
			EndpointDescription[] tcpEndpoints = EndpointUtil.selectByProtocol(endpoints, "opc.tcp");
			// Filter out all but Signed & Encrypted endpoints
			//tcpEndpoints = EndpointUtil.selectByMessageSecurityMode(tcpEndpoints, MessageSecurityMode.SignAndEncrypt);
			// No suitable endpoint was found
			if ( tcpEndpoints.length == 0 ) break exit;
			// Sort endpoints by security level. The lowest level at the beginning, the highest at the end of the array
			tcpEndpoints = EndpointUtil.sortBySecurityLevel(tcpEndpoints);			
			// Choose one endpoint
			return tcpEndpoints[ tcpEndpoints.length-1 ];
		}

		// Find Https endpoints 
		exit: {
			EndpointDescription[] httpsEndpoints = EndpointUtil.selectByProtocol(endpoints, "https");
			// No suitable endpoint was found
			if ( httpsEndpoints.length == 0 ) break exit;
			// Choose one endpoint
			return httpsEndpoints[ 0 ];
		}
		
		throw new ServiceResultException("No compatible endpoint was found");
	}
	
	/**
	 * Filter endpoints by various criteria
	 *
	 * @param searchSet set of endpoints
	 * @param url filter by url (inclusive, case insensitive) or null
	 * @param protocol filter by protocol (inclusive) or null
	 * @param mode filter by mode or null
	 * @param policy filter by policy or null
	 * @return filtered endpoints
	 * @param serverCertificate an array of byte.
	 */
	public static EndpointDescription[] select(EndpointDescription[] searchSet, String url, String protocol, MessageSecurityMode mode, SecurityPolicy policy, byte[] serverCertificate)
	{
		List<EndpointDescription> result = new ArrayList<EndpointDescription>();
		for (EndpointDescription d : searchSet) {
			final String endpointUrl = d.getEndpointUrl() == null ? null :d.getEndpointUrl().toLowerCase();
			if (endpointUrl == null) continue;
			if (protocol!=null && !endpointUrl.startsWith(protocol.toLowerCase())) continue;
			if (url!=null && !ObjectUtils.objectEquals(endpointUrl, url.toLowerCase())) continue;
			if (mode!=null && !ObjectUtils.objectEquals(d.getSecurityMode(), mode)) continue;
			if (policy!=null && !ObjectUtils.objectEquals(d.getSecurityPolicyUri(), policy.getPolicyUri())) continue;
			if (serverCertificate!=null && !Arrays.equals(serverCertificate, ByteString.asByteArray(d.getServerCertificate()))) continue;
			result.add(d);
		}
		return result.toArray(new EndpointDescription[result.size()]);		
	}

	/**
	 * <p>select.</p>
	 *
	 * @param searchSet an array of {@link org.opcfoundation.ua.core.EndpointDescription} objects.
	 * @param minKeySize a int.
	 * @param maxKeySize a int.
	 * @return an array of {@link org.opcfoundation.ua.core.EndpointDescription} objects.
	 */
	public static EndpointDescription[] select(EndpointDescription[] searchSet, int minKeySize, int maxKeySize)
	{
		List<EndpointDescription> result = new ArrayList<EndpointDescription>();
		for (EndpointDescription d : searchSet) {
			try {
				Cert cert = new Cert(ByteString.asByteArray(d.getServerCertificate()) );
				int keySize = cert.getKeySize();
				if ( keySize<minKeySize || keySize>maxKeySize ) continue;
				result.add(d);
			} catch (ServiceResultException e) {
				continue;
			}
		}
		return result.toArray(new EndpointDescription[result.size()]);		
	}
	
	/**
	 * Selects all endpoints that conform to given protcol
	 *
	 * @param searchSet an array of {@link org.opcfoundation.ua.core.EndpointDescription} objects.
	 * @param protocol a {@link java.lang.String} object.
	 * @return A subset of searchSet whose elements use given protocol
	 */
	public static EndpointDescription[] selectByProtocol(EndpointDescription[] searchSet, String protocol)
	{
		List<EndpointDescription> result = new ArrayList<EndpointDescription>();
		for (EndpointDescription d : searchSet)
			if (d.getEndpointUrl().toLowerCase().startsWith(protocol.toLowerCase()))
				result.add(d);
		return result.toArray(new EndpointDescription[result.size()]);
	}

	/**
	 * Selects all endpoints that conform to given message security mode
	 *
	 * @param searchSet an array of {@link org.opcfoundation.ua.core.EndpointDescription} objects.
	 * @param mode a {@link org.opcfoundation.ua.core.MessageSecurityMode} object.
	 * @return A subset of searchSet whose elements use given message security mode
	 */
	public static EndpointDescription[] selectByMessageSecurityMode(EndpointDescription[] searchSet, MessageSecurityMode mode)
	{
		List<EndpointDescription> result = new ArrayList<EndpointDescription>();
		for (EndpointDescription d : searchSet)
			if (d.getSecurityMode() == mode)
				result.add(d);
		return result.toArray(new EndpointDescription[result.size()]);
	}
	
	/**
	 * Selects all endpoints that conform to given message security mode
	 *
	 * @param searchSet an array of {@link org.opcfoundation.ua.core.EndpointDescription} objects.
	 * @param policy a {@link org.opcfoundation.ua.transport.security.SecurityPolicy} object.
	 * @return A subset of searchSet whose elements use given message security mode
	 */
	public static EndpointDescription[] selectBySecurityPolicy(EndpointDescription[] searchSet, SecurityPolicy policy)
	{
		List<EndpointDescription> result = new ArrayList<EndpointDescription>();
		for (EndpointDescription d : searchSet)
			if (ObjectUtils.objectEquals( d.getSecurityPolicyUri(), policy.getPolicyUri() ) )
				result.add(d);
		return result.toArray(new EndpointDescription[result.size()]);
	}
	
	/**
	 * Selects all endpoints with the given url. Compare is case-insensitive.
	 *
	 * @param searchSet an array of urls
	 * @param url a {@link java.lang.String} object.
	 * @return A subset of searchSet whose elements use given message security mode
	 */
	public static EndpointDescription[] selectByUrl(EndpointDescription[] searchSet, String url)
	{
		List<EndpointDescription> result = new ArrayList<EndpointDescription>();
		for (EndpointDescription d : searchSet)
			if (url.equalsIgnoreCase(d.getEndpointUrl()))
				result.add(d);
		return result.toArray(new EndpointDescription[result.size()]);
	}	

	/**
	 * Sorts endpoints by their security level. The highest security level last.
	 *
	 * @param set set of endpoints
	 * @return sorted array of endpoints
	 */
	public static EndpointDescription[] sortBySecurityLevel(EndpointDescription[] set)
	{
		Comparator<EndpointDescription> securityLevelComparator = new Comparator<EndpointDescription>() {
			public int compare(EndpointDescription o1, EndpointDescription o2) {
				return o1.getSecurityLevel().intValue() - o2.getSecurityLevel().intValue();
			}}; 
		EndpointDescription[] result = set.clone();
		Arrays.sort(result, securityLevelComparator);
		return result;
	}
	
	/**
	 * Select the most suitable endpoint.
	 * <p>
	 * Selection uses the following precedence:
	 *   1) Protocol must be opc.tcp (as http is not implemented)
	 *   2) Security uses sign and encrypt
	 *   3) Select highest security level (determined by the server)
	 *   4) Prefer hostname over localhost
	 *
	 * @param endpoints an array of {@link org.opcfoundation.ua.core.EndpointDescription} objects.
	 * @return compatible endpoint or null
	 */
	public static EndpointDescription selectEndpoint(EndpointDescription[] endpoints)
	{
		if (endpoints==null) 
			throw new IllegalArgumentException("null arg");
		// Filter out all but opc.tcp protocol endpoints
		endpoints = EndpointUtil.selectByProtocol(endpoints, "opc.tcp");
		// Filter out all but Signed & Encrypted endpoints
		endpoints = EndpointUtil.selectByMessageSecurityMode(endpoints, MessageSecurityMode.SignAndEncrypt);
		// 
		if (endpoints.length==0) return null;
		// Sort endpoints by security level. The lowest level at the beginning, the highest at the end of the array
		endpoints = EndpointUtil.sortBySecurityLevel(endpoints);
		EndpointUtil.reverse(endpoints);
		return endpoints[0]; 		
	}
	
	/**
	 * Reverse elements of an array
	 *
	 * @param array a {@link java.lang.Object} object.
	 */
	public static void reverse(Object array) {
		int length = Array.getLength(array);
		for (int i=0; i<length/2; i++) {
			Object x = Array.get(array, i);
			Object y = Array.get(array, length-1-i);
			Array.set(array, i, y);
			Array.set(array, length-i-1, x);
		}
	}
		
	/**
	 * Create user identity token based on username and password
	 *
	 * @param ep a {@link org.opcfoundation.ua.core.EndpointDescription} object.
	 * @param username a {@link java.lang.String} object.
	 * @param password a {@link java.lang.String} object.
	 * @return user identity token
	 * @throws org.opcfoundation.ua.common.ServiceResultException if endpoint or the stack doesn't support UserName token policy
	 * @param senderNonce an array of byte.
	 */
	public static UserIdentityToken createUserNameIdentityToken(EndpointDescription ep, byte[] senderNonce, String username, String password)	
	throws ServiceResultException
	{
		UserTokenPolicy policy = ep.findUserTokenPolicy(UserTokenType.UserName);
		if (policy==null) throw new ServiceResultException(StatusCodes.Bad_IdentityTokenRejected,  "UserName not supported");
		String securityPolicyUri = policy.getSecurityPolicyUri();
		if (securityPolicyUri==null) securityPolicyUri = ep.getSecurityPolicyUri();
		SecurityPolicy securityPolicy = SecurityPolicy.getSecurityPolicy( securityPolicyUri );
		if (securityPolicy==null) securityPolicy = SecurityPolicy.NONE;
		UserNameIdentityToken token = new UserNameIdentityToken();
		
		token.setUserName( username );
		token.setPolicyId( policy.getPolicyId() );
		
		// Encrypt the password, unless no security is defined
		SecurityAlgorithm algorithm = securityPolicy.getAsymmetricEncryptionAlgorithm();
		logger.debug("createUserNameIdentityToken: algorithm={}", algorithm);
		byte[] pw = password.getBytes(BinaryEncoder.UTF8);
		if (algorithm == null)
			token.setPassword(ByteString.valueOf(pw));
		else {
			try {
				byte[] c = ByteString.asByteArray(ep.getServerCertificate());
				Cert serverCert = (c == null || c.length == 0) ? null : new Cert(c);
				if (senderNonce != null)
					pw = ByteBufferUtils.concatenate(toArray(pw.length
							+ senderNonce.length), pw, senderNonce);
				else
					pw = ByteBufferUtils.concatenate(toArray(pw.length), pw);
				pw = CryptoUtil.encryptAsymm(pw, serverCert.getCertificate()
						.getPublicKey(), algorithm);
				token.setPassword(ByteString.valueOf(pw));

			} catch (InvalidKeyException e) {
				// Server certificate does not have encrypt usage
				throw new ServiceResultException(
						StatusCodes.Bad_CertificateInvalid,
						"Server certificate in endpoint is invalid: "
								+ e.getMessage());
			} catch (IllegalBlockSizeException e) {
				throw new ServiceResultException(
						StatusCodes.Bad_SecurityPolicyRejected, e.getClass()
								.getName() + ":" + e.getMessage());
			} catch (BadPaddingException e) {
				throw new ServiceResultException(
						StatusCodes.Bad_CertificateInvalid,
						"Server certificate in endpoint is invalid: "
								+ e.getMessage());
			} catch (NoSuchAlgorithmException e) {
				throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
			} catch (NoSuchPaddingException e) {
				throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
			}
			token.setEncryptionAlgorithm(algorithm.getUri());
		}
		return token;		
	}

	/**
	 * Create user identity token based on an issued token
	 *
	 * @param ep a {@link org.opcfoundation.ua.core.EndpointDescription} object.
	 * @param senderNonce an array of byte.
	 * @param issuedIdentityToken an array of byte.
	 * @return user identity token
	 * @throws org.opcfoundation.ua.common.ServiceResultException if endpoint or the stack doesn't support UserName token policy
	 */
	public static UserIdentityToken createIssuedIdentityToken(EndpointDescription ep, byte[] senderNonce, byte[] issuedIdentityToken)	
	throws ServiceResultException
	{
		UserTokenPolicy policy = ep.findUserTokenPolicy(UserTokenType.IssuedToken);
		if (policy==null) throw new ServiceResultException(StatusCodes.Bad_IdentityTokenRejected, "IssuedToken not supported");
		String securityPolicyUri = policy.getSecurityPolicyUri();
		if (securityPolicyUri==null) securityPolicyUri = ep.getSecurityPolicyUri();
		SecurityPolicy securityPolicy = SecurityPolicy.getSecurityPolicy( securityPolicyUri );
		if (securityPolicy==null) securityPolicy = SecurityPolicy.NONE;
		IssuedIdentityToken token = new IssuedIdentityToken();		
		token.setTokenData(ByteString.valueOf(issuedIdentityToken ));
		
		// Encrypt the token
		SecurityAlgorithm algorithmUri = securityPolicy.getAsymmetricEncryptionAlgorithm();
		if (algorithmUri==null) algorithmUri = SecurityAlgorithm.RsaOaep;
		try {
			Cipher cipher = Cipher.getInstance(algorithmUri.getStandardName());
			Cert serverCert = new Cert(ByteString.asByteArray(ep.getServerCertificate()));
			cipher.init(Cipher.ENCRYPT_MODE, serverCert.getCertificate());
			byte[] tokenData = issuedIdentityToken;
			if (senderNonce != null)
				tokenData = ByteBufferUtils
						.concatenate(toArray(issuedIdentityToken.length
								+ senderNonce.length), issuedIdentityToken,
								senderNonce);
			token.setTokenData(ByteString.valueOf(cipher.doFinal(tokenData)));
			token.setEncryptionAlgorithm(algorithmUri.getUri());
			
		} catch (InvalidKeyException e) {
			// Server certificate does not have encrypt usage
			throw new ServiceResultException(StatusCodes.Bad_CertificateInvalid, "Server certificate in endpoint is invalid: "+e.getMessage());
		} catch (IllegalBlockSizeException e) {
			throw new ServiceResultException(StatusCodes.Bad_SecurityPolicyRejected, e.getClass().getName()+":"+e.getMessage());
		} catch (BadPaddingException e) {
			throw new ServiceResultException(StatusCodes.Bad_CertificateInvalid, "Server certificate in endpoint is invalid: "+e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
		} catch (NoSuchPaddingException e) {
			throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
		}
		
		return token;		
	}

	/**
	 * Get all Internet addresses of this computer. Excludes IPv6 addresses.
	 *
	 * @return all Internet addresses of this computer.
	 * @throws java.net.SocketException if any.
	 */
	public static Set<InetAddress> getInetAddresses() throws SocketException {
		return getInetAddresses(false); 
	}

	/**
	 * Get all Internet addresses of this computer.
	 *
	 * @param enableIPv6 Set true to enable IPv6 addressing. Requires Java 7 or later on Windows platforms.
	 * @return all Internet addresses of this computer
	 * @throws java.net.SocketException if any.
	 */
	public static Set<InetAddress> getInetAddresses(boolean enableIPv6) 
	throws SocketException
	{
		Set<InetAddress> result = new HashSet<InetAddress>();
		Enumeration<NetworkInterface> nids = NetworkInterface.getNetworkInterfaces();
		for (;nids.hasMoreElements();) 
		{
			Enumeration<InetAddress> addrs = nids.nextElement().getInetAddresses(); 
			for (;addrs.hasMoreElements();) {
				InetAddress addr = addrs.nextElement();
				if (!(addr instanceof Inet6Address) || enableIPv6)
					result.add(addr);
			}
		}
		return result;
	}
	
	/**
	 * Figure out some random hostname for this computer
	 *
	 * @throws java.net.SocketException if any.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getHostname() throws SocketException {
		try {
			String hostname = java.net.InetAddress.getLocalHost().getHostName();
			if ( hostname != null ) return hostname;
		} catch (UnknownHostException e) {
		}
		
		Set<InetAddress> addrs = EndpointUtil.getInetAddresses();
		for (InetAddress addr : addrs) {
			String hostaddr = addr.getHostAddress();
			String hostname = EndpointUtil.inetAddressToName(addr);
			if ( !hostaddr.equals(hostname) ) return hostname;
		}
		
		return "localhost";
	}
	
	/**
	 * Get all internet address names of this computer.
	 *
	 * @return all internet address names of this computer in URL compatible format
	 * @throws java.net.SocketException if any.
	 */
	public static Set<String> getInetAddressNames() 
	throws SocketException
	{
 		Set<String> result = new HashSet<String>();
		Enumeration<NetworkInterface> nids = NetworkInterface.getNetworkInterfaces();
		for (;nids.hasMoreElements();) 
		{
			Enumeration<InetAddress> addrs = nids.nextElement().getInetAddresses(); 
			for (;addrs.hasMoreElements();)
			{
				InetAddress addr = addrs.nextElement();			
				// IPV6 does not work on Windows computers
				if (!(addr instanceof Inet6Address))
					result.add( inetAddressToName(addr) );
			}
		}
		return result;
	}
	
	/**
	 * <p>inetAddressToName.</p>
	 *
	 * @param addr a {@link java.net.InetAddress} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String inetAddressToName( InetAddress addr ) {
		String hostname = addr.getHostName();
		String hostaddr = addr.getHostAddress();				
		boolean hasName = !hostname.equals(hostaddr);
		boolean IPv6 = addr instanceof Inet6Address;
		
		if (hasName) return hostname ;
		if (IPv6) return "["+hostaddr+"]";
		else return hostaddr;
	}
	

	/**
	 * Convert endpoint url to socket addresses.
	 *
	 * @param endpointUrl a {@link java.lang.String} object.
	 * @return a collection of bind addresses
	 * @throws java.lang.IllegalArgumentException endpointUrl is problematic some way
	 */
	public static List<SocketAddress> toSocketAddresses(String endpointUrl) 
	throws IllegalArgumentException {
		return toSocketAddresses(endpointUrl, false);
	}
	/**
	 * Convert endpoint url to socket addresses.
	 *
	 * @param endpointUrl a {@link java.lang.String} object.
	 * @param enableIPv6 Set true to enable IPv6 addressing. Requires Java 7 or later on Windows platforms.
	 * @return a collection of bind addresses
	 * @throws java.lang.IllegalArgumentException endpointUrl is problematic some way
	 */
	public static List<SocketAddress> toSocketAddresses(String endpointUrl, boolean enableIPv6) 
	throws IllegalArgumentException
	{
		List<SocketAddress> result = new ArrayList<SocketAddress>();

		if (endpointUrl == null) 
			throw new IllegalArgumentException("URL not valid.");
		try {
			URI uri = new URI(endpointUrl);
			String proto = UriUtil.getTransportProtocol(endpointUrl);
			String host = uri.getHost();
			int port = uri.getPort();
			if (host == null) {
				// Do a custom parse, if the URI is not valid, possibly because it
				// does not conform to RFC 2396. This occurs, for example, if the
				// host name contains '_' characters, which are used by some Windows
				// computers
				String[] parts = endpointUrl.split("/+");
//				proto = parts[0].split(":")[0]; // // Use the proto parsed from URI, which should be fine, already
				String[] host_port = parts[1].split(":");
				host = host_port[0];
				try {
					port = Integer.parseInt(host_port[1]);
				} catch (NumberFormatException e1) {
					port = 0;
				} catch (ArrayIndexOutOfBoundsException e2) {
					port = 0;
				}
			}

			proto = proto.toLowerCase();

			if (port == 0 || port == -1)
				port = UriUtil.defaultPort(proto);

			if (proto.equals("opc.tcp") || proto.equals("http") || proto.equals("https")) {
				// Bind to the host in endpoint uri, and to no other interface

				try {
					// !!WORKAROUND!! Java6 cannot bind to IPv6
					// Hostnames (e.g. localhost) resolves to 127.0.0.1 and 0::1
					// This workaround omits IPv6 addresses if IPv4 addresses
					// exist, but lets error to be thrown if there are only
					// IPv6 addresses. This is to show IPv6 cannot be bound

					InetAddress addrs[] = InetAddress.getAllByName(host);
					boolean hasIPv4 = false;
					boolean hasIPv6 = false;
					for (InetAddress addr : addrs) {
						hasIPv4 |= addr instanceof Inet4Address;
						hasIPv6 |= addr instanceof Inet6Address;
					}

					for (InetAddress addr : addrs) {
						boolean IPv6 = addr instanceof Inet6Address;

						if (!enableIPv6 && IPv6 && hasIPv6 && hasIPv4) {
							logger.warn("Binding of {} to {} was omited. (Workaround)", endpointUrl, addr.getHostAddress());
							continue;
						}

						SocketAddress sa = new InetSocketAddress(addr, port);
						result.add(sa);
					}
				} catch (UnknownHostException e) {
					throw new IllegalArgumentException(e);
				}

			} else {
				throw new IllegalArgumentException("Unsupported protocol " + proto);
			}
		} catch (URISyntaxException ex) {
			throw new IllegalArgumentException("Invalid URL", ex);
		}
		return result;
	}

	

	
	/**
	 * Create anonymous user identity token
	 *
	 * @param ep a {@link org.opcfoundation.ua.core.EndpointDescription} object.
	 * @return user identity token
	 * @throws org.opcfoundation.ua.common.ServiceResultException if endpoint or the stack doesn't support Anonymous token policy
	 */
	public static UserIdentityToken createAnonymousIdentityToken(EndpointDescription ep)	
	throws ServiceResultException
	{
		UserTokenPolicy policy = ep.findUserTokenPolicy(UserTokenType.Anonymous);
		if (policy==null) throw new ServiceResultException(StatusCodes.Bad_IdentityTokenRejected, "Anonymous UserTokenType is not supported");
		return new AnonymousIdentityToken( policy.getPolicyId() );
	}
	
	/**
	 * <p>createX509IdentityToken.</p>
	 *
	 * @param ep a {@link org.opcfoundation.ua.core.EndpointDescription} object.
	 * @param serverNonce an array of byte.
	 * @param certificate a {@link org.opcfoundation.ua.transport.security.Cert} object.
	 * @param key a {@link java.security.PrivateKey} object.
	 * @param signatureData a {@link org.opcfoundation.ua.core.SignatureData} object.
	 * @return a {@link org.opcfoundation.ua.core.X509IdentityToken} object.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public static X509IdentityToken createX509IdentityToken(
			EndpointDescription ep, byte[] serverNonce, Cert certificate, PrivateKey key, SignatureData signatureData) throws ServiceResultException{
		if (signatureData == null)
			throw new NullPointerException("signatureData must be defined (will be filled in)");
		UserTokenPolicy policy = ep.findUserTokenPolicy(UserTokenType.Certificate);
		if (policy==null) throw new ServiceResultException(StatusCodes.Bad_IdentityTokenRejected,
				"Certificate UserTokenType is not supported");

		X509IdentityToken token = new X509IdentityToken( policy.getPolicyId(), ByteString.valueOf(certificate.getEncoded()) );		
		
		String securityPolicyUri = policy.getSecurityPolicyUri();
		if (securityPolicyUri==null) securityPolicyUri = ep.getSecurityPolicyUri();
		SecurityPolicy securityPolicy = SecurityPolicy.getSecurityPolicy( securityPolicyUri );
		Cert serverCert = new Cert(ByteString.asByteArray(ep.getServerCertificate()));
		if ((securityPolicy!=null) && (serverCert != null))
			try {
				// Create a Signature object and initialize it with the private
				// key
				Signature signature = Signature.getInstance(securityPolicy
						.getAsymmetricSignatureAlgorithm().getTransformation());
				signature.initSign(key);

				signature.update(serverCert.getEncoded());
				signature.update(serverNonce);

				signatureData.setSignature(ByteString.valueOf(signature.sign()));
				signatureData.setAlgorithm(securityPolicy
						.getAsymmetricSignatureAlgorithm().getUri());

			} catch (NoSuchAlgorithmException e) {
				throw new ServiceResultException(
						StatusCodes.Bad_SecurityChecksFailed,
						"Signature generation failed: " + e.getMessage());
			} catch (InvalidKeyException e) {
				// Server certificate does not have encrypt usage
				throw new ServiceResultException(
						StatusCodes.Bad_CertificateInvalid,
						"Server certificate in endpoint is invalid: "
								+ e.getMessage());
			} catch (SignatureException e) {
				throw new ServiceResultException(
						StatusCodes.Bad_SecurityChecksFailed,
						"Signature generation failed: " + e.getMessage());
			}
		return token;
	}
	
	private static byte[] toArray(int value)
	{
		// Little Endian
		return new byte[] {(byte)value, (byte)(value>>8), (byte)(value>>16), (byte)(value>>24)};
		
		// Big-endian
//		return new byte[] {(byte)(value>>24), (byte)(value>>16), (byte)(value>>8), (byte)(value)};
	}

	/**
	 * Check if the endpointUrl matches the url, except for the hostname part.
	 *
	 * @param uri a {@link java.net.URI} object.
	 * @param requestedUri a {@link java.net.URI} object.
	 * @return a boolean.
	 */
	public static boolean urlEqualsHostIgnoreCase(URI uri, URI requestedUri) {
		return uri.getScheme().equalsIgnoreCase(requestedUri.getScheme())
		&& uri.getPort() == requestedUri.getPort()
		&& uri.getPath().equalsIgnoreCase(requestedUri.getPath());
	}

	/**
	 * Check if the endpointUrl matches the url, except for the hostname part.
	 *
	 * @param endpointUrl a {@link java.lang.String} object.
	 * @param url a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean urlEqualsHostIgnoreCase(String endpointUrl,
			String url) {
		try {
			return urlEqualsHostIgnoreCase(new URI(endpointUrl), new URI(url));
		} catch (URISyntaxException e) {
			return false;
		}
	}

	/**
	 * <p>containsSecureUserTokenPolicy.</p>
	 *
	 * @param userIdentityTokens an array of {@link org.opcfoundation.ua.core.UserTokenPolicy} objects.
	 * @return a boolean.
	 */
	public static boolean containsSecureUserTokenPolicy(
			UserTokenPolicy[] userIdentityTokens) {
		if (userIdentityTokens != null)
			for (UserTokenPolicy p: userIdentityTokens)
				if (p.getSecurityPolicyUri() != null && !p.getSecurityPolicyUri().equals(SecurityPolicy.NONE))
					return true;
		return false;
	}

}
