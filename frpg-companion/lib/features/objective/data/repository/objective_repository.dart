import 'package:frpg_companion/features/objective/data/data.dart';
import 'package:frpg_networking_api/networking/result/result.dart';

///
/// Template for an objective repository.
///
abstract class ObjectiveRepository {
  final CompleteObjectiveDatasource completeObjectiveDatasource;
  final FetchAllObjectiveDatasource fetchAllObjectiveDatasource;

  ///
  /// Constructor.
  ///
  const ObjectiveRepository({
    required this.completeObjectiveDatasource,
    required this.fetchAllObjectiveDatasource,
  });

  ///
  /// Template for completing an objective.
  ///
  Future<Result<CompleteObjectiveResponse>> completeObjective({
    required CompleteObjectiveRequest request,
  });

  ///
  /// Template for fetching all objectives.
  ///
  Future<Result<FetchAllObjectiveResponse>> fetchAllObjectives({
    required FetchAllObjectiveRequest request,
  });
}
