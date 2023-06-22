import 'dart:convert';
import 'package:companion_app/repository/login_repository/logout_request.dart';
import 'package:companion_app/repository/shared_repository_state.dart';
import 'package:dio/dio.dart';

import 'login_with_credentials_request.dart';
import 'login_with_credentials_response.dart';
import 'logout_response.dart';

class LoginRepository{

  LoginRepository(){
    _dio = SharedRepositoryState().getDio()!;
  }
  late final Dio _dio;

  Future<LoginWithCredentialsResponse> loginPlayer(LoginWithCredentialsRequest request) async
  {
    try {
      final response = await _dio.post(
        '/login',

        data: jsonEncode(request),
      );
       return LoginWithCredentialsResponse.fromJson(
          json: jsonDecode(response.data));
    } on DioError catch (e) {
      throw e;
    }
  }

  Future<LogoutResponse> logoutPlayer(LogoutRequest request) async
  {
    try {
      final response = await _dio.post(
        '/logout',
        data: jsonEncode(request),
      );
      return LogoutResponse.fromJson(json: jsonDecode(response.data));
    } on DioError catch (e) {
      throw e;
    }
  }
}
