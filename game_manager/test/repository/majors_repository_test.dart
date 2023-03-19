import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:game_manager/repository/player/all_majors_request.dart';
import 'package:game_manager/repository/player/all_majors_response.dart';
import 'package:game_manager/repository/player/major.dart';
import 'package:game_manager/repository/player/majors_repository.dart';
import 'package:test/test.dart';
import 'package:http_mock_adapter/http_mock_adapter.dart';

void main() {
  group('Majors Repository Tests: ', () {
    late Dio dio;
    late DioAdapter dioAdapter;
    var firstMajor = const Major(
        majorID: 1,
        name: "Computer Science"
    );
    var secondMajor = const Major(
        majorID: 2,
        name: "Software Engineering"
    );
    Map<String, dynamic> goodResponse = {
      "success": true,
      "majors": [firstMajor, secondMajor],
    };


    setUp(() {
      dio = Dio();
      dioAdapter = DioAdapter(dio: dio);
      dio.httpClientAdapter = dioAdapter;
    });

    test('Good Request', () async {

      const getAllMajorsRequest = AllMajorsRequest();
      dioAdapter.onGet('/majors', (request) => request
          .reply(200,
          jsonEncode(goodResponse)),
          data: jsonEncode(getAllMajorsRequest));

      MajorsRepository repo = MajorsRepository(dio: dio);

      AllMajorsResponse response = await repo.getAllMajors(getAllMajorsRequest);
      expect(response.success, true);
      expect(response.majors.length, 2);
      expect(response.majors.contains(firstMajor), true);
      expect(response.majors.contains(secondMajor), true);
    });
  });
}
