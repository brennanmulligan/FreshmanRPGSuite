import 'package:flutter/foundation.dart';
import 'package:frpg_companion/features/login/data/data.dart';
import 'package:frpg_companion/features/network/network.dart';

///
/// Login repository implementation using HTTP.
///
class LoginRepositoryHTTP extends LoginRepository {
  ///
  /// Constructor.
  ///
  const LoginRepositoryHTTP({
    required LoginWithCredentialsDatasource loginWithCredentialsDatasource,
    required LogoutDatasource logoutDatasource,
  }) : super(
          loginWithCredentialsDatasource: loginWithCredentialsDatasource,
          logoutDatasource: logoutDatasource,
        );

  ///
  /// Login with credentials on a remote server.
  ///
  @override
  Future<Result<LoginWithCredentialsResponse>> loginWithCredentials(
      {required LoginWithCredentialsRequest request}) async {
    try {
      final response = await loginWithCredentialsDatasource
          .loginWithCredentials(request: request);
      return Result.data(data: response);
    } catch (exception, stackTrace) {
      return Result.failure(
        failure: HTTPFailure(
          message: '$exception : $stackTrace',
        ),
      );
    }
  }

  @override
  Future<void> logOut({
    required LogoutRequest request,
  }) async {
    try {
      await logoutDatasource.logOut(request: request);
    } catch (exception, stackTrace) {
      debugPrint('How did you get here? Anyway... here\'s your error '
          '-> $exception : $stackTrace');
    }
  }
}
