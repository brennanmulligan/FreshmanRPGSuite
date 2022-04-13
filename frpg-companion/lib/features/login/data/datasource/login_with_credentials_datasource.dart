import 'package:frpg_companion/features/network/network.dart';
import 'package:frpg_companion/features/login/data/data.dart';

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
