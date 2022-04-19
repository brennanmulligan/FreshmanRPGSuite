import 'package:frpg_companion/features/login/data/data.dart';
import 'package:frpg_networking_api/networking/result/result.dart';

///
/// Template for an login repository.
///
abstract class LoginRepository {
  final LoginWithCredentialsDatasource loginWithCredentialsDatasource;
  final LogoutDatasource logoutDatasource;

  ///
  /// Constructor.
  ///
  const LoginRepository({
    required this.loginWithCredentialsDatasource,
    required this.logoutDatasource,
  });

  ///
  /// Template for completing an objective.
  ///
  Future<Result<LoginWithCredentialsResponse>> loginWithCredentials({
    required LoginWithCredentialsRequest request,
  });

  ///
  ///
  ///
  Future<void> logOut({
    required LogoutRequest request,
  });
}
