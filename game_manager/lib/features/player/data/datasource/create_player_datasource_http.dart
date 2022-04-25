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
    const endpoint = '/player';
    final body = request.asJson;
    final response = await sc.post(
      endpoint: endpoint,
      body: body,
    );
    return CreatePlayerResponse.fromJson(
      json: response
    );
  }
}