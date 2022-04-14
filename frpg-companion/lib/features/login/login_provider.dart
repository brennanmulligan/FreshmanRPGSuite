import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frpg_companion/features/login/data/data.dart';
import 'package:frpg_companion/features/network/network.dart';

class LoginProvider {
  static final loginWithCredentialsDatasource =
      Provider<LoginWithCredentialsDatasource>((ref) {
    return LoginWithCredentialsDatasourceHTTP(
      sc: ref.watch(NetworkProvider.serviceClient),
    );
  });

  static final logoutDatasource = Provider<LogoutDatasource>((ref) {
    return LogoutDatasourceHTTP(
      sc: ref.watch(NetworkProvider.serviceClient),
    );
  });

  static final loginRepository = Provider<LoginRepository>((ref) {
    return LoginRepositoryHTTP(
      loginWithCredentialsDatasource: ref.watch(loginWithCredentialsDatasource),
      logoutDatasource: ref.watch(logoutDatasource),
    );
  });

  static final loginController =
      StateNotifierProvider.autoDispose<LoginController, LoginState>((ref) {
    return LoginController(
      repository: ref.watch(loginRepository),
    );
  });
}
