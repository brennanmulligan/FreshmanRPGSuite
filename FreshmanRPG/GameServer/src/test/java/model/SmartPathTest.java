package model;

import datatypes.PlayersForTest;
import org.junit.Before;
import org.junit.Test;

public class SmartPathTest
{
    boolean f = false;
    boolean t = true;

    private final boolean[][] testPassibilityMap =
            {
                      {t, t, f, t, f}
                    , {t, t, t, t, t}
                    , {t, f, f, f, t}
                    , {t, f, f, f, t}
                    , {f, t, t, t, t}
            };

    @Before
    public void setUp()
    {
        NPC npc = new NPC(33);
        npc.setMapName("quad.tmx");
        PlayerManager.getSingleton().addNpc(npc);

        OptionsManager.getSingleton().setMapName("quad.tmx");
        OptionsManager.getSingleton().setUsingMocKDataSource(true);
        QualifiedObservableConnector.resetSingleton();
        ChatManager.resetSingleton();
    }

    @Test
    public void testRightPath() {

    }

    @Test
    public void testConstructor()
    {
        SmartPath sp = new SmartPath(PlayersForTest.PRESIDENT_NPC.getPlayerID());
        sp.printPassabilityMap();
    }
}
