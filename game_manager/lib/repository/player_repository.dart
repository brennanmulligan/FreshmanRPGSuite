import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:game_manager/repository/service/http-service.dart';

import '../pages/create_player/models/create_player_request.dart';
import '../pages/create_player/models/create_player_response.dart';

class PlayerRepository{
  const PlayerRepository({
    required this.service,
  });
  final HttpService service;

  Future<CreatePlayerResponse> createPlayer(CreatePlayerRequest request) async
  {
    final response = await service.httpClient.post(
      service.getUrl(url: 'player'),
      headers:
          <String, String>{
            'Content-Type': 'application/json; charset=UTF-8',
            'Access-Control-Allow-Origin': '*',
          },
      body: jsonEncode(request),
    );
    //TODO Error checking on http response type
    return CreatePlayerResponse.fromJson(json: jsonDecode(response.body));
  }
}