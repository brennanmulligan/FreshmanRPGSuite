
import 'package:equatable/equatable.dart';
import '../../type_definitions.dart';
import 'objective.dart';

///
/// Object to response for completing login.
///
class AllObjectivesResponse extends Equatable {
  final List<Objective> objectives;
  final bool success;

  ///
  /// Constructor.
  ///
  const AllObjectivesResponse( this.success,{
    required this.objectives,
  });

  // constructor for all fields
  const AllObjectivesResponse.allFields({
    required this.objectives,
    required this.success,
  });

  ///
  /// Factory mapping `JSON` to `AllObjectivesResponse`.
  ///
  factory AllObjectivesResponse.fromJson({
    required JSON json,
  }) {
    List<Objective> objList = [];

    for (Map<String, dynamic> objective in json['objectives']) {
      objList.add(Objective(
          questID: objective['questID'],
          objectiveID: objective['objectiveID'],
          description: objective['description']));
    }

    return AllObjectivesResponse(true,
      objectives: objList,
    );
  }

  List<Objective> encodeJsonList(List jsonList) {
    List<Objective> encodedList = [];

    for (Map<String, dynamic> objective in jsonList) {
      encodedList.add(Objective(
          questID: objective['questID'],
          objectiveID: objective['objectiveID'],
          description: objective['description']));
    }

    return encodedList;
  }

  ///
  /// Get properties of object for comparison.
  ///
  @override
  List<Object?> get props => [
        objectives,
      ];

  ///
  /// Convert object to string.
  ///
  @override
  String toString() {
    return 'AllObjectivesResponse(objectives: $objectives)';
  }
}
