import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:game_manager/repository/player/all_players_request.dart';
import 'package:game_manager/repository/player/all_players_response.dart';
import 'package:game_manager/repository/player/change_player_request.dart';
import 'package:game_manager/repository/player/create_player_request.dart';
import 'package:game_manager/repository/player/basic_response.dart';
import 'package:game_manager/repository/player/crew.dart';
import 'package:game_manager/repository/player/get_all_crews_request.dart';
import 'package:game_manager/repository/player/get_all_crews_response.dart';
import 'package:game_manager/repository/player/get_all_majors_request.dart';
import 'package:game_manager/repository/player/get_all_majors_response.dart';
import 'package:game_manager/repository/player/major.dart';
import 'package:game_manager/repository/player/player.dart';
import 'package:game_manager/repository/player/player_repository.dart';
import 'package:http_mock_adapter/http_mock_adapter.dart';
import 'dart:io';

void main() {
  dotenv.testLoad(fileInput: File('assets/.env').readAsStringSync());

  group('General Player Tests: ', () {
    late Dio dio;
    late DioAdapter dioAdapter;
     Map<String, dynamic> goodResponse =
       {
         "success": true,
         "description": "Success",
       };

     Map<String, dynamic> weakPasswordResponse =
       {
         "success": false,
         "description": "Weak password. Please try again.",
       };

      var player1 = Player(
          playerID: 1,
          playerName: "name",
      );
      var player2 = Player(
          playerID: 2,
          playerName: "name2",
      );

      Map<String, dynamic> goodPlayersResponse =
        {
          "success": true,
          "players": [player1, player2],
        };

    setUp(() {
      dio = Dio();
      dioAdapter = DioAdapter(dio: dio);
      dio.httpClientAdapter = dioAdapter;
    });

    test('Good Request', () async {

      const createPlayerRequest = CreatePlayerRequest(playerName: "name",
          password: "password", crew: 1, major: 2, section: 3);
      dioAdapter.onPost('/player/create', (request) => request
          .reply(200,
            jsonEncode(goodResponse)),
        data: jsonEncode(createPlayerRequest));

      PlayerRepository repo = PlayerRepository(dio: dio);

      BasicResponse response = await repo.createPlayer(createPlayerRequest);
      expect(response.success, true);
      expect(response.description, "Success");
    });

    test('Good Change Password Request', () async {
      const changePlayerRequest = ChangePlayerRequest(playerName: "username",
          password: "password");
      dioAdapter.onPost('/player/update/',
              (request) => request
              .reply(200,
              jsonEncode(goodResponse)),
          data: jsonEncode(changePlayerRequest));

      PlayerRepository repo = PlayerRepository(dio: dio);

      BasicResponse passwordResponse = await repo.changePassword(changePlayerRequest);
      expect(passwordResponse.success, true);
      expect(passwordResponse.description, "Success");
    });

    test('Bad Request', () async {
      const createPlayerRequest = CreatePlayerRequest(playerName: "name",
          password: "password", crew: 1, major: 2, section: 3);
      dioAdapter.onPost('/player/create', (request) => request
          .reply(400,
          jsonEncode(weakPasswordResponse)),
          data: jsonEncode(createPlayerRequest));

      PlayerRepository repo = PlayerRepository(dio: dio);

      BasicResponse response = await repo.createPlayer(createPlayerRequest);
      expect(response.success, false);
      expect(response.description, "Weak password. Please try again.");
    });

    test('get all players good request', () async {
      const allPlayersRequest = AllPlayersRequest();
      dioAdapter.onGet('/getAllPlayers', (request) => request
          .reply(200,
          jsonEncode(goodPlayersResponse)),
          data: jsonEncode(allPlayersRequest));
      PlayerRepository repo = PlayerRepository(dio: dio);

      AllPlayersResponse response = await repo.getAllPlayers(allPlayersRequest);
      expect(response.success, true);
      expect(response.players.length, 2);
      expect(response.players.contains(player1), true);
      expect(response.players.contains(player2), true);
    });
  });

  group('Majors and Crews Tests: ', () {
    late Dio dio;
    late DioAdapter dioAdapter;
    var firstMajor = const Major(
        id: 1,
        name: "Computer Science"
    );
    var secondMajor = const Major(
        id: 2,
        name: "Software Engineering"
    );
    Map<String, dynamic> goodMajorsResponse = {
      "success": true,
      "majors": [firstMajor, secondMajor],
    };

    var firstCrew = const Crew(
        id: 1,
        name: "Off by One"
    );
    var secondCrew = const Crew(
        id: 2,
        name: "Out of Bounds"
    );
    Map<String, dynamic> goodCrewsResponse = {
      "success": true,
      "crews": [firstCrew, secondCrew],
    };


    setUp(() {
      dio = Dio();
      dioAdapter = DioAdapter(dio: dio);
      dio.httpClientAdapter = dioAdapter;
    });

    test('Good Majors Request', () async {

      const getAllMajorsRequest = GetAllMajorsRequest();
      dioAdapter.onGet('/majors', (request) => request
          .reply(200,
          jsonEncode(goodMajorsResponse)),
          data: jsonEncode(getAllMajorsRequest));

      PlayerRepository repo = PlayerRepository(dio: dio);

      GetAllMajorsResponse response = await repo.getAllMajors(getAllMajorsRequest);
      expect(response.success, true);
      expect(response.majors.length, 2);
      expect(response.majors.contains(firstMajor), true);
      expect(response.majors.contains(secondMajor), true);
    });

    test('Good Crews Request', () async {
      const getAllCrewsRequest = GetAllCrewsRequest();
      dioAdapter.onGet('/crews', (request) => request
          .reply(200,
          jsonEncode(goodCrewsResponse)),
          data: jsonEncode(getAllCrewsRequest));

      PlayerRepository repo = PlayerRepository(dio: dio);

      GetAllCrewsResponse response = await repo.getAllCrews(getAllCrewsRequest);
      expect(response.success, true);
      expect(response.crews.length, 2);
      expect(response.crews.contains(firstCrew), true);
      expect(response.crews.contains(secondCrew), true);
    });
  });
}
