import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:game_manager/repository/player/create_player_request.dart';
import 'package:game_manager/repository/player/create_player_response.dart';
import 'package:game_manager/repository/player/player_repository.dart';
import 'package:test/test.dart';
import 'package:http_mock_adapter/http_mock_adapter.dart';

void main() {
  group('Repository Tests: ', () {
    late Dio dio;
    late DioAdapter dioAdapter;
     Map<String, dynamic> goodResponse =
       {
         "success": true,
         "description": "Success",
       };

    setUp(() {
      dio = Dio();
      dioAdapter = DioAdapter(dio: dio);
      dio.httpClientAdapter = dioAdapter;
    });

    test('Good Request', () async {

      const createPlayerRequest = CreatePlayerRequest(name: "name",
          password: "password", crew: 1, major: 2, section: 3);
      dioAdapter.onPost('/player', (request) => request
          .reply(200,
            jsonEncode(goodResponse)),
        data: jsonEncode(createPlayerRequest));

      PlayerRepository repo = PlayerRepository(dio: dio);

      CreatePlayerResponse response = await repo.createPlayer(createPlayerRequest);
      expect(response.success, true);
      expect(response.description, "Success");
    });
  });
}
