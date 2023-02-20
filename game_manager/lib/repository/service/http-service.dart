import 'dart:convert';

import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;
import 'package:http/http.dart';

import '../../pages/create_player/models/create_player_response.dart';
import '../../pages/create_player/models/create_player_request.dart';

class HttpService {
  HttpService({
    http.Client? httpClient,
    // TODO build this from the .env file
    this.baseUrl = 'http://127.0.0.1:8080',
  }) : _httpClient = httpClient ?? http.Client();

  final String baseUrl;
  final Client _httpClient;

  Uri getUrl({
    required String url,
    Map<String, String>? extraParameters,
  }) {
    final queryParameters = <String, String>{
      //'key': dotenv.get('GAMES_API_KEY')
    };
    if (extraParameters != null) {
      queryParameters.addAll(extraParameters);
    }

    return Uri.parse('$baseUrl/$url').replace(
      queryParameters: queryParameters,
    );
  }

  Client get httpClient => _httpClient;
}
