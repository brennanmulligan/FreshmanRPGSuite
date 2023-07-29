import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:game_manager/repository/player/basic_response.dart';
import 'package:game_manager/repository/quest/action_type_DTO.dart';
import 'package:game_manager/repository/quest/objective_completion_type_DTO.dart';
import 'package:game_manager/repository/quest/quest_editing_request.dart';
import 'package:game_manager/repository/quest/quest_editing_response.dart';
import 'package:game_manager/repository/quest/quest_record.dart';
import 'package:game_manager/repository/quest/quest_repository.dart';
import 'package:game_manager/repository/quest/upsert_quest_request.dart';
import 'package:http_mock_adapter/http_mock_adapter.dart';
import 'dart:io';

void main() {
  dotenv.testLoad(fileInput: File('assets/.env').readAsStringSync());

  group('Quest Repository Tests: ', () {
    late Dio dio;
    late DioAdapter dioAdapter;

    var testQuestUpdate = const UpsertQuestRequest(
        id: 1,
        title: 'test1',
        description: 'test of quest repo',
        objectives: [],
        xpGained: 100,
        triggerMapName: 'randomMap',
        triggerRow: 1,
        triggerCol: 1,
        objectivesForFulfillment: 1,
        completionActionType: 1,
        startDate: '12-12-2020',
        endDate: '12-12-2023',
        easterEgg: false);

    var testBadQuestUpdate = const UpsertQuestRequest(
        id: 1,
        title: 'test1ThatForSomeReasonFailsToUpsert',
        description: 'test of quest repo',
        objectives: [],
        xpGained: 100,
        triggerMapName: 'randomMap',
        triggerRow: 1,
        triggerCol: 1,
        objectivesForFulfillment: 1,
        completionActionType: 1,
        startDate: '12-12-2020',
        endDate: '12-12-2023',
        easterEgg: false);

    var questEditingDataRequest = const QuestEditingDataRequest();


    var testingQuestRecord = [
      const QuestRecord(
          id: 1,
          title: "title",
          description: "description",
          xpGained: 2,
          triggerMapName: "triggerMapName",
          triggerRow: 1,
          triggerCol: 3,
          objectivesForFulfillment: 2,
          objectives: [],
          completionActionType: ActionTypeDTO(actionName: "Name", actionID: 5),
          startDate: '12-12-2020',
          endDate: '12-12-2023',
          easterEgg: false)
    ];

    var testingMapNames = ["map1", "map2", "map3"];

    var testingActionTypes = [
      const ActionTypeDTO(actionName: "name", actionID: 2)
    ];

    var testingObjCompletionTypes = [
      const ObjectiveCompletionTypeDTO(
          objCompletionName: "name", objCompletionId: 2)
    ];



    var goodEditingDataResponse = QuestEditingDataResponse(true, quests:
    testingQuestRecord, mapNames: testingMapNames, completionActionTypes:
    testingActionTypes, objCompletionTypes: testingObjCompletionTypes);

    Map<String, dynamic> goodResponse = {
      "success": true,
      "description": "worked",
    };

    Map<String, dynamic> badResponse = {
      "success": false,
      "description": "Did not work",
    };

    setUp(() {
      dio = Dio();
      dioAdapter = DioAdapter(dio: dio);
      dio.httpClientAdapter = dioAdapter;
    });

    test('Good Request', () async {
      dioAdapter.onPost('/quest/upsert',
          (request) => request.reply(200, jsonEncode(goodResponse)),
          data: jsonEncode(testQuestUpdate));

      QuestRepository repo = QuestRepository(dio: dio);

      BasicResponse response = await repo.upsertQuest(testQuestUpdate);
      expect(response.success, true);
      expect(response.description, "worked");
    });

    test('Bad Request', () async {
      dioAdapter.onPost('/quest/upsert',
          (request) => request.reply(200, jsonEncode(badResponse)),
          data: jsonEncode(testBadQuestUpdate));

      QuestRepository repo = QuestRepository(dio: dio);

      BasicResponse response = await repo.upsertQuest(testBadQuestUpdate);
      expect(response.success, false);
      expect(response.description, "Did not work");
    });

    test('Good get all quests Request', () async {
      dioAdapter.onGet('/quest/getQuestEditingInfo',
          (request) => request.reply(200, jsonEncode(goodEditingDataResponse)),
          data: jsonEncode(questEditingDataRequest));

      QuestRepository repo = QuestRepository(dio: dio);

      QuestEditingDataResponse response = await repo.getQuestEditingData(questEditingDataRequest);
      expect(response.success, true);
      expect(response.quests, testingQuestRecord);
      expect(response.mapNames, testingMapNames);
      expect(response.completionActionTypes, testingActionTypes);
      expect(response.objCompletionTypes, testingObjCompletionTypes);
    });


  });
}
