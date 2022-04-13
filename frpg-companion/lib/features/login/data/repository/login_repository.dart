import 'package:frpg_companion/features/network/network.dart';
import 'package:frpg_companion/features/login/data/data.dart';

///
/// Template for an login repository.
///
abstract class LoginRepository {
  final LoginWithCredentialsDatasource loginWithCredentialsDatasource;

  ///
  /// Constructor.
  ///
  const LoginRepository({
    required this.loginWithCredentialsDatasource,
  });

  ///
  /// Template for completing an objective.
  ///
  Future<Result<LoginWithCredentialsResponse>> loginWithCredentials({
    required LoginWithCredentialsRequest request,
  });
}
