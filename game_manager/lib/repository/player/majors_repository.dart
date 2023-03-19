import 'dart:convert';
import 'package:dio/dio.dart';
import 'package:game_manager/repository/player/all_majors_response.dart';

import 'all_majors_request.dart';



class MajorsRepository {

  MajorsRepository({required this.dio}){
    dio.options.headers['content-Type'] = 'application/json; charset=UTF-8';
    dio.options.headers['Access-Control-Allow-Origin'] = '*';
    dio.options.baseUrl = "http://127.0.0.1:8080";
  }
  final Dio dio;

  Future<AllMajorsResponse> getAllMajors(AllMajorsRequest request) async
  {
    try {
      final response = await dio.get(
        '/majors',
        data: jsonEncode(request),
      );
      return AllMajorsResponse.fromJson(json: jsonDecode(response.data));
    } on DioError catch (e) {
      return const AllMajorsResponse(false, majors: []);
    }
  }
}