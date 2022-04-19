import 'package:frpg_companion/features/login/data/data.dart';
import 'package:frpg_networking_api/networking/service_client/service_client.dart';

///
/// HTTP implementation of `LoginWithCredentialsDatasource`.
///
class LoginWithCredentialsDatasourceHTTP
    extends LoginWithCredentialsDatasource {
  ///
  /// Constructor.
  ///
  const LoginWithCredentialsDatasourceHTTP({
    required ServiceClient sc,
  }) : super(sc: sc);

  ///
  /// Allows us to login from a request.
  ///
  @override
  Future<LoginWithCredentialsResponse> loginWithCredentials({
    required LoginWithCredentialsRequest request,
  }) async {
    const endpoint = '/login';
    final body = request.asJson;
    final response = await sc.post(
      endpoint: endpoint,
      body: body,
    );
    return LoginWithCredentialsResponse.fromJson(
      json: response,
    );
  }
}
