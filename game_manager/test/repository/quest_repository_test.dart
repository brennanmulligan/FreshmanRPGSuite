import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:game_manager/repository/player/basic_response.dart';
import 'package:game_manager/repository/quest/action_type_DTO.dart';
import 'package:game_manager/repository/quest/quest_editing_info_DTO.dart';
import 'package:game_manager/repository/quest/quest_editing_request.dart';
import 'package:game_manager/repository/quest/quest_editing_response.dart';
import 'package:game_manager/repository/quest/quest_record.dart';
import 'package:game_manager/repository/quest/quest_repository.dart';
import 'package:game_manager/repository/quest/upsert_quest_request.dart';
import 'package:test/test.dart';
import 'package:http_mock_adapter/http_mock_adapter.dart';

void main() {
  group('Quest Repository Tests: ', () {
    late Dio dio;
    late DioAdapter dioAdapter;


    var testQuestUpdate =  const UpsertQuestRequest(
      title: 'test1',
      description: 'test of quest repo',
      xpGained: 100,
      triggerMapName: 'randomMap',
      triggerRow: 1,
      triggerCol: 1,
      objectivesForFulfillment: 1,
      completionActionType: 1,
      startDate: '12-12-1212',
      endDate: '12-12-1212',
      easterEgg: false
    );

    var testBadQuestUpdate =  const UpsertQuestRequest(
        title: 'test1ThatForSomeReasonFailsToUpsert',
        description: 'test of quest repo',
        xpGained: 100,
        triggerMapName: 'randomMap',
        triggerRow: 1,
        triggerCol: 1,
        objectivesForFulfillment: 1,
        completionActionType: 1,
        startDate: '12-12-1212',
        endDate: '12-12-1212',
        easterEgg: false
    );

    var testGetQuests =  const QuestRequest();

    var testBadGetQuests =  const QuestRequest();

    var emptyQuestInfoDto = const QuestEditingInfoDTO(quests: [], mapNames: [], completionActionTypes: []);

    // EditingQuestInfoDto just for testing purpose.
    var testingQuestDTO = const QuestEditingInfoDTO(quests: [QuestRecord(title: "title", description: "description",
        xpGained: 2, triggerMapName: "triggerMapName", triggerRow: 1, triggerCol: 3, objectivesForFulfillment: 2,
        completionActionType: ActionTypeDTO(actionName: "Name", actionID: 5), startDate: "1232", endDate: "3232", easterEgg: false)],
        mapNames: ["map1", "map2", "map3"], completionActionTypes: [ActionTypeDTO (actionName: "name", actionID: 2)]);

    Map<String, dynamic> goodRequest = {
      "success": true,
      "questEditingInfoDTO" : testingQuestDTO
    };

    Map<String, dynamic> goodResponse = {
      "success": true,
      "description": "worked",
    };

    Map<String, dynamic> badResponse = {
      "success" : false,
      "description" : "Did not work",
    };

    setUp(() {
      dio = Dio();
      dioAdapter = DioAdapter(dio: dio);
      dio.httpClientAdapter = dioAdapter;
    });

    test('Good Request', () async {
      dioAdapter.onPost('/quest/upsert', (request) => request
          .reply(200,
          jsonEncode(goodResponse)),
          data: jsonEncode(testQuestUpdate));

      QuestRepository repo = QuestRepository(dio: dio);

      BasicResponse response = await repo.upsertQuest(testQuestUpdate);
      expect(response.success, true);
      expect(response.description, "worked");

    });

    test('Bad Request', () async {
      dioAdapter.onPost('/quest/upsert', (request) =>
          request
              .reply(200,
              jsonEncode(badResponse)),
          data: jsonEncode(testBadQuestUpdate));

      QuestRepository repo = QuestRepository(dio: dio);

      BasicResponse response = await repo.upsertQuest(testBadQuestUpdate);
      expect(response.success, false);
      expect(response.description, "Did not work");
    });

      test('Good get all quests Request', () async {
        dioAdapter.onPost('/quest/getQuestEditingInfo', (request) => request
            .reply(200,
            jsonEncode(goodRequest)),
            data: jsonEncode(testGetQuests));

        QuestRepository repo = QuestRepository(dio: dio);

        QuestResponse response = await repo.getQuests(testGetQuests);
        expect(response.success, true);
        expect(response.questEditingInfoDTO, testingQuestDTO);
      });

      test('Bad get all quests Request', () async {
        dioAdapter.onPost('/quest/getQuestEditingInfo', (request) => request
            .reply(200,
            jsonEncode(badResponse)),
            data: jsonEncode(testBadQuestUpdate));

        QuestRepository repo = QuestRepository(dio: dio);

        QuestResponse response = await repo.getQuests(testBadGetQuests);
        expect(response.success, false);
        expect(response.questEditingInfoDTO, emptyQuestInfoDto);

    });
  });
}
