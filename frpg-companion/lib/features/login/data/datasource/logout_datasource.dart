import 'package:frpg_companion/features/login/data/model/logout_request.dart';
import 'package:frpg_networking_api/networking/service_client/service_client.dart';

abstract class LogoutDatasource {
  final ServiceClient sc;

  ///
  /// Constructor.
  ///
  const LogoutDatasource({
    required this.sc,
  });

  ///
  ///
  ///
  Future<void> logOut({
    required LogoutRequest request,
  });
}
