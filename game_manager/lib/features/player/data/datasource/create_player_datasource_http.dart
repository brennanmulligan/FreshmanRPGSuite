import 'package:flutter/foundation.dart';
import 'package:frpg_networking_api/networking/service_client/type_definitions/type_definitions.dart';
import 'package:game_manager/features/player/data/data.dart';
import 'package:frpg_networking_api/networking/service_client/service_client.dart';

///
/// HTTP implementation of 'CreatePlayerDatasource'.
///
class CreatePlayerDatasourceHTTP extends CreatePlayerDatasource {
  ///
  /// Constructor
  ///
  const CreatePlayerDatasourceHTTP({
    required ServiceClient sc,
  }) : super(sc: sc);

  ///
  /// Allows us to create a player from a request.
  ///
  @override
  Future<CreatePlayerResponse> createPlayer({
    required CreatePlayerRequest request,
  }) async {
    debugPrint("in createPlayer");
    const endpoint = '/player';
    final body = request.asJson;
    print(sc.baseURL);
    print(request.toString());
    final response = await sc.post(
      endpoint: endpoint,
      body: body,
    );
    debugPrint(response.toString());
    debugPrint("out createPlayer");
    return CreatePlayerResponse.fromJson(json: response);
  }
}
