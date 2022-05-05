import 'package:frpg_companion/features/objective/data/data.dart';
import 'package:frpg_networking_api/networking/service_client/service_client.dart';

///
/// HTTP implementation of 'FetchAllObjectiveDatasource'
///
class FetchAllObjectiveDatasourceHTTP extends FetchAllObjectiveDatasource {
  ///
  /// Constructor
  ///
  const FetchAllObjectiveDatasourceHTTP({
    required ServiceClient sc,
  }) : super(sc: sc);

  @override
  Future<FetchAllObjectiveResponse> fetchAllObjective({
    required FetchAllObjectiveRequest request,
  }) async {
    final endpoint = '/objective-fetch-all/${request.playerID}';
    final response = await sc.get(
      endpoint: endpoint,
    );
    return FetchAllObjectiveResponse.fromJson(
      json: response,
    );
  }
}
