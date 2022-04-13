import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import manager.GameManagerTestFX;
import ui.fx.contentviews.AdventureContentViewTest;
import ui.fx.framework_test.AddAdventureTest;
import ui.fx.framework_test.AddPlayerTest;
import ui.fx.framework_test.AddQuestTest;
import ui.fx.framework_test.AddQuizbotTest;
import ui.fx.framework_test.DeleteAdventureTest;
import ui.fx.framework_test.DeleteInteractableObjectTest;
import ui.fx.framework_test.DeletePlayerTest;
import ui.fx.framework_test.DeleteQuestionTest;
import ui.fx.framework_test.EditPlayerTest;
import ui.fx.framework_test.EditQuizbotTest;
import ui.fx.framework_test.FrameworkTest;
import ui.fx.framework_test.ImportPlayerTest;

/**
 * @author merlin
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
		{
				//GameManagerTest.class,
				FrameworkTest.class,
				AdventureContentViewTest.class,
				GameManagerTestFX.class,

				AddAdventureTest.class,
				AddPlayerTest.class,
				AddQuestTest.class,
				AddQuizbotTest.class,
				DeleteAdventureTest.class,
				DeleteInteractableObjectTest.class,
				DeletePlayerTest.class,
				DeleteQuestionTest.class,

				EditPlayerTest.class,
				EditQuizbotTest.class,

				ImportPlayerTest.class
		})

public class GUIManagerTests
{

}
