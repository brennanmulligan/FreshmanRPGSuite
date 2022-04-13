import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frpg_companion/features/network/network.dart';
import 'objective.dart';

///
/// Provider that watches all parts of an objective sequence.
///
class ObjectiveProvider {
  ///
  /// The objective controller.
  ///
  static final objectiveController =
      StateNotifierProvider.autoDispose<ObjectiveController, ObjectiveState>(
          (ref) {
    return ObjectiveController(
      repository: ref.watch(objectiveRepository),
    );
  });

  ///
  /// The complete objective datasource.
  ///
  static final completeObjectiveDatasource =
      Provider<CompleteObjectiveDatasource>((ref) {
    return CompleteObjectiveDatasourceHTTP(
      sc: ref.watch(NetworkProvider.serviceClient),
    );
  });

  ///
  /// The objective repository.
  ///
  static final objectiveRepository = Provider<ObjectiveRepository>((ref) {
    return ObjectiveRepositoryHTTP(
      completeObjectiveDatasource: ref.watch(completeObjectiveDatasource),
    );
  });
}
