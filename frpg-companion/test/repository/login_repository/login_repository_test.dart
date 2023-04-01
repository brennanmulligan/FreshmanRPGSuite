
import 'dart:convert';

import 'package:companion_app/repository/login_repository'
    '/login_with_credentials_request.dart';
import 'package:companion_app/repository/shared_repository_state.dart';
import 'package:dio/dio.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:companion_app/repository/login_repository'
    '/login_with_credentials_response.dart';
import 'package:companion_app/repository/login_repository/login_repository'
    '.dart';
import 'package:http_mock_adapter/http_mock_adapter.dart';

void main() {
  group('Player Repository Tests: ', () {
    late Dio dio;
    late DioAdapter dioAdapter;
    Map<String, dynamic> goodResponse =
    {
      "playerID": 3,
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
    test('Good Login Request', () async {

      const loginRequest = LoginWithCredentialsRequest(username: "merlin",
          password: "pw");
      dioAdapter.onPost('/login', (request) => request
          .reply(200,
          jsonEncode(goodResponse)),
          data: jsonEncode(loginRequest));

      LoginRepository repo = LoginRepository();

      LoginWithCredentialsResponse response = await repo.loginPlayer(loginRequest);
      expect(response.playerID, isNonZero) ;
    });
  });
}