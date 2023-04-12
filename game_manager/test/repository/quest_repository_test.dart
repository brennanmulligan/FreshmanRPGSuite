import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:game_manager/repository/player/basic_response.dart';
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
      dioAdapter.onPost('/quest/upsert', (request) => request
          .reply(200,
          jsonEncode(badResponse)),
          data: jsonEncode(testBadQuestUpdate));

      QuestRepository repo = QuestRepository(dio: dio);

      BasicResponse response = await repo.upsertQuest(testBadQuestUpdate);
      expect(response.success, false);
      expect(response.description, "Did not work");


    });
  });
}
