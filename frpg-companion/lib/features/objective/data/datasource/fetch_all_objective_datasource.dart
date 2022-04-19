import 'package:frpg_companion/features/objective/data/data.dart';
import 'package:frpg_networking_api/networking/service_client/service_client.dart';

///
/// Template for fetching all objectives.
///
abstract class FetchAllObjectiveDatasource {
  final ServiceClient sc;

  ///
  /// Constructor.
  ///
  const FetchAllObjectiveDatasource({
    required this.sc,
  });

  ///
  /// Command to fetch all an objective.
  ///
  Future<FetchAllObjectiveResponse> fetchAllObjective({
    required FetchAllObjectiveRequest request,
  });
}
