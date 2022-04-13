import 'package:frpg_companion/features/network/network.dart';
import 'package:frpg_companion/features/objective/data/data.dart';

///
/// Template for an objective repository.
///
abstract class ObjectiveRepository {
  final CompleteObjectiveDatasource completeObjectiveDatasource;

  ///
  /// Constructor.
  ///
  const ObjectiveRepository({
    required this.completeObjectiveDatasource,
  });

  ///
  /// Template for completing an objective.
  ///
  Future<Result<CompleteObjectiveResponse>> completeObjective({
    required CompleteObjectiveRequest request,
  });
}
