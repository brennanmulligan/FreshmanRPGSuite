package ui.fx;

/**
 * @author Joshua McMillen and Benjamin Uleau
 * Giving meaning to unicode icon font ( Font Family: Ionicons )
 */
public enum IconFont
{
	/**
	 * Symbol for Player Menu Button
	 */
	PERSON("\uf47c"),
	/**
	 * Symbol for Adventures and Quests Menu Button
	 */
	MAP("\uf203"),
	/**
	 * Symbol for QuizBot Menu Button
	 */
	SUNGLASSES("\uf43f"),
	/**
	 * Symbol for Intractable Items Menu Button
	 */
	ITEM_BOX("\uf4dc"),
	/**
	 * Symbol for Search Action Button
	 */
	MAGNIFYING_GLASS("\uf4a4"),
	/**
	 * Symbol for Refresh Action Button
	 */
	REFRESH("\uf49a"),
	/**
	 * Symbol for Import Action Button
	 */
	IMPORT("\uf255"),
	/**
	 * Symbol for Add Action Button
	 */
	ADD("\uf2c7"),
	/**
	 * Symbol for Edit Action Button
	 */
	PENCIL("\uf2bf"),
	/**
	 * Symbol for Delete Action Button
	 */
	TRASH("\uf37f"),
	/**
	 * Symbol for PlayerAdventureState Button
	 */
	CIRCLE_CHECK("\uf375"),
	/**
	 * Symbol for Help Button
	 */
	HELP("?"),
	/**
	 * Symbol for message board
	 */
	BOARD("\uf3b2");

	private String unicode;

	private IconFont(String unicode)
	{
		this.unicode = unicode;
	}

	/**
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString()
	{
		return this.unicode;
	}

}
