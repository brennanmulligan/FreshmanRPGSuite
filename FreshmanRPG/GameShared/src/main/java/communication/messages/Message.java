package communication.messages;

import java.io.Serializable;

/**
 * Everything that goes over the communication must implement the interface
 * Message. Yes, I know it is empty. This is an example of the [Marker Interface
 * pattern](http://en.wikipedia.org/wiki/Marker_interface_pattern). The purpose
 * of implementing the interface is to demonstrate the purpose of the class even
 * though it doesn't require any specific functionality. <br>
 * Given that, things are a little more complex for us. For consistency, we want
 * all Messages to adhere to these criteria:
 *
 * <ol>
 * <li>The name number end in "Message"
 * <li>The class must implement both Serializable and Message (both for marking
 * purposes)
 * <li>The class must be in the communication.messages package in the GameShared
 * project
 * <li>The class must contain a toString that returns the name of the class, a
 * colon, and the instance variables it contains.
 * <li>There is a getter for each instance variable
 * </ol>
 *
 * <br>
 * In addition, we should try to meet these objectives:
 *
 * <ol>
 * <li>All instance variables are set by the constructor
 * </ol>
 *
 * <br>
 * Given these criteria, there are two ways Messages get tested. First, there is
 * a MessageStructureVerifierTest in the communication.messages package. That
 * test will find all of the other classes in the package, verify that each
 * implements Serializable and Message interfaces, and verify that there is an
 * appropriate getter for each non-transient/final field (using is... for
 * booleans and get... for all other types).
 *
 * <br>
 * Given MessageStructureVerifierTest, new Message classes only require one test
 * that verifies that its toString method and its getters work. So, the test
 * classes for methods look like this:
 *
 * <br>
 *
 * <pre>
 * public void testToString()
 * {
 * 	LoginMessage msg = new LoginMessage(&quot;fred&quot;, &quot;xxx&quot;);
 * 	assertEquals(&quot;Login Message: playerName = fred and password = xxx&quot;, msg.toString());
 * 	assertEquals(&quot;fred&quot;, msg.getPlayerName());
 * 	assertEquals(&quot;xxx&quot;, msg.getPassword());
 * }
 * </pre>
 *
 * @author merlin
 *
 */
public interface Message extends Serializable
{

}
