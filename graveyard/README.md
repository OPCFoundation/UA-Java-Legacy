# Idea&Feature Graveyard
This "module" contains parts that are previously been part of the stack, but are not part of the main distribution for some reasons, but might still be something someone might need. These are not supported, nor might not compile/work with newer versions of java (or the main stack itself).

Additionally this is somewhat experimental and the entire "module" might be removed in some version if deemed to be unuseful.

## SunJceCryptoProvider & SunJceCertificateProvider
Removed in 1.4.0. See #164 (and possibly #165). These classes were originally intended as "last-effort-fallbacks", if Bouncy/SpongyCastle could not be used. They did this by depending on the private JRE APIs as java 6+ does not have public apis for creating and managing certificates. 

Starting from java 9+ those private APIs are no longer accessible and would not work. Additionally they never really fully worked (#7) with the full feature set that e.g. BouncyCastle did, so in the end users needed to use Bouncy/SpongyCastle after all.