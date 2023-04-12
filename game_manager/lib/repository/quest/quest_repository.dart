import 'dart:convert';
import 'package:dio/dio.dart';

import 'package:game_manager/repository/quest/upsert_quest_request.dart';

import '../player/basic_response.dart';

class QuestRepository {
  QuestRepository({required this.dio}) {
    dio.options.headers['content-Type'] = 'application/json; charset=UTF-8';
    dio.options.headers['Access-Control-Allow-Origin'] = '*';
    dio.options.baseUrl = "http://127.0.0.1:8080";
  }

  final Dio dio;

  //UpsertQuestRequest
  //BasicResponse
  Future<BasicResponse> upsertQuest(UpsertQuestRequest request) async {
    try {
      final response = await dio.post(
        '/quest/upsert',
        data: jsonEncode(request),
      );

      return BasicResponse.fromJson(json: jsonDecode(response.data));
    } on DioError catch (e) {
      final errorResponse = BasicResponse.fromJson(
        json: jsonDecode(e.response?.data ?? '{}'),
      );
      String desc = errorResponse.description;
      return BasicResponse(success: false, description: desc);
    }
  }
}
