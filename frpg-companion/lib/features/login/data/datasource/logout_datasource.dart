import 'package:frpg_companion/features/login/data/model/logout_request.dart';

import '../../../network/network.dart';

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
