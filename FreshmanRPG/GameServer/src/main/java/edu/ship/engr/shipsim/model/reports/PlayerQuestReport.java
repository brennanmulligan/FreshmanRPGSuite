package edu.ship.engr.shipsim.model.reports;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerObjectiveStateDTO;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.Report;
import org.apache.commons.compress.utils.Lists;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Derek
 */
public class PlayerQuestReport implements Report
{
    private final Player player;
    private final List<ClientPlayerQuestStateDTO> quests;

    public PlayerQuestReport(Player player, List<ClientPlayerQuestStateDTO> quests)
    {
        this.player = player;
        this.quests = quests;
    }

    public Player getPlayer()
    {
        return player;
    }

    public List<ClientPlayerQuestStateDTO> getQuests()
    {
        return quests;
    }

    public List<ClientPlayerObjectiveStateDTO> getObjectives(int questID)
    {
        return quests.stream()
                .filter(q -> q.getQuestID() == questID)
                .map(ClientPlayerQuestStateDTO::getObjectiveList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public String toJson() throws JsonProcessingException
    {
        Map<String, List<Map<String, Object>>> map = Maps.newHashMap();

        List<Map<String, Object>> questEntries = Lists.newArrayList();
        for (ClientPlayerQuestStateDTO quest : getQuests())
        {
            questEntries.add(quest.toMap());
        }

        map.put("quests", questEntries);

        return new ObjectMapper().writeValueAsString(map);
    }

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
        PlayerQuestReport that = (PlayerQuestReport) o;

        return Objects.deepEquals(player, that.player) && Objects.deepEquals(quests, that.quests);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(player, quests);
    }
}
