import 'package:frpg_companion/features/network/network.dart';
import 'package:frpg_companion/features/objective/data/data.dart';

///
/// Template for completing an objective.
///
abstract class CompleteObjectiveDatasource {
  final ServiceClient sc;

  ///
  /// Constructor.
  ///
  const CompleteObjectiveDatasource({
    required this.sc,
  });

  ///
  /// Command to complete an objective.
  ///
  Future<CompleteObjectiveResponse> completeObjective({
    required CompleteObjectiveRequest request,
  });
}
