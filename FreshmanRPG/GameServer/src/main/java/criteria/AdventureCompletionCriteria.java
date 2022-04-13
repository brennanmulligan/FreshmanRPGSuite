package criteria;

import java.io.Serializable;

/**
 * A marker interface for things that can be used as criteria for completing an
 * adventure
 *
 * @author Merlin
 */
public interface AdventureCompletionCriteria extends Serializable
{
	/**
	 * @return string representation of the criteria
	 */
	@Override
	public String toString();
}
