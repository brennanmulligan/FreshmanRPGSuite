import 'package:frpg_companion/features/login/data/data.dart';
import 'package:frpg_networking_api/networking/service_client/service_client.dart';

///
/// Template for completing an objective.
///
abstract class LoginWithCredentialsDatasource {
  final ServiceClient sc;

  ///
  /// Constructor.
  ///
  const LoginWithCredentialsDatasource({
    required this.sc,
  });

  ///
  /// Command to complete an objective.
  ///
  Future<LoginWithCredentialsResponse> loginWithCredentials({
    required LoginWithCredentialsRequest request,
  });
}
