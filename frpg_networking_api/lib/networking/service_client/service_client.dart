import 'type_definitions/type_definitions.dart';

///
/// Template for a service client.
///
abstract class ServiceClient {
  final String baseURL;

  ///
  /// Constructor.
  ///
  const ServiceClient({
    required this.baseURL,
  });

  ///
  /// Template for a get method.
  ///
  Future<JSON> get({
    required String endpoint,
    HEADER? headers,
  });

  ///
  /// Template for a post method.
  ///
  Future<JSON> post({
    required String endpoint,
    HEADER? headers,
    required JSON body,
  });
}
