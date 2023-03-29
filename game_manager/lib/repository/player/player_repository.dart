import 'dart:convert';
import 'package:dio/dio.dart';

import 'change_player_request.dart';
import 'create_player_request.dart';
import 'basic_response.dart';



class PlayerRepository{

  PlayerRepository({required this.dio}){
    dio.options.headers['content-Type'] = 'application/json; charset=UTF-8';
    dio.options.headers['Access-Control-Allow-Origin'] = '*';
    dio.options.baseUrl = "http://127.0.0.1:8080";
  }
  final Dio dio;

  Future<BasicResponse> createPlayer(CreatePlayerRequest request) async
  {
    print(request);
    try {
      final response = await dio.post(
        '/player/create',

        data: jsonEncode(request),
      );
      return BasicResponse.fromJson(json: jsonDecode(response.data));
    } on DioError catch (e) {
        return const BasicResponse(success: false, description: "Failed to create player");
      //TODO Error checking on http response types
    }
  }

  Future<BasicResponse> changePassword(ChangePlayerRequest request) async
  {
    final response = await dio.post(
      '/player/update/',
      data: jsonEncode(request),
    );
    //TODO Error checking on http response type
    return BasicResponse.fromJson(json: jsonDecode(response.data));
  }
}