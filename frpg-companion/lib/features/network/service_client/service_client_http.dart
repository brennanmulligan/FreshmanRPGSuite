import 'dart:convert';

import 'service_client.dart';
import 'package:http/http.dart' as http;

///
/// Implementation of service client using HTTP.
///
class ServiceClientHTTP extends ServiceClient {
  ///
  /// Super constructor.
  ///
  ServiceClientHTTP({
    required String baseURL,
  }) : super(
          baseURL: baseURL,
        );

  ///
  /// Converts `http.Response` to `JSON`.
  ///
  JSON _processResponse(http.Response response) {
    return json.decode(response.body);
  }

  ///
  /// Ask for information from a server and expect a response.
  ///
  @override
  Future<JSON> get({
    required String endpoint,
    HEADER? headers,
  }) async {
    final response = await http.get(
      Uri.http(baseURL, endpoint),
      headers: headers ??
          <String, String>{'Content-Type': 'application/json; charset=UTF-8'},
    );
    return _processResponse(response);
  }

  ///
  /// Send information to a server and expect a response.
  ///
  @override
  Future<JSON> post({
    required String endpoint,
    HEADER? headers,
    required JSON body,
  }) async {
    final response = await http.post(
      Uri.http(baseURL, endpoint),
      headers: headers ??
          <String, String>{'Content-Type': 'application/json; charset=UTF-8'},
      body: jsonEncode(body),
    );
    return _processResponse(response);
  }
}
