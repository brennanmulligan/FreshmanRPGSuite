import 'package:frpg_companion/features/login/data/datasource/logout_datasource.dart';
import 'package:frpg_companion/features/login/data/model/logout_request.dart';
import 'package:frpg_networking_api/networking/service_client/service_client.dart';

class LogoutDatasourceHTTP extends LogoutDatasource {
  ///
  ///
  ///
  const LogoutDatasourceHTTP({
    required ServiceClient sc,
  }) : super(sc: sc);

  ///
  ///
  ///
  @override
  Future<void> logOut({
    required LogoutRequest request,
  }) async {
    final endpoint = '/logout/${request.playerID}';
    await sc.get(
      endpoint: endpoint,
    );
  }
}
