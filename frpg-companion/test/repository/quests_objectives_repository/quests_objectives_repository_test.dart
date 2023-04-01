import 'dart:convert';

import 'package:companion_app/repository/quests_objectives_repository/all-objectives-request.dart';
import 'package:companion_app/repository/quests_objectives_repository/all-objectives-response.dart';
import 'package:companion_app/repository/quests_objectives_repository/complete-objective-request.dart';
import 'package:companion_app/repository/quests_objectives_repository/objective.dart';
import 'package:companion_app/repository/quests_objectives_repository/quests_objectives_repository.dart';
import 'package:companion_app/repository/shared/general_response.dart';
import 'package:companion_app/repository/shared_repository_state.dart';
import 'package:dio/dio.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:http_mock_adapter/http_mock_adapter.dart';

void main() {
  group('Quests and Objectives Repository Tests: ', () {
    late Dio dio;
    late DioAdapter dioAdapter;
    var firstObjective = const Objective(
        questID: 2,
        objectiveID: 3,
        description: "first"
            " one");
    var secondObjective =
        const Objective(questID: 3, objectiveID: 42, description: "second one");
    Map<String, dynamic> goodResponse = {
      "objectives": [firstObjective, secondObjective],
    };

    setUpAll(() {
      dio = Dio();
      dioAdapter = DioAdapter(dio: dio);
      dio.httpClientAdapter = dioAdapter;
      SharedRepositoryState().setDio(dio);
    });

    // clear the dio adapter after each test
    setUp()
    {
      dioAdapter = DioAdapter(dio: dio);
      dio.httpClientAdapter = dioAdapter;
    }
    test('Request for objective list', () async {
      const request = AllObjectivesRequest(playerID: 42);
      dioAdapter.onGet('/objectives',
          (request) => request.reply(200, jsonEncode(goodResponse)),
          data: jsonEncode(request));

      QuestsObjectivesRepository repo = QuestsObjectivesRepository();

      AllObjectivesResponse response = await repo.getAllObjectives(request);
      expect(response.objectives.length, 2);
      expect(response.objectives.contains(firstObjective), true);
      expect(response.objectives.contains(secondObjective), true);
    });

    test('http status not 200 on getting all objectives', () async {
      const allObjectivesRequest = AllObjectivesRequest(playerID: 42);

      final dioError = DioError(
        error: {'message': 'We planned for  getting all objectives to fail!'},
        requestOptions: RequestOptions(path: '/objectives'),
      );

      dioAdapter.onGet('/objectives', (server) => server.throws(401, dioError),
          data: jsonEncode(allObjectivesRequest));

      QuestsObjectivesRepository repo = QuestsObjectivesRepository();

      AllObjectivesResponse response =
          await repo.getAllObjectives(allObjectivesRequest);
      expect(response.success, false);
    });

    test('Good request to complete and objective', () async {
      const request =
          CompleteObjectiveRequest(playerID: 42, questID: 4, objectiveID: 42);
      dioAdapter.onPost(
          '/complete-objective',
          (request) => request.reply(
              200,
              jsonEncode(const GeneralResponse(
                  success: true, description: 'good job!'))),
          data: jsonEncode(request));

      QuestsObjectivesRepository repo = QuestsObjectivesRepository();
      GeneralResponse response = await repo.completeObjective(request);
      expect(response.success, true);
    });

    test('http status 401 on completing an objective', () async {
      const request =
          CompleteObjectiveRequest(playerID: 42, questID: 42, objectiveID: 42);

      final dioError = DioError(
        error: {'message': 'We planned for completing an objective to fail!'},
        response: Response(
            statusCode: 401,
            requestOptions: RequestOptions(path: '/complete-objective')),
        requestOptions:
            RequestOptions(path: '/complete-objective', method: 'POST'),
        type: DioErrorType.badResponse,
      );

      dioAdapter.onPost(
          '/complete-objective', (server) => server.throws(401, dioError),
          data: jsonEncode(request));

      QuestsObjectivesRepository repo = QuestsObjectivesRepository();

      GeneralResponse response = await repo.completeObjective(request);
      expect(response.success, false);
      expect(response.description, 'Http error 401');
    });
  });
}
