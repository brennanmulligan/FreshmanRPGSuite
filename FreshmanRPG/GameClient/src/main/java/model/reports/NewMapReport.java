package model.reports;

import com.badlogic.gdx.maps.tiled.TiledMap;

import model.QualifiedObservableReport;

import java.util.Objects;

/**
 * Carries a TiledMap when we change the map we are using
 *
 * @author Merlin
 *
 */
public final class NewMapReport implements QualifiedObservableReport
{

	private TiledMap tiledMap;

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		NewMapReport that = (NewMapReport) o;
		return Objects.equals(tiledMap, that.tiledMap);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(tiledMap);
	}

	/**
	 * @param tiledMap
	 *            the new map we have moved to
	 */
	public NewMapReport(TiledMap tiledMap)
	{
		this.tiledMap = tiledMap;
	}

	/**
	 * @return the new map we have moved to
	 */
	public TiledMap getTiledMap()
	{
		return tiledMap;
	}

}
