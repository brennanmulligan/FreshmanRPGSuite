import 'package:frpg_companion/features/objective/data/data.dart';
import 'package:frpg_companion/features/network/network.dart';

///
/// Objective repository implementation using HTTP.
///
class ObjectiveRepositoryHTTP extends ObjectiveRepository {
  ///
  /// Constructor.
  ///
  const ObjectiveRepositoryHTTP({
    required CompleteObjectiveDatasource completeObjectiveDatasource,
    required FetchAllObjectiveDatasource fetchAllObjectiveDatasource,
  }) : super(
            completeObjectiveDatasource: completeObjectiveDatasource,
            fetchAllObjectiveDatasource: fetchAllObjectiveDatasource);

  ///
  /// Complete an objective on a remote server.
  ///
  @override
  Future<Result<CompleteObjectiveResponse>> completeObjective(
      {required CompleteObjectiveRequest request}) async {
    try {
      final response =
          await completeObjectiveDatasource.completeObjective(request: request);
      return Result.data(data: response);
    } catch (exception, stackTrace) {
      return Result.failure(
        failure: HTTPFailure(
          message: '$exception : $stackTrace',
        ),
      );
    }
  }

  ///
  /// Fetch all objective on a remote server
  ///
  @override
  Future<Result<FetchAllObjectiveResponse>> fetchAllObjectives(
      {required FetchAllObjectiveRequest request}) async {
    try {
      final response =
          await fetchAllObjectiveDatasource.fetchAllObjective(request: request);
      return Result.data(data: response);
    } catch (exception, stackTrace) {
      return Result.failure(
        failure: HTTPFailure(
          message: '$exception : $stackTrace',
        ),
      );
    }
  }
}
