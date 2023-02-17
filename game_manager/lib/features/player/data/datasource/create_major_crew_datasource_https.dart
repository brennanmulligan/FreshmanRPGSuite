import 'package:flutter/foundation.dart';
import 'package:frpg_networking_api/networking/service_client/type_definitions/type_definitions.dart';
import 'package:game_manager/features/player/data/data.dart';
import 'package:frpg_networking_api/networking/service_client/service_client.dart';

///
/// HTTP implementation of 'CreatePlayerDatasource'.
///
class CreateMajorCrewDatasourceHTTP extends CreateMajorCrewDatasource {
  ///
  /// Constructor
  ///
  const CreateMajorCrewDatasourceHTTP({
    required ServiceClient sc,
  }) : super(sc: sc);

  ///
  /// Allows us to create a player from a request.
  ///
  @override
  Future<CreateMajorCrewResponse> createMajorCrew({
    required CreateMajorCrewRequest request,
  }) async {
    const endpoint = '/player';
    final body = request.asJson;
    final response = await sc.post(
      endpoint: endpoint,
      body: body,
    );
    return CreateMajorCrewResponse.fromJson(json: response);
  }
}
