import 'dart:convert';
import 'package:companion_app/repository/shared_repository_state.dart';
import 'package:dio/dio.dart';

import 'login_with_credentials_request.dart';
import 'login_with_credentials_response.dart';

class LoginRepository{

  LoginRepository(){
    _dio = SharedRepositoryState().getDio()!;
  }
  late final Dio _dio;

  Future<LoginWithCredentialsResponse> loginPlayer(LoginWithCredentialsRequest request) async
  {
    final response = await _dio.post(
        '/login',

      data: jsonEncode(request),
    );
    //TODO Error checking on http response type
    return LoginWithCredentialsResponse.fromJson(json: jsonDecode(response.data));
  }
}