package api.service;

import api.model.GameManagerPlayer;

/**
 * Player Service interface
 * Contains actions available to controller related to Players
 *
 * @author Joel
 */
public interface PlayerService
{
    int addPlayer(GameManagerPlayer player);
}
