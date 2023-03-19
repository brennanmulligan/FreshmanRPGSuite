import 'dart:convert';
import 'package:dio/dio.dart';

import 'all_crews_request.dart';
import 'all_crews_response.dart';



class CrewsRepository {

  CrewsRepository({required this.dio}){
    dio.options.headers['content-Type'] = 'application/json; charset=UTF-8';
    dio.options.headers['Access-Control-Allow-Origin'] = '*';
    dio.options.baseUrl = "http://127.0.0.1:8080";
  }
  final Dio dio;

  Future<AllCrewsResponse> getAllCrews(AllCrewsRequest request) async
  {
    try {
      final response = await dio.get(
        '/crews',
        data: jsonEncode(request),
      );
      return AllCrewsResponse.fromJson(json: jsonDecode(response.data));
    } on DioError catch (e) {
      return const AllCrewsResponse(false, crews: []);
    }
  }
}