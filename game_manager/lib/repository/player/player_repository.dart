import 'dart:convert';
import 'package:dio/dio.dart';

import 'create_player_request.dart';
import 'create_player_response.dart';



class PlayerRepository{

  PlayerRepository({required this.dio}){
    dio.options.headers['content-Type'] = 'application/json; charset=UTF-8';
    dio.options.headers['Access-Control-Allow-Origin'] = '*';
    dio.options.baseUrl = "http://127.0.0.1:8080";
  }
  final Dio dio;

  Future<CreatePlayerResponse> createPlayer(CreatePlayerRequest request) async
  {
    final response = await dio.post(
      '/player',

      data: jsonEncode(request),
    );
    //TODO Error checking on http response type
    return CreatePlayerResponse.fromJson(json: jsonDecode(response.data));
  }
}