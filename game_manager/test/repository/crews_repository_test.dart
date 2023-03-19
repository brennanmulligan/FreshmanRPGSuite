import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:game_manager/repository/player/all_crews_request.dart';
import 'package:game_manager/repository/player/all_crews_response.dart';
import 'package:game_manager/repository/player/crew.dart';
import 'package:game_manager/repository/player/crews_repository.dart';
import 'package:test/test.dart';
import 'package:http_mock_adapter/http_mock_adapter.dart';

void main() {
  group('Crews Repository Tests: ', () {
    late Dio dio;
    late DioAdapter dioAdapter;
    var firstCrew = const Crew(
        crewID: 1,
        name: "Off by One"
    );
    var secondCrew = const Crew(
        crewID: 2,
        name: "Out of Bounds"
    );
    Map<String, dynamic> goodResponse = {
      "success": true,
      "crews": [firstCrew, secondCrew],
    };


    setUp(() {
      dio = Dio();
      dioAdapter = DioAdapter(dio: dio);
      dio.httpClientAdapter = dioAdapter;
    });

    test('Good Request', () async {

      const getAllCrewsRequest = AllCrewsRequest();
      dioAdapter.onGet('/crews', (request) => request
          .reply(200,
          jsonEncode(goodResponse)),
          data: jsonEncode(getAllCrewsRequest));

      CrewsRepository repo = CrewsRepository(dio: dio);

      AllCrewsResponse response = await repo.getAllCrews(getAllCrewsRequest);
      expect(response.success, true);
      expect(response.crews.length, 2);
      expect(response.crews.contains(firstCrew), true);
      expect(response.crews.contains(secondCrew), true);
    });
  });
}
