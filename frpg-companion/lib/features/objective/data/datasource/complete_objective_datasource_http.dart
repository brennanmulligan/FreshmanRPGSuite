import 'package:frpg_companion/features/network/network.dart';
import 'package:frpg_companion/features/objective/data/data.dart';

///
/// HTTP implementation of `CompleteObjectiveDatasource`.
///
class CompleteObjectiveDatasourceHTTP extends CompleteObjectiveDatasource {
  ///
  /// Constructor.
  ///
  const CompleteObjectiveDatasourceHTTP({
    required ServiceClient sc,
  }) : super(sc: sc);

  ///
  /// Allows us to complete an objective from a request.
  ///
  @override
  Future<CompleteObjectiveResponse> completeObjective({
    required CompleteObjectiveRequest request,
  }) async {
    const endpoint = '/objective-complete';
    final body = request.asJson;
    final response = await sc.post(
      endpoint: endpoint,
      body: body,
    );
    return CompleteObjectiveResponse.fromJson(
      json: response,
    );
  }
}
