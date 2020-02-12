package org.opcfoundation.ua.application;

import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Test;
import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.AnonymousIdentityToken;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.core.UserIdentityToken;
import org.opcfoundation.ua.core.UserNameIdentityToken;
import org.opcfoundation.ua.transport.security.SecurityPolicy;

/**
 * Tests {@link SessionChannel}.
 */
public class SessionChannelTest {

  @Test
  public void validateServerNonceNullNonceAnonymousToken() throws Exception {
    Session session = new Session();
    session.serverNonce = null;
    session.serverNoncesAreNotUnique = false;

    UserIdentityToken identity = new AnonymousIdentityToken();
    SessionChannel.validateServerNonce(session, identity, null);
    // should pass, as Anonymous token does not need nonce
  }

  @Test
  public void validateServerNonceNullNonceUserNameTokenPlainText() throws Exception {
    Session session = new Session();
    session.serverNonce = null;
    session.serverNoncesAreNotUnique = false;

    // some fake encrypted password
    Random rand = new Random(0);
    byte[] encryptedpassword = new byte[10];
    rand.nextBytes(encryptedpassword);

    UserIdentityToken identity =
        new UserNameIdentityToken("policyId", "user", ByteString.valueOf(encryptedpassword), null);
    SessionChannel.validateServerNonce(session, identity, null);

    // The user token does not require encryption, thus we do not need to validate nonce thus we
    // should reach here
  }

  @Test
  public void validateServerNonceNullNonceUserNameTokenRequiringEncryption() throws Exception {
    Session session = new Session();
    session.serverNonce = null;
    session.serverNoncesAreNotUnique = false;

    // some fake encrypted password
    Random rand = new Random(0);
    byte[] encryptedpassword = new byte[10];
    rand.nextBytes(encryptedpassword);

    UserIdentityToken identity = new UserNameIdentityToken("policyId", "user", ByteString.valueOf(encryptedpassword),
        SecurityPolicy.BASIC128RSA15.getAsymmetricEncryptionAlgorithm().getUri());
    try {
      SessionChannel.validateServerNonce(session, identity, null);
      fail("Should have thrown");
    } catch (ServiceResultException e) {
      if (!StatusCodes.Bad_NonceInvalid.equals(e.getStatusCode().getValue())) {
        throw e;
      }
    }
  }

}
