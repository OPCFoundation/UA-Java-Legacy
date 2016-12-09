package org.opcfoundation.ua.utils;

import org.opcfoundation.ua.builtintypes.Structure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A base class for Structure implementations. Main use case for extending this class
 * is the default implementation for .clone which does not throw {@link CloneNotSupportedException}
 * in .clone method signature as Structure as of GH#65 extends Cloneable making it easier for
 * classes extending this class to just call super.clone without a try-catch block.
 */
public abstract class AbstractStructure implements Structure{

	private static final Logger logger = LoggerFactory.getLogger(AbstractStructure.class);
	
	@Override
	public AbstractStructure clone() {
		try {
			return (AbstractStructure) super.clone();
		} catch (CloneNotSupportedException e) {
			//It should be technically impossible for this call to fail as
			//Structure extends Cloneable, but handing this just in case.
			logger.error("Got a CloneNotSupportedException, should be impossible",e);
			throw new Error("Every Structure implementation shall be Cloneable", e);
		}
	}
	
	
}
