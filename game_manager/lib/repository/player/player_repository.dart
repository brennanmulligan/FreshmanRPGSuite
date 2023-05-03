import 'dart:convert';
import 'dart:developer';
import 'package:dio/dio.dart';
import 'package:game_manager/repository/player/all_players_request.dart';

import 'all_players_response.dart';
import 'change_player_request.dart';
import 'create_player_request.dart';
import 'basic_response.dart';
import 'get_all_crews_request.dart';
import 'get_all_crews_response.dart';
import 'get_all_majors_request.dart';
import 'get_all_majors_response.dart';

/**
 * A class for connecting with the backend using the restful server.
 * Deals with anything and everything related to a player.
 */
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
      final errorResponse = BasicResponse.fromJson(
        json: jsonDecode(e.response?.data ?? '{}'),
      );
      String description = errorResponse.description;
      return BasicResponse(success: false, description: description);
      //TODO Error checking on http response types
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

  Future<AllPlayersResponse> getAllPlayers(AllPlayersRequest request) async {
    try {
      final response = await dio.get(
        '/getAllPlayers',
        data: jsonEncode(request)
      );

      return AllPlayersResponse.fromJson(json: jsonDecode(response.data));
    } on DioError catch (e) {
      if (e.response != null) {
        if (e.response!.statusCode == 400) {
          return AllPlayersResponse.fromJson(json: jsonDecode(e.response!.data));
        }
      }
    }

    return const AllPlayersResponse(false, players: []);
  }

  Future<GetAllMajorsResponse> getAllMajors(GetAllMajorsRequest request) async
  {
    try {
      final response = await dio.get(
        '/majors',
        data: jsonEncode(request),
      );
      return GetAllMajorsResponse.fromJson(json: jsonDecode(response.data));
    } on DioError catch (e) {
      return const GetAllMajorsResponse(false, majors: []);
    }
  }

  Future<GetAllCrewsResponse> getAllCrews(GetAllCrewsRequest request) async
  {
    try {
      final response = await dio.get(
        '/crews',
        data: jsonEncode(request),
      );
      return GetAllCrewsResponse.fromJson(json: jsonDecode(response.data));
    } on DioError catch (e) {
      return const GetAllCrewsResponse(false, crews: []);
    }
  }

}
