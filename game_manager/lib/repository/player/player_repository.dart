import 'dart:convert';
import 'dart:developer';
import 'package:dio/dio.dart';

import 'change_player_request.dart';
import 'create_player_request.dart';
import 'basic_response.dart';

class PlayerRepository {
  PlayerRepository({required this.dio}) {
    dio.options.headers['content-Type'] = 'application/json; charset=UTF-8';
    dio.options.headers['Access-Control-Allow-Origin'] = '*';
    dio.options.baseUrl = "http://127.0.0.1:8080";
  }

  final Dio dio;

  Future<BasicResponse> createPlayer(CreatePlayerRequest request) async {
    try {
      final response = await dio.post(
        '/player/create',
        data: jsonEncode(request),
      );
      return BasicResponse.fromJson(json: jsonDecode(response.data));
    } on DioError catch (e) {
      if (e.response != null) {
        if (e.response!.statusCode == 400) {
          return BasicResponse.fromJson(json: jsonDecode(e.response!.data));
        }
      }
    }

    return const BasicResponse(
        success: false,
        description: "Player could not be created. No response from server.");
  }

  Future<BasicResponse> changePassword(ChangePlayerRequest request) async {
    try {
      final response = await dio.post(
        '/player/update/',
        data: jsonEncode(request),
      );

      return BasicResponse.fromJson(json: jsonDecode(response.data));
    } on DioError catch (e) {
      if (e.response != null) {
        if (e.response!.statusCode == 400) {
          return BasicResponse.fromJson(json: jsonDecode(e.response!.data));
        }
      }
    }

    return const BasicResponse(
        success: false,
        description: "Password could not be changed. No response from server.");
  }
}
